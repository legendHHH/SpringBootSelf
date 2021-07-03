package com.legend.spring.controller;

import com.legend.spring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author legend
 * @version 1.0
 * @description
 * @date 2021/7/2
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;
}
