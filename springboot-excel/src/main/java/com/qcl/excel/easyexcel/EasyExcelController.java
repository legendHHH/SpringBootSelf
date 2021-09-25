package com.qcl.excel.easyexcel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Function;
import com.qcl.excel.domain.User;
import com.qcl.excel.easyexcel.handler.StyleExcelHandler;
import com.qcl.excel.repository.UserRepository;
import com.qcl.excel.vo.UserExcelVo;
import com.qcl.excel.vo.UserExcelVo2;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    private Logger LOGGER = LoggerFactory.getLogger(EasyExcelController.class);

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

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    /**
     * http://localhost:8080/test2
     * @return
     */
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    @ResponseBody
    public String method2(HttpServletResponse response) throws Exception{
        Map<String, Object> taskMap = new HashMap<>(16);
        taskMap.put("taskKey", "1_1631531028274_pukangOrderExport");
        taskMap.put("taskExcelFileName", "普康平台订单销售报表20210913190348.xlsx");
        String fileName = (String) taskMap.get("taskExcelFileName");
        String taskKey = (String) taskMap.get("taskKey");

        FileOutputStream out = null;
        FileInputStream fileInputStream = null;
        WriteSheet writeSheet = EasyExcel.writerSheet("普康对账订单").build();

        // 这里 需要指定写用哪个class去写
        final ExcelWriter excelWriter = EasyExcel.write(fileName, UserExcelVo.class).build();

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        //String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");

        // 文件队列
        LinkedBlockingQueue<List<UserExcelVo>> queue = new LinkedBlockingQueue<>();
        //LinkedBlockingQueue<File> htmls = new LinkedBlockingQueue<>();

        UserExcelVo userExcelVoFirst = new UserExcelVo();
        userExcelVoFirst.setAge("1234567");
        userExcelVoFirst.setAddress("第一次");
        userExcelVoFirst.setId(-1);
        queue.put(Arrays.asList(userExcelVoFirst));

        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    List<UserExcelVo> userList = queue.take();
                    LOGGER.info("用户数据：{}", JSONUtil.toJsonStr(userList));
                    if (userList.size() == 0) {
                        taskMap.put("state", 200);
                        taskMap.put("message", "下载成功");
                        break;
                    }
                    excelWriter.write(userList, writeSheet);
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });

        thread.setDaemon(true);
        thread.start();

        //分页参数
        int pageNum = 40;
        int pageSize = 100;


        try {
            //初始化数据
            List<UserExcelVo> resultList = new ArrayList<>();
            while (true) {

                boolean countFlag = false;
                if (pageNum == 1) {
                    countFlag = true;
                }
                PageHelper.startPage(pageNum, pageSize, countFlag);
                Pageable pageable=new PageRequest(pageNum, pageSize);

                //查询订单信息
                org.springframework.data.domain.Page<User> userList = userRepository.findAll(pageable);
                if (userList != null) {
                    userList.forEach(user -> {
                        UserExcelVo userExcelVo = new UserExcelVo();
                        BeanUtils.copyProperties(user, userExcelVo);
                        resultList.add(userExcelVo);
                    });
                }

                if (pageNum == 50) {
                    //excelWriter.write(resultList, writeSheet);
                    queue.put(new ArrayList<>());
                    break;
                }
                LOGGER.info("当前页数：{},总计页数：{}, 页面数据：{}", pageNum, 1, resultList.toString());
                excelWriter.write(resultList, writeSheet);

                queue.put(resultList);
                pageNum++;
            }
            thread.join();

        } catch (Exception e) {
            e.printStackTrace();

            taskMap.put("state", -1);
            taskMap.put("message", e.getMessage());

            LOGGER.error("普康对账订单执行导出失败:{}", e.getMessage());
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
        return null;
    }


    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    @ResponseBody
    public void repeatedWrite(HttpServletResponse response) throws Exception{
        // 方法1 如果写到同一个sheet
        String fileName = "D:/" + "repeatedWrite" + System.currentTimeMillis() + ".xlsx";
        ExcelWriterSheetBuilder sheetBuilder = EasyExcel.writerSheet("订单");
        ExcelWriter excelWriter = null;
        try {
            // 这里 需要指定写用哪个class去写
            excelWriter = EasyExcel.write(fileName, UserExcelVo.class).build();
            LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
            // 这里注意 如果同一个sheet只要创建一次
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").registerWriteHandler(loopMergeStrategy).build();

            List<UserExcelVo> data = new ArrayList<>();

            AtomicInteger atomicInteger = new AtomicInteger(0);
            int corePoolSize=10;
            int maximumPoolSize=20;
            //用线程池管理多线程
            ThreadPoolExecutor exector = (ThreadPoolExecutor) Executors.newFixedThreadPool(corePoolSize);
            exector.setCorePoolSize(corePoolSize);
            exector.setMaximumPoolSize(maximumPoolSize);
            List<GenerateExcelInfoThread> tasks = new ArrayList<>();

            /*final ExcelWriter excelWriterTemp = excelWriter;
            LinkedBlockingQueue<List<UserExcelVo>> queue = new LinkedBlockingQueue<>();

            Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        List<UserExcelVo> dataTemp = queue.take();
                        LOGGER.info("用户数据：{}", JSONUtil.toJsonStr(dataTemp));
                        if (dataTemp.size() == 0) {
                            break;
                        }
                        excelWriterTemp.write(dataTemp, writeSheet);
                        dataTemp.clear();
                    }

                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            });

            thread.start();*/

            long startTime = System.currentTimeMillis();
            // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来
            for (int i = 0; i < 1500; i++) {
                //System.out.println("当前开始执行操作：" + i);
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                //List<UserExcelVo> data = data();

                //异步线程队列
                /*Pageable pageable = new PageRequest(i + 1, 100);
                System.out.println("当前页数："+pageable.getPageNumber());
                //查询订单信息
                org.springframework.data.domain.Page<User> userList = userRepository.findAll(pageable);
                if (userList != null) {
                    userList.forEach(user -> {
                        UserExcelVo userExcelVo = new UserExcelVo();
                        BeanUtils.copyProperties(user, userExcelVo);
                        data.add(userExcelVo);
                    });
                }
                queue.put(data);*/

                //创建异步任务线程任务
                GenerateExcelInfoThread readExifInfoThread = new GenerateExcelInfoThread(userRepository, i);
                tasks.add(readExifInfoThread);

                /*Pageable pageable = new PageRequest(i + 1, 100);
                System.out.println("当前页数："+pageable.getPageNumber());
                //查询订单信息
                org.springframework.data.domain.Page<User> userList = userRepository.findAll(pageable);
                if (userList != null) {
                    userList.forEach(user -> {
                        UserExcelVo userExcelVo = new UserExcelVo();
                        BeanUtils.copyProperties(user, userExcelVo);
                        data.add(userExcelVo);
                    });
                }
                //excelWriter.write(data, writeSheet);
                excelWriter.write(data, writeSheet);
                data.clear();*/
            }

            //thread.join();

            try {
                //用invokeAll方法提交任务，返回数据的顺序和tasks中的任务顺序一致，如果第一个线程查0-10000行数据，第二个线程查10000-10001行数据，
                //第二个线程大概率比第一个线程先执行完，但是futures中第一位数据是0-10000的数据。
                List<Future<List<UserExcelVo>>> futures = exector.invokeAll(tasks);
                for (int i = 0; i < 1500; i++) {
                    List<UserExcelVo> exifInfoList = futures.get(i).get();
                    System.out.println("1111:"+exifInfoList.toString());
                    excelWriter.write(exifInfoList, writeSheet);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }


            long endTime = System.currentTimeMillis();
            System.out.println("总耗时：" + (endTime - startTime));
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

        /*writer.write(resultPro,sheetBuilder
                .registerWriteHandler(new BizMergeStrategy(strategyMap))
                .build());*/

    }

    private List<UserExcelVo> data() {
        int count = 1;
        List<UserExcelVo> userExcelVoList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            count = count + i;
            UserExcelVo userExcelVo = new UserExcelVo();
            userExcelVo.setId(count);
            userExcelVo.setAddress(UUID.randomUUID().toString());

            userExcelVoList.add(userExcelVo);

        }
        return userExcelVoList;
    }

}
