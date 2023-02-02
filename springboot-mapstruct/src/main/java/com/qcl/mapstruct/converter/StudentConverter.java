package com.qcl.mapstruct.converter;

import com.qcl.mapstruct.entity.Course;
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

    @Mapping(source = "student.gender.name", target = "gender")
    @Mapping(source = "student.birthday", target = "birthday", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "course.courseName", target = "course")
    @Mapping(target = "address", source = "student.address", defaultValue = "河北省")
    StudentVO studentAndCourse2StudentVO(Student student, Course course);

    /**
     * student对象转studentVO
     *
     * @param student
     * @return
     */
    @Mapping(source = "gender.name", target = "gender")
    @Mapping(source = "birthday", target = "birthday", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "address", source = "address", defaultValue = "广州市区")
    StudentVO student2StudentVO(Student student);

    /**
     * 枚举类型
     *
     * @param gender
     * @return
     */
    default String getGenderName(GenderEnum gender) {
        return gender.getName();
    }
}