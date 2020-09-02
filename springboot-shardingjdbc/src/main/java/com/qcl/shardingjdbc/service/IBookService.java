package com.qcl.shardingjdbc.service;


import com.qcl.shardingjdbc.entity.Book;

import java.util.List;

/**
 * @author chunlin.qi@hand-china.com
 * @version 1.0
 * @description
 * @date 2020/9/2
 */
public interface IBookService {

    /**
     * 获取数据
     *
     * @return
     */
    public List<Book> getBookList();

    /**
     * 保存数据
     *
     * @param book
     * @return
     */
    public boolean save(Book book);
}