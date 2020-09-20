package com.qcl.mybatisplus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qcl.mybatisplus.entity.Employee;
import com.qcl.mybatisplus.mapper.EmployeeMapper;
import com.qcl.mybatisplus.service.IEmployeeService;
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
 * 员工控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/20
 */
@Slf4j
@Api(tags = "员工Controller测试")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private EmployeeMapper employeeMapper;


    @ApiOperation(value = "测试批量新增实体对象")
    @PostMapping(value = "/add/list")
    public ResponseDataVO add(@Valid @RequestBody List<Employee> employeeList) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = employeeService.saveBatch(employeeList);
        if (result) {
            responseDataVO.setRows(employeeList);
            responseDataVO.setCode("200");
            responseDataVO.setSuccess(true);
        } else {
            responseDataVO.setRows(employeeList);
            responseDataVO.setCode("500");
            responseDataVO.setSuccess(false);
        }
        return responseDataVO;
    }


    @ApiOperation(value = "测试删除(单个条目)")
    @DeleteMapping(value = "/remove/{id}")
    public Boolean remove(@PathVariable Long id) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = employeeService.removeById(id);
        return result;
    }


    @ApiOperation(value = "测试删除(多个条目)")
    @PostMapping(value = "/removes")
    public Object removes(@Valid @RequestBody List<Long> ids) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = employeeService.removeByIds(ids);
        responseDataVO.setMessage(result + "");
        return responseDataVO;
    }

    @ApiOperation(value = "测试修改")
    @PostMapping(value = "/modify")
    public Object modify(@Valid @RequestBody Employee employee) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = employeeService.updateById(employee);
        if (result) {
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


    @ApiOperation(value = "测试分页列表", response = Employee.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
            @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
            @ApiImplicitParam(name = "sort", value = "排序方式排序[true:正序; false:倒序]", dataType = "Boolean"),
            @ApiImplicitParam(name = "sortName", value = "排序字段,参照返回字段", dataType = "String")})
    @PostMapping(value = "/page")
    public ResponseDataVO list(@Valid @RequestBody Employee employee) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        Page<Employee> page = employeeMapper.selectPage(new Page<>(0, 2), null);
        responseDataVO.setRows(page.getRecords());
        responseDataVO.setTotal(page.getTotal());
        responseDataVO.setCode("200");
        responseDataVO.setSuccess(true);
        return responseDataVO;
    }

    @ApiOperation(value = "测试详情", response = Employee.class)
    @GetMapping(value = "/info/{id}")
    public ResponseDataVO info(@PathVariable("id") Integer id) {
        log.info(">>>>>>{}", id);
        ResponseDataVO responseDataVO = new ResponseDataVO();
        Employee data = employeeService.getById(id);
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
