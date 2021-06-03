package com.example.demo.debug;

/**
 * 实现类
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/6/3
 */
public class ServiceImpl implements IService {

    @Override
    public String execute() {
        System.out.println("这里是方法断点");
        return null;
    }
}
