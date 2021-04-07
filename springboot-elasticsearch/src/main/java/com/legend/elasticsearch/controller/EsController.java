package com.legend.elasticsearch.controller;

import com.legend.elasticsearch.utils.EsCurl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Es控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/4/7
 */
@RequestMapping("/es")
@RestController
public class EsController {

    @Autowired
    private EsCurl esCurl;

    /**
     * 浏览器直接访问：
     * (sku维度) http://localhost:8012/es/queryEsInfo?indexName=sku1&typeName=sku_type&id=5029935
     * (product维度) http://localhost:8012/es/queryEsInfo?indexName=product2&typeName=product_type&id=4659157
     *
     * @param indexName  索引index
     * @param typeName   索引类型
     * @param esDealType es操作类型
     * @param id         业务主键
     * @return
     */
    @GetMapping(value = "/queryEsInfo")
    @ResponseBody
    public String queryEsByIdAndIndexAndName(@RequestParam("indexName") String indexName,
                                             @RequestParam("typeName") String typeName,
                                             @RequestParam(value = "esDealType", required = false) String esDealType,
                                             @RequestParam(value = "id", required = false) Integer id) {

        return esCurl.queryEsByIndexAndName(indexName, typeName, id, esDealType);
    }
}
