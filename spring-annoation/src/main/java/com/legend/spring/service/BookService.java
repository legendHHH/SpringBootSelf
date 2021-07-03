package com.legend.spring.service;

import com.legend.spring.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/2
 */
@Service
public class BookService {

    /**
     * 指定装配bookDao对象
     */
    @Qualifier("bookDao")
    //@Resource(name = bookDao)
    //@Autowired(required = false)
    @Autowired
    private BookDao bookDao2;

    public void method(){
        System.out.println("bookdao：" + bookDao2);
    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookDao=" + bookDao2 +
                '}';
    }
}
