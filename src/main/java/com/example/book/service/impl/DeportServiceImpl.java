package com.example.book.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.book.common.advice.validatorGroup.UpdateStatus;
import com.example.book.common.exception.SaleBusinessException;
import com.example.book.dao.DeportDao;
import com.example.book.entity.DeportEntity;
import com.example.book.service.DeportService;
import com.example.book.utils.UUIDUtils;
import com.example.book.utils.common.AirUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

/**
 * @author yangna
 * @date 2019/2/21
 */
@Service
public class DeportServiceImpl implements DeportService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private DeportDao deportDao;

    /**
     * 新增仓库信息
     *
     * @param deportEntity
     * @return
     */
    @Override
    public int addDeport(@Validated DeportEntity deportEntity) {
        //判断仓库编码是否已经存在
        DeportEntity deport = deportDao.getDeportByCode(deportEntity.getDeportCode());
        if (AirUtils.hv(deport)) {
            throw new SaleBusinessException("仓库编码已经存在");
        }
        deportEntity.setCreateTime(new Date());
        deportEntity.setUpdateTime(new Date());
        deportEntity.setId(UUIDUtils.getUUID());
        log.info("新增仓库信息，请求参数为:{}", JSONObject.toJSONString(deportEntity));
        return deportDao.addDeport(deportEntity);
    }

    /**
     * 分页查询仓库档案信息
     *
     * @param deportEntity
     * @return
     */
    @Override
    public PageInfo<DeportEntity> findDataForPage(DeportEntity deportEntity) {
        //分页查询
        PageHelper.startPage(deportEntity.getPage(), deportEntity.getSize());
        PageInfo<DeportEntity> pageInfo = new PageInfo<>(deportDao.findDataForPage(deportEntity));
        return pageInfo;
    }

    /**
     * 更新仓库状态
     *
     * @param deportEntity
     * @return
     */
    @Override
    public int updateStatus(@Validated({UpdateStatus.class}) DeportEntity deportEntity) {
        deportEntity.setUpdateTime(new Date());
        return deportDao.updateStatus(deportEntity);
    }

    /**
     * 删除仓库
     *
     * @param deportEntity
     * @return
     */
    @Override
    public int deleteDeport(DeportEntity deportEntity) {
        deportEntity.setUpdateTime(new Date());
        return deportDao.deleteDeport(deportEntity);
    }

    /**
     * 修改仓库信息
     *
     * @param deportEntity
     * @return
     */
    @Override
    public int update(DeportEntity deportEntity) {
        deportEntity.setUpdateTime(new Date());
        return deportDao.update(deportEntity);
    }
}
