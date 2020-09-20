package com.qcl.mybatisplus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qcl.mybatisplus.entity.Test;
import com.qcl.mybatisplus.mapper.TestMapper;
import com.qcl.mybatisplus.service.ITestService;
import com.qcl.mybatisplus.vo.ResponseDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * 测试控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/20
 */
@Slf4j
@Api(tags = "测试")
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ITestService testService;

    @Autowired
    private TestMapper testMapper;


    @ApiOperation(value = "测试新增单个实体对象")
    @PostMapping(value = "/add")
    public ResponseDataVO add(@Valid @RequestBody Test param) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = testService.save(param);
        if (result) {
            responseDataVO.setRows(Arrays.asList(param));
            responseDataVO.setCode("200");
            responseDataVO.setSuccess(true);
        } else {
            responseDataVO.setRows(Arrays.asList(param));
            responseDataVO.setCode("500");
            responseDataVO.setSuccess(false);
        }
        return responseDataVO;
    }


    @ApiOperation(value = "测试批量新增实体对象")
    @PostMapping(value = "/add/list")
    public ResponseDataVO add(@Valid @RequestBody List<Test> param) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = testService.saveBatch(param);
        if (result) {
            responseDataVO.setRows(param);
            responseDataVO.setCode("200");
            responseDataVO.setSuccess(true);
        } else {
            responseDataVO.setRows(param);
            responseDataVO.setCode("500");
            responseDataVO.setSuccess(false);
        }
        return responseDataVO;
    }


    @ApiOperation(value = "测试删除(单个条目)")
    @DeleteMapping(value = "/remove/{id}")
    public ResponseDataVO remove(@PathVariable Long id) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = testService.removeById(id);
        if (result) {
            responseDataVO.setCode("200");
            responseDataVO.setSuccess(true);
        } else {
            responseDataVO.setCode("500");
            responseDataVO.setSuccess(false);
        }
        return responseDataVO;
    }


    @ApiOperation(value = "测试删除(多个条目)")
    @PostMapping(value = "/removes")
    public Object removes(@Valid @RequestBody List<Long> ids) {

        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = testService.removeByIds(ids);
        if (result) {
            responseDataVO.setCode("200");
            responseDataVO.setMessage("删除成功");
            responseDataVO.setSuccess(true);
        } else {
            responseDataVO.setCode("500");
            responseDataVO.setMessage("删除失败");
            responseDataVO.setSuccess(false);
        }
        return responseDataVO;
    }

    @ApiOperation(value = "测试修改")
    @PostMapping(value = "/modify")
    public Object modify(@Valid @RequestBody Test param) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        int count = testMapper.updateById(param);
        if (count > 0) {
            responseDataVO.setCode("200");
            responseDataVO.setMessage("更新成功");
            responseDataVO.setSuccess(true);
        } else {
            responseDataVO.setCode("500");
            responseDataVO.setMessage("更新失败");
            responseDataVO.setSuccess(false);
        }
        return responseDataVO;

    }


    @ApiOperation(value = "测试分页列表", response = Test.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
            @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
            @ApiImplicitParam(name = "sort", value = "排序方式排序[true:正序; false:倒序]", dataType = "Boolean"),
            @ApiImplicitParam(name = "sortName", value = "排序字段,参照返回字段", dataType = "String")})
    @PostMapping(value = "/page")
    public ResponseDataVO list(@Valid @RequestBody Test param) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        Page<Test> page = testMapper.selectPage(new Page<>(0, 2), null);
        responseDataVO.setRows(page.getRecords());
        responseDataVO.setTotal(page.getTotal());
        responseDataVO.setCode("200");
        responseDataVO.setSuccess(true);
        return responseDataVO;
    }

    @ApiOperation(value = "测试详情", response = Test.class)
    @GetMapping(value = "/info/{id}")
    public ResponseDataVO info(@PathVariable("id") Integer id) {
        log.info(">>>>>>{}", id);
        ResponseDataVO responseDataVO = new ResponseDataVO();
        Test data = testService.getById(id);
        if (data != null) {
            responseDataVO.setRows(Arrays.asList(data));
            responseDataVO.setTotal(1L);
            responseDataVO.setCode("200");
            responseDataVO.setSuccess(true);
        } else {
            responseDataVO.setTotal(0L);
            responseDataVO.setCode("404");
            responseDataVO.setSuccess(false);
        }

        return responseDataVO;
    }
}
