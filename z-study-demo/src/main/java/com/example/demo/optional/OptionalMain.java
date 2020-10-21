package com.example.demo.optional;

import java.util.Objects;
import java.util.Optional;

/**
 * 测试
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/10/21
 */
public class OptionalMain {

    public static void main(String[] args) throws Exception {
        //空对象
        User user = new User();
        //空值
        User user1 = null;

        School school = new School();

        //user.setGender("woman");
        //user.setSchool(school);

        if (Objects.nonNull(user) && Objects.nonNull(user.getSchool())) {

            School userSc = user.getSchool();

            System.out.println("地址:"+userSc.getAddress());

        }
        System.out.println(Optional.ofNullable(user).orElse(new User("Name of legend")));
        System.out.println(Optional.ofNullable(user1).orElse(new User("Name of legend")));
        System.out.println(Optional.ofNullable(user1).orElseThrow(()->new Exception("Name of legend")));

    }
}
