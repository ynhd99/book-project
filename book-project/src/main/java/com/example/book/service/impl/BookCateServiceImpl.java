package com.example.book.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.example.book.common.exception.SaleBusinessException;
import com.example.book.dao.BookCateDao;
import com.example.book.entity.BookCategory;
import com.example.book.service.BookCateService;
import com.example.book.utils.UUIDUtils;
import com.example.book.utils.common.AirUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangna
 * @date 2019/2/25
 */
@Service
public class BookCateServiceImpl implements BookCateService {
    @Autowired
    private BookCateDao bookCateDao;
    private static Logger log = LoggerFactory.getLogger(BookCateServiceImpl.class);
    private static final Pattern PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]+|[a-zA-Z]+|\\d+");
    private static final String PARENTID = "-1";
    private static final String ENDNUMBER = "\\d+";
    private static final String LZNUMBER = "z";
    private static final String BZNUMBER = "Z";
    private static final String NUMBER = "[a-zA-Z]+";

    /**
     * 新增分类信息
     *
     * @param bookCategory
     * @return
     */
    @Override
    public int addBookCate(BookCategory bookCategory) {
        int count = bookCateDao.getBookCateByCode(bookCategory.getCateCode());
        if (count > 0) {
            throw new SaleBusinessException("分类编码已经存在，请重新输入");
        }
        bookCategory.setUpdateTime(new Date());
        bookCategory.setCreateTime(new Date());
        bookCategory.setId(UUIDUtils.getUUID());
        log.info("新增分类信息，请求参数为:{}", JSONObject.toJSONString(bookCategory));
        return bookCateDao.addBookCate(bookCategory);
    }

    /**
     * 获取到最大编码
     *
     * @param bookCategory
     * @return
     */
    @Override
    public Map<String, String> getMaxCode(BookCategory bookCategory) {
        //得到最大的编码值
        String maxCode = bookCateDao.getMaxCode(bookCategory);
        Map<String, String> map = new HashMap<>(16);
        if (!AirUtils.hv(maxCode) && PARENTID.equals(bookCategory.getParentId())) {
            map.put("cateCode", "0001");
        }
        if (!AirUtils.hv(maxCode) && !PARENTID.equals(bookCategory.getParentId())) {
            String code = bookCateDao.getCodeById(bookCategory.getParentId());
            map.put("cateCode", code + "0001");
        }
        if (AirUtils.hv(maxCode)) {
            String mCode = getNextCode(maxCode);
            while (bookCateDao.getBookCateByCode(mCode) > 0) {
                mCode = getNextCode(mCode);
            }
            map.put("cateCode", mCode);
        }
        return map;
    }

    /**
     * 分页查询分类信息
     *
     * @param bookCategory
     * @return
     */
    @Override
    public List<BookCategory> findDataForPage(BookCategory bookCategory) {
        List<BookCategory> bookCateServiceList = bookCateDao.getCategoryByQuery(bookCategory);
        //封装父子树
        List<BookCategory> bookCategories = getParentChildrenTree(bookCateServiceList);
        return bookCategories;
    }

    /**
     * 修改图书分类信息
     * @param bookCategory
     * @return
     */
    @Override
    public int updateBookCate(BookCategory bookCategory) {
        return bookCateDao.updateBookCate(bookCategory);
    }

    /**
     * 获取下一个编码
     *
     * @param maxCode
     * @return
     */
    private String getNextCode(String maxCode) {
        List<String> list = new ArrayList<>();
        Matcher m = PATTERN.matcher(maxCode);
        while (m.find()) {
            list.add(m.group());
        }
        String end = list.get(list.size() - 1);
        //数字结尾
        if (end.matches(ENDNUMBER)) {
            String e = "";
            e = Objects.toString(Long.parseLong(end) + 1);
            while (e.length() < end.length()) {
                e = "0"+e;
            }
            end = e;
        } else if (end.endsWith(LZNUMBER) || end.endsWith(BZNUMBER)) {
            end = end + "1";
        } else if (end.matches(NUMBER)) {
            char endChar = end.toCharArray()[end.length() - 1];
            end = end.substring(0, end.length() - 1) + (char) (endChar + 1);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() - 1; i++) {
            sb.append(list.get(i));
        }
        sb.append(end);
        return sb.toString();
    }
    /**
     * 方法来封装客户分类父子树
     *
     * @param bookCategories
     * @return
     */
    private List<BookCategory> getParentChildrenTree(List<BookCategory> bookCategories) {
        //筛选出父种类放到ovList列表中
        List<BookCategory> voList = new ArrayList<>();
        if (AirUtils.hv(bookCategories)) {
            bookCategories.forEach(entity -> {
                if (PARENTID.equals(entity.getParentId())) {
                    voList.add(entity);
                }
            });
        }
        //筛选出父类下面的子类列表
        if (AirUtils.hv(voList)) {
            voList.forEach(entity -> {
                List<BookCategory> childrenList = new ArrayList<>();
                bookCategories.forEach(scc -> {
                    if (scc.getParentId().equals(entity.getId())) {
                        childrenList.add(scc);
                    }
                });
                entity.setChildren(childrenList);
            });
        }
        return voList;
    }
}
