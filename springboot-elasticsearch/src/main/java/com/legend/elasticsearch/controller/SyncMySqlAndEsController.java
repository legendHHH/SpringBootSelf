package com.legend.elasticsearch.controller;

import com.legend.elasticsearch.service.MySqlToEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 同步Mysql和Es数据控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/11/24
 */
@RestController
public class SyncMySqlAndEsController {

    @Autowired
    private MySqlToEsService mySqlToEsService;

    /**
     * http://localhost:8012/es
     *
     * @return
     */
    @GetMapping("/mysql-to-es")
    public String mysqlToEs(){
        try {
            mySqlToEsService.createAndUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "完成";
    }

    /**
     * http://localhost:8012/es
     *
     * @return
     */
    @GetMapping("/es")
    public String queryEsDataOfMysql(){
        try {
            mySqlToEsService.select();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "完成";
    }

    /**
     * http://localhost:8012/ies
     *
     * @return
     */
    @GetMapping("/ies")
    public String insertOneDataToEs(){
        try {
            mySqlToEsService.insertOneDataToEs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "完成";
    }
}
