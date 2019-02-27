package com.example.book.dao;

import com.example.book.entity.BookCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author yangna
 * @date 2019/2/25
 */
@Mapper
@Repository
public interface BookCateDao {
    /**
     * 添加图书种类
     *
     * @param bookCategory
     * @return
     */
    int addBookCate(BookCategory bookCategory);

    /**
     * 根据编码获取分类信息
     *
     * @param cateCode
     * @return
     */
    int getBookCateByCode(String cateCode);
    /**
     * 获取最大编码
     *
     * @param bookCategory
     * @return
     */
    String getMaxCode(BookCategory bookCategory);

    /**
     * 根据id获取编码
     * @param id
     * @return
     */
    String getCodeById(String id);

    /**
     * 获取分类列表
     * @param bookCategory
     * @return
     */
    List<BookCategory> findDataForPage(BookCategory bookCategory);

    /**
     * 可以筛选客户分类
     * @param bookCategory
     * @return
     */
    List<BookCategory> getCategoryByQuery(BookCategory bookCategory);

    /**
     * 修改图书分类
     * @param bookCategory
     * @return
     */
    int updateBookCate(BookCategory bookCategory);
}
