package com.example.book.service;


import com.example.book.entity.BookCategory;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author yangna
 * @date 2019/2/25
 */
public interface BookCateService {
    /**
     * 添加图书种类
     *
     * @param bookCategory
     * @return
     */
    public int addBookCate(BookCategory bookCategory);
    /**
     * 获取最大编码
     *
     * @param bookCategory
     * @return
     */
    Map<String,String> getMaxCode(BookCategory bookCategory);
    /**
     * 获取分类列表
     * @param bookCategory
     * @return
     */
    List<BookCategory> findDataForPage(BookCategory bookCategory);

    /**
     * 修改图书分类
     * @param bookCategory
     * @return
     */
    int updateBookCate(BookCategory bookCategory);
}
