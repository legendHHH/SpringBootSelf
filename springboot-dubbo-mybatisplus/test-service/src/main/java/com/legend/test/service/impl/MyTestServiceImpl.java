package com.legend.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.legend.test.entity.Test;
import com.legend.test.mapper.TestMapper;
import com.legend.test.service.ITestService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0",interfaceClass = ITestService.class )
public class MyTestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {
 
}
