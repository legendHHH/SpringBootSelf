package com.qcl.permission.dto;

import com.google.common.collect.Lists;
import com.qcl.permission.model.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 权限模块树
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/4
 */
@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule {

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

    private List<AclDto> aclList = Lists.newArrayList();

    /**
     * 适配
     *
     * @param aclModule
     * @return
     */
    public static AclModuleLevelDto adapt(SysAclModule aclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(aclModule, dto);
        return dto;
    }
}
