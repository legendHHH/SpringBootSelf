package com.qcl.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcl.mybatisplus.entity.Test;
import com.qcl.mybatisplus.mapper.TestMapper;
import com.qcl.mybatisplus.service.ITestService;
import org.springframework.stereotype.Service;

/**
 * ServiceImpl
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/20
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
