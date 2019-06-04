package com.example.room.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.RoomCateDao;
import com.example.room.dao.RoomDao;
import com.example.room.entity.RoomCategory;
import com.example.room.service.RoomCateService;
import com.example.room.utils.UUIDUtils;
import com.example.room.utils.common.AirUtils;
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
public class RoomCateServiceImpl implements RoomCateService {
    @Autowired
    private RoomCateDao roomCateDao;
    @Autowired
    private UserController userController;
    @Autowired
    private RoomDao roomDao;
    private static Logger log = LoggerFactory.getLogger(RoomCateServiceImpl.class);
    private static final Pattern PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]+|[a-zA-Z]+|\\d+");
    private static final String PARENTID = "-1";
    private static final String ENDNUMBER = "\\d+";
    private static final String LZNUMBER = "z";
    private static final String BZNUMBER = "Z";
    private static final String NUMBER = "[a-zA-Z]+";

    /**
     * 新增分类信息
     *
     * @param roomCategory
     * @return
     */
    @Override
    public int addRoomCate(RoomCategory roomCategory) {
        int count = roomCateDao.getBookCateByCode(roomCategory.getCateCode());
        if (count > 0) {
            throw new SaleBusinessException("分类编码已经存在，请重新输入");
        }
        roomCategory.setUpdateTime(new Date());
        roomCategory.setCreateTime(new Date());
        roomCategory.setCreateUser(userController.getUser());
        roomCategory.setUpdateUser(userController.getUser());
        roomCategory.setId(UUIDUtils.getUUID());
        log.info("新增分类信息，请求参数为:{}", JSONObject.toJSONString(roomCategory));
        return roomCateDao.addRoomCate(roomCategory);
    }

    /**
     * 获取到最大编码
     *
     * @param roomCategory
     * @return
     */
    @Override
    public Map<String, String> getMaxCode(RoomCategory roomCategory) {
        //得到最大的编码值
        String maxCode = roomCateDao.getMaxCode(roomCategory);
        Map<String, String> map = new HashMap<>(16);
        if (!AirUtils.hv(maxCode) && PARENTID.equals(roomCategory.getParentId())) {
            map.put("cateCode", "0001");
        }
        if (!AirUtils.hv(maxCode) && !PARENTID.equals(roomCategory.getParentId())) {
            String code = roomCateDao.getCodeById(roomCategory.getParentId());
            map.put("cateCode", code + "0001");
        }
        if (AirUtils.hv(maxCode)) {
            String mCode = getNextCode(maxCode);
            while (roomCateDao.getBookCateByCode(mCode) > 0) {
                mCode = getNextCode(mCode);
            }
            map.put("cateCode", mCode);
        }
        return map;
    }

    /**
     * 分页查询分类信息
     *
     * @param roomCategory
     * @return
     */
    @Override
    public List<RoomCategory> findDataForPage(RoomCategory roomCategory) {
        List<RoomCategory> bookCateServiceList = roomCateDao.getCategoryByQuery(roomCategory);
        //封装父子树
        List<RoomCategory> bookCategories = getParentChildrenTree(bookCateServiceList);
        return bookCategories;
    }

    /**
     * 修改宿舍分类信息
     * @param roomCategory
     * @return
     */
    @Override
    public int updateRoomCate(RoomCategory roomCategory) {
        if(roomDao.findRoomByCate(roomCategory.getId())>0 && roomCategory.getStatus() == 1){
            throw new SaleBusinessException("该分类已经被引用，无法进行停用");
        }
        roomCategory.setUpdateTime(new Date());
        roomCategory.setUpdateUser(userController.getUser());
        return roomCateDao.updateRoomCate(roomCategory);
    }
    /**
     * 删除宿舍分类
     * @param roomCategory
     * @return
     */
    @Override
    public int deleteRoomCate(RoomCategory roomCategory){
        if(roomCategory.getParentId().equals("-1")){
           if(roomCateDao.findRoomByParent(roomCategory.getId())>0){
               throw new SaleBusinessException("该分类下的子类有启用状态的，不可删除");
           }
        }
        return roomCateDao.deleteRoomCate(roomCategory.getId());
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
    private List<RoomCategory> getParentChildrenTree(List<RoomCategory> bookCategories) {
        //筛选出父种类放到ovList列表中
        List<RoomCategory> voList = new ArrayList<>();
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
                List<RoomCategory> childrenList = new ArrayList<>();
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
