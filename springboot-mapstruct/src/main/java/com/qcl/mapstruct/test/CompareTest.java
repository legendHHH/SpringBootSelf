package com.qcl.mapstruct.test;

import com.qcl.mapstruct.converter.StudentConverter;
import com.qcl.mapstruct.entity.Student;
import com.qcl.mapstruct.enums.GenderEnum;
import com.qcl.mapstruct.vo.StudentVO;

import java.util.Date;


/**
 * 比较测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/11/25
 */
public class CompareTest {

    public static void main(String[] args) {
        Student student = new Student();
        student.setName("小明");
        student.setAge(6);
        student.setGender(GenderEnum.Male);
        student.setHeight(121.1);
        student.setBirthday(new Date());
        System.out.println(student);
        //这行代码便是实际要用的代码
        StudentVO studentVO = StudentConverter.INSTANCE.student2StudentVO(student);
        System.out.println(studentVO);
    }

}
