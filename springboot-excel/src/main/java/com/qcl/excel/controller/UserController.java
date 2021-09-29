package com.qcl.excel.controller;

import com.github.pagehelper.PageHelper;
import com.qcl.excel.domain.Users;
import com.qcl.excel.easyexcel.GenerateExcelInfoThread;
import com.qcl.excel.easyexcel.GenerateExcelInfoThread3;
import com.qcl.excel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * http://localhost:8080/user/findAll
     *
     * @return
     */
    @GetMapping("/findAll")
    @ResponseBody
    public List<Users> findUser() {
        PageHelper.startPage(1,3);
        List<Users> userList = userService.getAllUser();
        return userList;
    }

    /**
     * http://localhost:8080/user/findAll2
     *
     * @return
     */
    @GetMapping("/findAll2")
    @ResponseBody
    public List<Users> findUserThread() {
        List<Users> userList = new ArrayList<>();

        int corePoolSize=10;
        int maximumPoolSize=20;
        //用线程池管理多线程
        ThreadPoolExecutor exector = (ThreadPoolExecutor) Executors.newFixedThreadPool(corePoolSize);
        exector.setCorePoolSize(corePoolSize);
        exector.setMaximumPoolSize(maximumPoolSize);
        List<GenerateExcelInfoThread3> tasks = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            GenerateExcelInfoThread3 generateExcelInfoThread3 = new GenerateExcelInfoThread3(userService, i);
            tasks.add(generateExcelInfoThread3);
        }

        try {
            List<Future<List<Users>>> futures = exector.invokeAll(tasks);
            for (int i = 0; i < 2; i++) {
                List<Users> exifInfoList = futures.get(i).get();
                userList.addAll(exifInfoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return userList;
    }
}