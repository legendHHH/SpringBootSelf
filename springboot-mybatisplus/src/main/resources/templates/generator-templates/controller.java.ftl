package ${package.Controller};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Mapper}.${table.mapperName};
import com.example.demo.utils.ResponseDataVO;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

<#if restControllerStyle>
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

import java.util.Arrays;
import javax.validation.Valid;
import java.util.List;

/**
* <p>
* ${table.comment} 前端控制器
* </p>
*
* @author ${author}
* @since ${date}
*/
@Api(tags = "${table.comment}")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
    class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
    <#if superControllerClass??>
        public class ${table.controllerName} extends ${superControllerClass} {
    <#else>
        public class ${table.controllerName} {
    </#if>

    @Autowired
    private ${table.serviceName} ${(table.serviceName?substring(1))?uncap_first};
    @Autowired
    private ${table.mapperName} ${table.mapperName?uncap_first};

    @ApiOperation(value = "${table.comment}新增单个实体对象")
    @PostMapping(value = "/add")
    public ResponseDataVO add(@Valid @RequestBody ${entity} param) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = ${(table.serviceName?substring(1))?uncap_first}.save(param);
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


    @ApiOperation(value = "${table.comment}批量新增实体对象")
    @PostMapping(value = "/add/list")
    public ResponseDataVO add(@Valid @RequestBody List<${entity}> param) {
    ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = ${(table.serviceName?substring(1))?uncap_first}.saveBatch(param);
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


    @ApiOperation(value = "${table.comment}删除(单个条目)")
    @DeleteMapping(value = "/remove/{id}")
    public ResponseDataVO remove(@PathVariable Long id) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = ${(table.serviceName?substring(1))?uncap_first}.removeById(id);
        if (result) {
            responseDataVO.setCode("200");
            responseDataVO.setSuccess(true);
        } else {
            responseDataVO.setCode("500");
            responseDataVO.setSuccess(false);
        }
        return responseDataVO;
     }


    @ApiOperation(value = "${table.comment}删除(多个条目)")
    @PostMapping(value = "/removes")
    public  Object removes(@Valid @RequestBody List<Long> ids) {

        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean result = ${(table.serviceName?substring(1))?uncap_first}.removeByIds(ids);
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

    @ApiOperation(value = "${table.comment}修改")
    @PostMapping(value = "/modify")
    public  ResponseDataVO modify(@Valid @RequestBody ${entity} param) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        boolean update = ${(table.serviceName?substring(1))?uncap_first}.updateById(param);
        if (update) {
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


    @ApiOperation(value = "${table.comment}分页列表", response = ${entity}.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
            @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
            @ApiImplicitParam(name = "sort", value = "排序方式排序[true:正序; false:倒序]", dataType = "Boolean"),
            @ApiImplicitParam(name = "sortName", value = "排序字段,参照返回字段", dataType = "String")})
    @PostMapping(value = "/page")
    public  ResponseDataVO list(@Valid @RequestBody ${entity} param) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        Page<${package.Entity}.${entity}> page = ${(table.mapperName?uncap_first)}.selectPage(new Page<>(0, 2), null);
        responseDataVO.setRows(page.getRecords());
        responseDataVO.setTotal(page.getTotal());
        responseDataVO.setCode("200");
        responseDataVO.setSuccess(true);
        return responseDataVO;
    }

    @ApiOperation(value = "${table.comment}详情", response = ${entity}.class)
    @GetMapping(value = "/info/{id}")
    public  ResponseDataVO info(@PathVariable Long id) {
        ResponseDataVO responseDataVO = new ResponseDataVO();
        ${entity} data = ${(table.serviceName?substring(1))?uncap_first}.getById(id);
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
</#if>