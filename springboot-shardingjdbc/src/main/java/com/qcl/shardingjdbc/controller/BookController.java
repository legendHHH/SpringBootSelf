package com.qcl.shardingjdbc.controller;

import com.qcl.shardingjdbc.entity.Book;
import com.qcl.shardingjdbc.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/2
 */
@RestController
public class BookController {

    @Autowired
    private IBookService bookService;

    /**
     * http://localhost:9999/book
     *
     * @param book
     * @return
     */
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public List<Book> getItems(Book book){
        return bookService.getBookList();
    }

    /**
     * http://localhost:9999/book?id=9&name=java编程思想&count=8
     *
     * @param book
     * @return
     */
    @RequestMapping(value = "/book",method = RequestMethod.POST)
    public Boolean saveItem(Book book){
        return bookService.save(book);
    }


    /**
     * 后台手动创建集合批量保存测试
     *
     * http://localhost:9999/book-create
     *
     * @return
     */
    @RequestMapping(value = "/book-create", method = RequestMethod.GET)
    public Boolean createItemsAuto(){
        List<Book> bookList = new ArrayList<>(50);
        for (int i = 0; i < 30; i++) {
            Book book = new Book();
            Random random = new Random();
            book.setId(random.nextInt(100000));
            book.setCount(random.nextInt(100000));
            book.setName(UUID.randomUUID().toString());

            bookList.add(book);
        }
        return bookService.saveBatch(bookList, 30);
    }

}