package com.legend.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.legend.test.entity.Test;
import org.apache.ibatis.annotations.Mapper;

/**
 * TestMapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/7
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {

}
