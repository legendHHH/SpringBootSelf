package com.qcl.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcl.mybatisplus.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户Mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/5/13
 */
//@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 根据id查询(测试起别名)
     *
     * @param id
     * @return
     */
    User findById2(Integer id);
}
