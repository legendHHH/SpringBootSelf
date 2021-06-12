package com.example.demo.transfer;

/**
 * 测试传值
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/12
 */
public class ChangeTest {

    public static void main(String[] args) {
        ChangeTest changeTest = new ChangeTest();

        //变量作用域
        int age = 99;
        changeTest.changeValue1(age);
        //99
        System.out.println("=========:" + age);

        Person person = new Person("abc");
        changeTest.changeValue2(person);
        //legendXXXXXX
        System.out.println("Person.name=========:" + person.getName());

        String str = "cvcvcvxvcx";
        changeTest.changeValue3(str);
        //cvcvcvxvcx
        System.out.println("String=========:" + str);
    }


    public void changeValue1(int age) {
        age = 30;
    }

    public void changeValue2(Person person) {
        person.setName("legendXXXXXX");
    }

    public void changeValue3(String str) {
        str = "XXXXXX";
    }
}


class Person {
    private int age;
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
