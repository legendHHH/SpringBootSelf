package com.qcl.permission.dto;

import com.qcl.permission.model.SysAcl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * 权限树
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/4
 */
@Getter
@Setter
@ToString
public class AclDto extends SysAcl {

    /**
     * 是否要默认选中
     */
    private boolean checked = false;

    /**
     * 是否有权限操作
     */
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl, dto);
        return dto;
    }
}
