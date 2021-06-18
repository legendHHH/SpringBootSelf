package com.qcl.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcl.shardingjdbc.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/19
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}