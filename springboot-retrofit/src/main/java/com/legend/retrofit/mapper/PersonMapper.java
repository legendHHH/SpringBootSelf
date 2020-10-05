package com.legend.retrofit.mapper;

import com.legend.retrofit.entity.Person;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/5
 */
@Mapper
public interface PersonMapper {

    int savePerson(Person person);

    Person getPerson(Long id);
}
