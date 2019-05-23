package com.example.room.service.impl;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.GoodsDao;
import com.example.room.entity.GoodsInfo;
import com.example.room.entity.StudentInfo;
import com.example.room.service.GoodsService;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.ExcelUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author yangna
 * @date 2019/3/20
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private UserController userController;

    /**
     * 新增物品信息
     *
     * @param goodsInfo
     * @return
     */
    @Override
    public int addGoods(GoodsInfo goodsInfo) {
        //判断编码是否已经存在
        if (AirUtils.hv(goodsDao.getGoodsByCode(goodsInfo.getGoodsCode()))) {
            throw new SaleBusinessException("编码已经存在，请重新输入");
        }
        //封装参数
        goodsInfo.setId(UUIDGenerator.getUUID());
        goodsInfo.setCreateTime(new Date());
        goodsInfo.setCreateUser(userController.getUser());
        goodsInfo.setUpdateTime(new Date());
        goodsInfo.setUpdateUser(userController.getUser());
        return goodsDao.addGoods(goodsInfo);
    }

    /**
     * 分页查询物品信息
     *
     * @param goodsInfo
     * @return
     */
    @Override
    public PageInfo<GoodsInfo> getGoodsForPage(GoodsInfo goodsInfo) {
        PageHelper.startPage(goodsInfo.getPage(), goodsInfo.getSize());
        PageInfo pageInfo = new PageInfo(goodsDao.getGoodsForPage(goodsInfo));
        return pageInfo;
    }

    /**
     * 更新物品信息
     *
     * @param goodsInfo
     * @return
     */
    @Override
    public int updateGoods(GoodsInfo goodsInfo) {
        goodsInfo.setUpdateTime(new Date());
        goodsInfo.setUpdateUser(userController.getUser());
        return goodsDao.updateGoods(goodsInfo);
    }

    /**
     * 删除物品信息
     *
     * @param goodsInfo
     * @return
     */
    @Override
    public int deleteGoods(GoodsInfo goodsInfo) {
        goodsInfo.setUpdateTime(new Date());
        goodsInfo.setUpdateUser(userController.getUser());
        return goodsDao.deleteGoods(goodsInfo);
    }

    /**
     * 导出公共财信息
     * @param response
     */
    @Override
    public void exportGoods(HttpServletResponse response) {
        GoodsInfo goodsInfo = new GoodsInfo();
        //获取到学生列表
        List<GoodsInfo> goodsInfos = goodsDao.getGoodsForPage(goodsInfo);
        String header[] = {"物品编码","物品名称","状态"};
        String title = "物品信息表";
        String fileName = "物品信息表";
        int rowNum = 1;
        HSSFWorkbook workbook = ExcelUtils.exportExcel(title,header);
        HSSFSheet sheet = workbook.getSheet(title);
        for (GoodsInfo e : goodsInfos) {
            HSSFRow rows = sheet.createRow(rowNum);
            rows.createCell(0).setCellValue(e.getGoodsCode());
            rows.createCell(1).setCellValue(e.getGoodsName());
            rows.createCell(2).setCellValue(e.getStatus() == 0?"启用":"停用");
            rowNum++;
        };
        ExcelUtils.returnExport(workbook,response,fileName);
    }

}
