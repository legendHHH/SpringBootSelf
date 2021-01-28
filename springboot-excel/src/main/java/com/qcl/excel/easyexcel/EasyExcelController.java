package com.qcl.excel.easyexcel;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.google.common.base.Function;
import com.qcl.excel.domain.User;
import com.qcl.excel.easyexcel.handler.StyleExcelHandler;
import com.qcl.excel.repository.UserRepository;
import com.qcl.excel.vo.UserExcelVo;
import com.qcl.excel.vo.UserExcelVo2;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import com.google.common.collect.Lists;

/**
 * EasyExcel导出文件
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/1/26
 */
@Controller
public class EasyExcelController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 导出本地文件保存
     *
     * @param response
     */
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response) {
        List<User> userList = userRepository.findAllById(Arrays.asList(1, 2, 3, 4, 5));

        // 表示要导出的数据源(list转换数据类型)
        List<UserExcelVo> resultList = Lists.transform(userList, new Function<User, UserExcelVo>() {
            @Nullable
            @Override
            public UserExcelVo apply(@Nullable User user) {
                UserExcelVo userExcelVo = new UserExcelVo();
                BeanUtils.copyProperties(user, userExcelVo);
                userExcelVo.setAge("12");
                return userExcelVo;
            }
        });

        // 定义输出文件名称
        String excelName = "用户信息导出-" + DateUtil.formatDate(new Date()) + ".xlsx";
        File file = new File("D:/" + excelName);

        // 此处用自动适应列宽的策略
        EasyExcelFactory.write(file, UserExcelVo2.class).
                registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("sheet名称").doWrite(resultList);
    }


    /**
     * 浏览器导出下载excel
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportExcel2")
    public void export2(HttpServletResponse response) throws IOException {
        //  设置内容格式 以及 编码方式
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        //List<User> userList = userRepository.findAllById(Arrays.asList(1, 2, 3, 4, 5));
        List<User> userList = userRepository.findAll();

        // 表示要导出的数据源(list转换数据类型)
        List<UserExcelVo> resultList = Lists.transform(userList, new Function<User, UserExcelVo>() {
            @Nullable
            @Override
            public UserExcelVo apply(@Nullable User user) {
                UserExcelVo userExcelVo = new UserExcelVo();
                BeanUtils.copyProperties(user, userExcelVo);
                userExcelVo.setAge("99");
                return userExcelVo;
            }
        });
        // 使用java8新特性的stream流去处理数据，把空的数据过滤掉
        /*List<UserExcelVo> resultNewList = resultList.stream().filter(Objects::isNull).map(t ->{
            return UserExcelVo.builder().name(t.getName()).age(t.getAge()).sex(t.getSex()).build();
        }).collect(Collectors.toList());*/

        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("用户信息导出", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        // 此处用自动适应列宽的策略
        EasyExcelFactory.write(response.getOutputStream(), UserExcelVo2.class).
                registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("sheet名称").doWrite(resultList);
    }

    @GetMapping("/exportExcel3")
    public void export3(HttpServletResponse response) throws IOException {
        StyleExcelHandler handler = new StyleExcelHandler();
        ServletOutputStream outputStream = response.getOutputStream();
        // 这里要把上面创建的样式类通过构造函数传入
        ExcelWriter writer = new ExcelWriter(null, outputStream, ExcelTypeEnum.XLSX, true, handler);
        Sheet sheet1 = new Sheet(1, 1, UserExcelVo2.class, "用户信息", null);
    }

    @RequestMapping(value = "/test/{id}/{name}/{age}", method = RequestMethod.POST)
    @ResponseBody
    public String method(@PathVariable Integer id, @PathVariable String name, @PathVariable Integer age) {
        String s = "hello " + id + " " + name + " " + age;
        System.out.println(s);
        return s;
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    @ResponseBody
    public String method2(@RequestParam("id") Integer id, @RequestParam("name") String name, @RequestParam("age") Integer age) {
        String s = "hello " + id + " " + name + " " + age;
        System.out.println(s);
        return s;
    }
}
