package com.qcl.shardingjdbc.controller;

import com.qcl.shardingjdbc.entity.Book;
import com.qcl.shardingjdbc.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public List<Book> getItems(Book book){
        return bookService.getBookList();
    }

    @RequestMapping(value = "/book",method = RequestMethod.POST)
    public Boolean saveItem(Book book){
        return bookService.save(book);
    }
}