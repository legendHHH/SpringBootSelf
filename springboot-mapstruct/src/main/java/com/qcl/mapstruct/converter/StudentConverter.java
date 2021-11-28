package com.qcl.mapstruct.converter;

import com.qcl.mapstruct.entity.Student;
import com.qcl.mapstruct.enums.GenderEnum;
import com.qcl.mapstruct.vo.StudentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Student转StudentVO实体类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/11/28
 */
@Mapper
public interface StudentConverter {

    StudentConverter INSTANCE = Mappers.getMapper(StudentConverter.class);

    /**
     * @param student
     * @return
     */
    @Mapping(source = "gender.name", target = "gender")
    @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyy-MM-dd HH:mm:ss")
    StudentVO student2StudentVO(Student student);

    /**
     * @param gender
     * @return
     */
    default String getGenderName(GenderEnum gender) {
        return gender.getName();
    }
}