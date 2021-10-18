package com.qcl.excel.easyexcel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Function;
import com.qcl.excel.domain.User;
import com.qcl.excel.easyexcel.handler.StyleExcelHandler;
import com.qcl.excel.repository.UserRepository;
import com.qcl.excel.vo.TsOrderVoForEasyExport;
import com.qcl.excel.vo.UserExcelVo;
import com.qcl.excel.vo.UserExcelVo2;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;
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
            for (int i = 0; i < 15; i++) {
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
                List<Future<List<UserExcelVo>>> futures = exector.invokeAll(tasks);
                for (int i = 0; i < 15; i++) {
                    List<UserExcelVo> exifInfoList = futures.get(i).get();
                    LOGGER.info("1111：{}", exifInfoList.toString());
                    excelWriter.write(exifInfoList, writeSheet);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.info(e.getMessage());
            }

            long endTime = System.currentTimeMillis();
            LOGGER.info("总耗时：{}", (endTime - startTime));
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
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


    /**
     *
     * @return
     */
    @RequestMapping("/jdbc")
    @ResponseBody
    public Object method() {
        String fileName = "D:/" + "repeatedWrite22" + System.currentTimeMillis() + ".xlsx";
        ExcelWriterSheetBuilder sheetBuilder = EasyExcel.writerSheet("订单");
        ExcelWriter excelWriter = null;

        // 这里 需要指定写用哪个class去写
        excelWriter = EasyExcel.write(fileName, TsOrderVoForEasyExport.class).build();

        //合并策略
        //LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        //OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy(1, 6000, 0, 0);

        //需要合并的索引列
        int[] mergeColIndex = {0,1,2,3,4,5,6,7,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,32,33,34,38,39};
        //需要从第一行开始，列头第一行
        int mergeRowIndex = 1;
        ExcelFileCellMergeStrategy excelFileCellMergeStrategy = new ExcelFileCellMergeStrategy(mergeColIndex,mergeRowIndex);

        /*MergeStrategy mergeStrategy = new MergeStrategy(6000, 0, 1);
        MergeStrategy mergeStrategy3 = new MergeStrategy(6000,2,3);*/
        //MergeStrategy mergeStrategy3 = new MergeStrategy(6000,4, 5, 6, 7, 14, 15, 16, 17, 18, 19, 20, 26, 27, 28, 29, 33);

        // 这里注意 如果同一个sheet只要创建一次
        //WriteSheet writeSheet = EasyExcel.writerSheet("模板").registerWriteHandler(mergeStrategy).registerWriteHandler(mergeStrategy3).build();
        //WriteSheet writeSheet = EasyExcel.writerSheet("模板").registerWriteHandler(excelFileCellMergeStrategy).build();

        String sql = "";
        int corePoolSize = 10;
        int maximumPoolSize = 20;
        //用线程池管理多线程
        ThreadPoolExecutor exector = (ThreadPoolExecutor) Executors.newFixedThreadPool(corePoolSize);
        exector.setCorePoolSize(corePoolSize);
        exector.setMaximumPoolSize(maximumPoolSize);
        List<GenerateExcelInfoThread2> tasks = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        List<TsOrderVoForEasyExport> resultPro = new ArrayList<>();
        try {
            for (int i = 0; i < 81; i++) {
                //PageHelper.startPage(i - 1, 100);
                sql = "SELECT order_sn, pay_sn, order_type, order_from, buyer_name, buyer_phone, add_time, payment_time, sku_name, sku_code AS skuCode, item_no, spec, number, unit_price, goods_amount,( SELECT count(*) FROM ts_order_item WHERE order_id = ts_order.order_id) AS goods_kind, order_amount, pd_amount, voucher_price, shipping_fee, order_state, evaluation_state, delete_state, refund_state,( SELECT refund_amount FROM ts_refund_return WHERE order_id = ts_order.order_id LIMIT 1 ) AS refund_amount, ts_order_common.shipping_code, ts_order.store_id, mall_store.store_code, ts_order.store_name, ts_order.chain_id, mall_product_storage.NAME AS storage_name, ts_order.chain_code, mall_chain.chain_name, req_no, sku_activity_type, reciver_phone, reciver_name, reciver_info,( SELECT CODE FROM mem_vip_card mc WHERE mc.user_id = ts_order.buyer_id AND mc.store_id = ts_order.store_id AND mc.is_delete = 0 LIMIT 1 ) AS card_no, ts_order.employee_phone, platform_discount, merchant_discount, order_amount + ifnull( douli_amount, 0 )+ ifnull( pd_amount, 0 )+ ifnull( pukang_amount, 0 ) AS order_amount_origin, ifnull( douli_amount, 0 ) AS douli_amount, ifnull( pukang_amount, 0 ) AS pukang_amount, ts_order_item.commission, sp_share_clerk.share_name, ts_order_item.product_id, ts_order.share_phone, ts_order.share_name AS shareUserName, ts_order.share_type, ( SELECT channel_name FROM mall_channel m WHERE m.id = ts_order.channel_id ) AS channel_name FROM ts_order LEFT JOIN ts_order_common ON ts_order.order_id = ts_order_common.order_id LEFT JOIN ts_order_item ON ts_order.order_id = ts_order_item.order_id LEFT JOIN mall_store ON ts_order.store_id = mall_store.store_id LEFT JOIN mall_chain ON ts_order.chain_id = mall_chain.id LEFT JOIN mall_product_storage ON ts_order.storage_id = mall_product_storage.id LEFT JOIN sp_share_clerk ON ts_order_item.share_clerk_id = sp_share_clerk.share_clerk_id WHERE ( delete_state = 0 and order_state <> 0  and ts_order.store_id in ( 715 ) ) AND add_time >= '2021-01-29 23:59:59' ORDER by order_sn DESC " +
                        " limit  " + (i * 100) + ",100 ";

                List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql);
                List<TsOrderVoForEasyExport> listTsOrderVoForEasyExport = JSON.parseArray(JSON.toJSONString(listMap), TsOrderVoForEasyExport.class);
                resultPro.addAll(listTsOrderVoForEasyExport);

                /*GenerateExcelInfoThread2 readExifInfoThread = new GenerateExcelInfoThread2(jdbcTemplate, i, sql);
                tasks.add(readExifInfoThread);*/

                LOGGER.info("分页查询sql语句：{}", sql);
            }

            List<Future<List<TsOrderVoForEasyExport>>> futures = exector.invokeAll(tasks);

            //List<TsOrderVoForEasyExport> resultPro = new ArrayList<>();
            /*for (int i = 0; i < 81; i++) {
                LOGGER.info("处理数据：{}", i);
                List<TsOrderVoForEasyExport> exifInfoList = futures.get(i).get();
                //LOGGER.info("多线程获取返回的数据:{}" ,exifInfoList.toString());
                resultPro.addAll(exifInfoList);

                //Map<String, List<RowRangeDto>> strategyMap = addMerStrategy(exifInfoList);

                *//*if (i <= 40) {
                    excelWriter.write(exifInfoList, EasyExcel.writerSheet("模板f").build());
                } else {
                    excelWriter.write(exifInfoList, EasyExcel.writerSheet("模板f2").build());
                }*//*

                LOGGER.info("处理数据完成：", i);
            }*/

            Map<String, List<RowRangeDto>> strategyMap = addMerStrategy(resultPro);
            excelWriter.write(resultPro, EasyExcel.writerSheet("模板").registerWriteHandler(new BizMergeStrategy(strategyMap)).build());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("总耗时：{}", (endTime - startTime));
        //List<Map<String, Object>> list = userRepository.query();
        return "总耗时：" + (endTime - startTime);
    }


    public static void readExcel(String fileName){
        File file = new File("D://repeatedWrite221632814895910.xlsx");
        ExcelListener excelListener = new ExcelListener();
        ExcelReader excelReader = EasyExcel.read(fileName, TsOrderVoForEasyExport.class, excelListener).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();

//        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
//        ExcelWriter excelWriter = EasyExcel.write("12345" + fileName, TsOrderVoForEasyExport.class).build();
//        excelWriter.finish();

        Workbook book = POIExcelUtil.readExcelFromFile(file);
        org.apache.poi.ss.usermodel.Sheet sheetAt = book.getSheetAt(0);
        Workbook megreWorkbook = new XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = megreWorkbook.createSheet(book.getSheetName(0));

        //POIExcelUtil.cloneSheet(file, 2, "模板");

        /*List<Object> list = excelListener.getDatas();
        list.forEach((tsOrder) -> {
            TsOrderVoForEasyExport import1 = (TsOrderVoForEasyExport) tsOrder;
            System.out.println(import1.getOrderSn() + ", " + import1.getBuyerName());
        });*/

        /*//新建监听器
        ExcelListener listener = new ExcelListener();
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file);
        ExcelReader excelReader = excelReaderBuilder.build();
        List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
        for (ReadSheet sheet : sheets) {


        }
        excelReader.read(sheets);

        List<Object> list = listener.getDatas();
        list.forEach((tsOrder) -> {
            TsOrderVoForEasyExport import1 = (TsOrderVoForEasyExport) tsOrder;
            System.out.println(import1.getOrderSn() + ", " + import1.getBuyerName());
        });
        // 清空之前的数据
        listener.getDatas().clear();*/

        //获取加载生成的excel
        //判断sheet的页数
        //获取第二个sheet开始  判断第一个sheet的最后一条数据的位置 然后追加数据合并sheet

        //将最终的文件上传到obs
    }


    public static void main(String[] args) {
        readExcel("D://repeatedWrite221632814895910.xlsx");
    }

    /**
     * @Description: 添加合并策略
     * @Param:
     * @return:
     **/
    private Map<String, List<RowRangeDto>> addMerStrategy(List<TsOrderVoForEasyExport> excelDtoList) {
        Map<String, List<RowRangeDto>> strategyMap = new HashMap<>(16);
        long startTime = System.currentTimeMillis();

        TsOrderVoForEasyExport preExcelDto = null;
        for (int i = 0; i < excelDtoList.size(); i++) {
            TsOrderVoForEasyExport currDto = excelDtoList.get(i);
            if (preExcelDto != null) {
                //从第二行开始判断是否需要合并
                if (currDto.getOrderSn().equals(preExcelDto.getOrderSn())) {
                    //如果订单号一样，则可合并订单主单相关列
                    fillStrategyMap(strategyMap, "0", i);
                    fillStrategyMap(strategyMap, "1", i);
                    fillStrategyMap(strategyMap, "2", i);
                    fillStrategyMap(strategyMap, "3", i);
                    fillStrategyMap(strategyMap, "4", i);
                    fillStrategyMap(strategyMap, "5", i);
                    fillStrategyMap(strategyMap, "6", i);
                    fillStrategyMap(strategyMap, "7", i);
                    fillStrategyMap(strategyMap, "14", i);
                    fillStrategyMap(strategyMap, "15", i);
                    fillStrategyMap(strategyMap, "16", i);
                    fillStrategyMap(strategyMap, "17", i);
                    fillStrategyMap(strategyMap, "18", i);
                    fillStrategyMap(strategyMap, "19", i);
                    fillStrategyMap(strategyMap, "20", i);
                    fillStrategyMap(strategyMap, "21", i);
                    fillStrategyMap(strategyMap, "22", i);
                    fillStrategyMap(strategyMap, "23", i);
                    fillStrategyMap(strategyMap, "24", i);
                    fillStrategyMap(strategyMap, "25", i);
                    fillStrategyMap(strategyMap, "26", i);
                    fillStrategyMap(strategyMap, "27", i);
                    fillStrategyMap(strategyMap, "28", i);
                    fillStrategyMap(strategyMap, "29", i);
                    fillStrategyMap(strategyMap, "32", i);
                    fillStrategyMap(strategyMap, "33", i);
                    fillStrategyMap(strategyMap, "34", i);
                    fillStrategyMap(strategyMap, "38", i);
                    fillStrategyMap(strategyMap, "39", i);
                }
            }
            preExcelDto = currDto;
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("合并策略总耗时：{}", (endTime - startTime));
        return strategyMap;
    }

    /**
     * @Description: 新增或修改合并策略map
     * @Param:
     * @return:
     **/
    private void fillStrategyMap(Map<String, List<RowRangeDto>> strategyMap, String key, int index) {
        List<RowRangeDto> rowRangeDtoList = strategyMap.get(key) == null ? new ArrayList<>() : strategyMap.get(key);
        boolean flag = false;
        for (RowRangeDto dto : rowRangeDtoList) {
            //分段list中是否有end索引是上一行索引的，如果有，则索引+1
            if (dto.getEnd() == index) {
                dto.setEnd(index + 1);
                flag = true;
            }
        }
        //如果没有，则新增分段
        if (!flag) {
            rowRangeDtoList.add(new RowRangeDto(index, index + 1));
        }
        strategyMap.put(key, rowRangeDtoList);
    }
}
