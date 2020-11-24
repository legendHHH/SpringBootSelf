package com.qcl.shardingjdbc.service;


import com.qcl.shardingjdbc.entity.Book;

import java.util.List;

/**
 * @author legend
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

    /**
     * 批量保存数据
     *
     * @param bookList 数据集合
     * @param batchSize 一次保存多少
     * @return
     */
    boolean saveBatch(List<Book> bookList,int batchSize);
}