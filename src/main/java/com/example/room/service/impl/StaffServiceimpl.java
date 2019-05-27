package com.example.room.service.impl;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.StaffDao;
import com.example.room.dao.UserDao;
import com.example.room.entity.StaffInfo;
import com.example.room.entity.TeacherInfo;
import com.example.room.entity.UserInfo;
import com.example.room.service.StaffService;
import com.example.room.service.UserService;
import com.example.room.utils.common.AirUtils;
import com.example.room.utils.common.ExcelUtils;
import com.example.room.utils.common.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Service
public class StaffServiceimpl implements StaffService {
    @Autowired
    private UserController userController;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    /**
     * 分页查询宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @Override
    public PageInfo<StaffInfo> getStaffForPage(StaffInfo staffInfo) {
        PageHelper.startPage(staffInfo.getPage(), staffInfo.getSize());
        PageInfo<StaffInfo> staffInfoPageInfo = new PageInfo<StaffInfo>(staffDao.getStaffForPage(staffInfo));
        return staffInfoPageInfo;
    }

    /**
     * 编辑宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @Transactional
    @Override
    public int updateStaff(StaffInfo staffInfo) {
        staffInfo.setUpdateTime(new Date());
        staffInfo.setUpdateUser(userController.getUser());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(staffInfo.getStaffName());
        userInfo.setRoleId(staffInfo.getRoleId());
        userInfo.setUpdateUser(userController.getUser());
        userService.updateUser(userInfo);
        return staffDao.updateStaff(staffInfo);
    }

    /**
     * 删除宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @Transactional
    @Override
    public int deleteStaff(StaffInfo staffInfo) {
        staffInfo.setUpdateTime(new Date());
        staffInfo.setUpdateUser(userController.getUser());
        //批量删除宿管云列表
        List<String> list = staffInfo.getDeleteStaffList();
        if(AirUtils.hv(list)){
            staffDao.batchDeleteUser(list);
            return staffDao.batchDelete(list);
        }
        //删除用户表
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(staffInfo.getStaffName());
        userInfo.setUpdateUser(userController.getUser());
        userService.deleteUser(userInfo);
        return staffDao.deleteStaff(staffInfo);
    }

    /**
     * 新增宿管员信息
     *
     * @param staffInfo
     * @return
     */
    @Transactional
    @Override
    public int addStaff(StaffInfo staffInfo) {
        if (AirUtils.hv(userDao.getDataByCode(staffInfo.getStaffCode()))) {
            throw new SaleBusinessException("编码已经存在");
        }
        staffInfo.setId(UUIDGenerator.getUUID());
        staffInfo.setCreateTime(new Date());
        staffInfo.setCreateUser(userController.getUser());
        staffInfo.setUpdateTime(new Date());
        staffInfo.setUpdateUser(userController.getUser());
        //同时将用户名，账号，角色写入user库
        UserInfo userInfo = new UserInfo();
        userInfo.setId(UUIDGenerator.getUUID());
        userInfo.setFullName(staffInfo.getStaffName());
        userInfo.setUserName(staffInfo.getStaffCode());
        userInfo.setUserPass("SJ123456");
        userInfo.setRoleId(staffInfo.getRoleId());
        userInfo.setCreateUser(userController.getUser());
        userInfo.setUpdateUser(userController.getUser());
        userService.userRegister(userInfo);
        return staffDao.addStaff(staffInfo);
    }

    /**
     * 导出宿管员信息
     * @param response
     */
    @Override
    public void exportStaff(HttpServletResponse response) {
        StaffInfo staffInfo = new StaffInfo();
        //获取到宿管员列表
        List<StaffInfo> staffInfos= staffDao.getStaffForPage(staffInfo);
        String header[] = {"教职工号","姓名","性别","手机号"};
        String title = "宿管员信息表";
        String fileName = "宿管员信息表";
        int rowNum = 1;
        HSSFWorkbook workbook = ExcelUtils.exportExcel(title,header);
        CellStyle cellStyle =ExcelUtils.getCellStyle(workbook);
        HSSFSheet sheet = workbook.getSheet(title);
        for (StaffInfo e : staffInfos) {
            HSSFRow rows = sheet.createRow(rowNum);
            ExcelUtils.addCell(rows, 0,e.getStaffCode(), cellStyle,sheet);
            ExcelUtils.addCell(rows, 1,e.getStaffName(), cellStyle,sheet);
            ExcelUtils.addCell(rows, 2,e.getStaffSex(), cellStyle,sheet);
            ExcelUtils.addCell(rows, 3,e.getStaffPhone(), cellStyle,sheet);
            rowNum++;
        };
        ExcelUtils.returnExport(workbook,response,fileName);
    }
}
