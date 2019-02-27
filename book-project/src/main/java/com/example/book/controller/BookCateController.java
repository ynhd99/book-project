package com.example.book.controller;

import com.example.book.common.advice.validatorGroup.Add;
import com.example.book.common.advice.validatorGroup.Update;
import com.example.book.common.exception.SaleBusinessException;
import com.example.book.entity.BookCategory;
import com.example.book.entity.dto.MessageBody;
import com.example.book.service.BookCateService;
import com.example.book.utils.common.AirUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangna
 * @date 2019/2/25
 */
@RestController
@RequestMapping("/bookCate")
public class BookCateController {
    @Autowired
    private BookCateService bookCateService;

    /**
     * 新增分类信息
     *
     * @param bookCategory
     * @return
     */
    @PostMapping("addBookCate")
    public MessageBody addBookCate(@Validated({Add.class}) @RequestBody BookCategory bookCategory) {
        if (bookCateService.addBookCate(bookCategory) < 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 获取到最大的编码
     *
     * @param bookCategory
     * @return
     */
    @PostMapping("getMaxCode")
    public MessageBody getMaxCode(@RequestBody BookCategory bookCategory) {
        return MessageBody.getMessageBody(true, bookCateService.getMaxCode(bookCategory));
    }

    /**
     * 分页查询分类信息
     *
     * @param bookCategory
     * @return
     */
    @PostMapping("findDataForPage")
    public MessageBody findDataForPage(@RequestBody BookCategory bookCategory) {
        if (!AirUtils.hv(bookCategory.getPage()) || !AirUtils.hv(bookCategory.getSize())) {
            bookCategory.setPage(1);
            bookCategory.setSize(10);
        }
        return MessageBody.getMessageBody(true, bookCateService.findDataForPage(bookCategory));
    }

    /**
     * 修改图书分类信息
     *
     * @param bookCategory
     * @return
     */
    @PostMapping("updateBookCate")
    public MessageBody updateBookCate(@RequestBody BookCategory bookCategory) {
        if (bookCateService.updateBookCate(bookCategory) < 0) {
            throw new SaleBusinessException("修改失败");
        }
        return MessageBody.getMessageBody(true, "修改成功");
    }
}
