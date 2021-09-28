package com.qcl.excel.easyexcel;

import com.alibaba.fastjson.JSON;
import com.qcl.excel.domain.User;
import com.qcl.excel.repository.UserRepository;
import com.qcl.excel.vo.TsOrderVoForEasyExport;
import com.qcl.excel.vo.UserExcelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class GenerateExcelInfoThread2 implements Callable<List<TsOrderVoForEasyExport>> {
    private int i;
    private String sql;
    private JdbcTemplate jdbcTemplate;

    public GenerateExcelInfoThread2(JdbcTemplate jdbcTemplate, int i, String sql) {
        this.jdbcTemplate = jdbcTemplate;
        this.sql = sql;
        this.i = i;
    }

    @Override
    public List<TsOrderVoForEasyExport> call() {
        long startTime = System.currentTimeMillis();
        List<TsOrderVoForEasyExport> exifInfoList = new ArrayList<>();
        try {
            //从数据库查询要写入excel的数据
            List<Map<String, Object>> listMap = jdbcTemplate.queryForList(sql);
            List<TsOrderVoForEasyExport> listTsOrderVoForEasyExport = JSON.parseArray(JSON.toJSONString(listMap), TsOrderVoForEasyExport.class);
            log.info("数据返回：{}", listTsOrderVoForEasyExport.size());
            exifInfoList.addAll(listTsOrderVoForEasyExport);

            long endTime = System.currentTimeMillis();
            long spendTime = endTime - startTime;
            log.info(Thread.currentThread().getName() + "查询耗时：" + spendTime);
        } catch (Exception e) {
            log.error("查询数据失败");
        }
        return exifInfoList;
    }
}