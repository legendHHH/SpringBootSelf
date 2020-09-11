package com.qcl.datasource.controller;


import com.qcl.datasource.bean.Area;
import com.qcl.datasource.common.CommonResult;
import com.qcl.datasource.mapper.TestMapper;
import com.qcl.datasource.mapper.test01.Test1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用递归获取树结构(test.hjmall_area)
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/11
 */
@RestController
public class TestController {

    @Autowired
    private Test1Mapper testMapper;


    /**
     * @param
     * @return CommonResult
     * @Description 获取树形结构数据
     **/
    @GetMapping("/getLabelTree")
    public CommonResult getAreaLabelTree() {
        List<Area> areaList = testMapper.selectAll();
        List<Area> resultList = new ArrayList<>();
        for (Area area : areaList) {
            String areaParentCode = area.getAreaParentCode();
            if (StringUtils.isEmpty(areaParentCode)) {
                //说明是最顶层的菜单 将这些数据
                //areaList.stream().collect(Collectors.toMap(Area::getAreaCode, areaTemp -> areaTemp));
                List<Area> firstNode = areaList.stream().collect(Collectors.groupingBy(Area::getAreaCode)).get(areaParentCode);
                System.out.println("获取的第一层节点" + firstNode.toString());
                resultList.addAll(firstNode);
            }
        }
        return new CommonResult(200, "成功", resultList);
    }
}
