package com.qcl.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcl.shardingjdbc.entity.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/2
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
}