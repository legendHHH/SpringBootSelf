package com.qcl.excel.easyexcel;

import com.github.pagehelper.PageHelper;
import com.qcl.excel.domain.User;
import com.qcl.excel.domain.Users;
import com.qcl.excel.repository.UserRepository;
import com.qcl.excel.service.UserService;
import com.qcl.excel.vo.UserExcelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 查询数据异步线程
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/9/28
 */
@Slf4j
public class GenerateExcelInfoThread3 implements Callable<List<Users>> {
    private int i;
    private UserService userService;

    public GenerateExcelInfoThread3(UserService userService, int i) {
        this.userService = userService;
        this.i = i;
    }

    @Override
    public List<Users> call() {
        //List<Object> data = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        List<Users> exifInfoList = new ArrayList<>();
        try {
            PageHelper.startPage(i + 1,3);

            //从数据库查询要写入excle的数据
            List<Users> userList = userService.getAllUser();
            exifInfoList.addAll(userList);
            long endTime = System.currentTimeMillis();
            long spendTime = endTime - startTime;
            log.info(Thread.currentThread().getName() + "查询耗时：" + spendTime);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询数据失败:{}", e.getMessage());
        }
        return exifInfoList;
    }
}