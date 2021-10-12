package com.qcl.excel.easyexcel;

import com.qcl.excel.domain.User;
import com.qcl.excel.repository.UserRepository;
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
public class GenerateExcelInfoThread implements Callable<List<UserExcelVo>> {
    private int i;
    private UserRepository userRepository;

    public GenerateExcelInfoThread(UserRepository userRepository, int i) {
        this.userRepository = userRepository;
        this.i = i;
    }

    @Override
    public List<UserExcelVo> call() {
        //List<Object> data = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        List<UserExcelVo> exifInfoList = new ArrayList<>();
        try {
            //从数据库查询要写入excle的数据
            Pageable pageable = PageRequest.of(i + 1, 100);
            log.info("当前页数：{}", pageable.getPageNumber());
            //查询订单信息
            org.springframework.data.domain.Page<User> userList = userRepository.findAll(pageable);
            if (userList != null) {
                userList.forEach(user -> {
                    UserExcelVo userExcelVo = new UserExcelVo();
                    BeanUtils.copyProperties(user, userExcelVo);
                    exifInfoList.add(userExcelVo);
                });
            }
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