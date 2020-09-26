package com.qcl.permission.controller;

import com.qcl.permission.common.JsonData;
import com.qcl.permission.dto.DeptLevelDto;
import com.qcl.permission.param.DeptParam;
import com.qcl.permission.service.ISysDeptService;
import com.qcl.permission.service.ISysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门controller
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/26
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private ISysDeptService sysDeptService;
    @Resource
    private ISysTreeService sysTreeService;

    /**
     * 返回页面
     *
     * @return
     */
    @RequestMapping("/dept.page")
    public ModelAndView page() {
        return new ModelAndView("dept");
    }

    /**
     * 新增保存部门
     * http://localhost:8080/permission/sys/dept/save.json?name=%E6%8A%80%E6%9C%AF%E9%83%A8&seq=1&remark=%E6%8A%80%E6%9C%AF%E9%83%A8
     * <p>
     * curl http://localhost:8080/permission/sys/dept/save.json\?name\=技术部\&seq\=1\&remark\=技术部
     * curl http://localhost:8080/permission/sys/dept/save.json\?name\=后端开发\&seq\=1\&parentId\=1
     * curl http://localhost:8080/permission/sys/dept/save.json\?name\=前端开发\&seq\=2\&parentId\=1
     *
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param) {
        //保存部门
        sysDeptService.save(param);
        return JsonData.success();
    }

    /**
     * 部门树接口
     * http://localhost:8080/permission/sys/dept/tree.json
     *
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    /**
     * 更新部门接口
     * http://localhost:8080/permission/sys/dept/update.json?id=2&name=后端开发&remark=后端开发
     *
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param) {
        sysDeptService.update(param);
        return JsonData.success();
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") int id) {
        sysDeptService.delete(id);
        return JsonData.success();
    }

}
