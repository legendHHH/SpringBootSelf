package com.qcl.permission.service;

import com.qcl.permission.param.AclModuleParam;

public interface ISysAclModuleService {

    public void save(AclModuleParam param);

    public void update(AclModuleParam param);

    public void delete(int aclModuleId);

}
