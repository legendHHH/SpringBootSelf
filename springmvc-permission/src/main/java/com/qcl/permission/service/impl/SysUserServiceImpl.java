package com.qcl.permission.service.impl;

import com.google.common.base.Preconditions;
import com.qcl.permission.common.RequestHolder;
import com.qcl.permission.exception.ParamException;
import com.qcl.permission.mapper.SysUserMapper;
import com.qcl.permission.model.SysUser;
import com.qcl.permission.param.UserParam;
import com.qcl.permission.service.ISysUserService;
import com.qcl.permission.utils.BeanValidator;
import com.qcl.permission.utils.IpUtil;
import com.qcl.permission.utils.MD5Util;
import com.qcl.permission.utils.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户ServiceImpl
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/27
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
//    @Resource
//    private SysLogService sysLogService;

    @Override
    public void save(UserParam param) {
        BeanValidator.check(param);
        //校验电话号码是否被占用
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        //校验邮箱是否被占用
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }

        String password = PasswordUtil.randomPassword();
        //password = "12345678";
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();
        user.setOperator(RequestHolder.getCurrentUser().getUsername());
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());

        // TODO: sendEmail

        sysUserMapper.insertSelective(user);
        sysLogService.saveUserLog(null, user);
    }

    @Override
    public void update(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        //校验对象是否为空
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveUserLog(before, after);
    }

    /**
     * 校验邮箱是否被占用
     *
     * @param mail
     * @param userId
     * @return
     */
    public boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    /**
     * 校验电话号码是否被占用
     *
     * @param telephone
     * @param userId
     * @return
     */
    public boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }

    @Override
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
        BeanValidator.check(page);
        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0) {
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, page);
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    @Override
    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }
}
