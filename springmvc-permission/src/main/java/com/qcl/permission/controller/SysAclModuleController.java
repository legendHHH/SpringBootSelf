package com.qcl.permission.controller;

import com.qcl.permission.common.JsonData;
import com.qcl.permission.param.AclModuleParam;
import com.qcl.permission.service.ISysAclModuleService;
import com.qcl.permission.service.ISysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 权限模块控制器
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/4
 */
@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Resource
    private ISysAclModuleService sysAclModuleService;
    @Resource
    private ISysTreeService sysTreeService;

    /**
     *
     * @return
     */
    @RequestMapping("/acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModuleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModuleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        //权限模块树
        return JsonData.success(sysTreeService.aclModuleTree());
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") int id) {
        sysAclModuleService.delete(id);
        return JsonData.success();
    }
}
