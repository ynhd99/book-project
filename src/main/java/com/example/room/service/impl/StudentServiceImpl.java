package com.example.room.service.impl;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.controller.UserController;
import com.example.room.dao.StudentDao;
import com.example.room.dao.UserDao;
import com.example.room.entity.StudentInfo;
import com.example.room.entity.UserInfo;
import com.example.room.service.StudentService;
import com.example.room.service.UserService;
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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangna
 * @date 2019/3/15
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private UserController userController;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    /**
     * 分页查询学生信息
     *
     * @param studentInfo
     * @return
     */
    @Override
    public PageInfo<StudentInfo> getStudentForPage(StudentInfo studentInfo) {
        PageHelper.startPage(studentInfo.getPage(), studentInfo.getSize());
        List<StudentInfo> studentInfos = studentDao.getStudentForPage(studentInfo);
        //获取学生的床位信息
        List<String> ids = studentInfos.stream().map(e -> e.getId()).collect(Collectors.toList());
        if (AirUtils.hv(ids)) {
            List<StudentInfo> studentInfoList = studentDao.getBedCountById(ids);
            if (AirUtils.hv(studentInfoList)) {
                studentInfos.forEach(index -> {
                    studentInfoList.forEach(item -> {
                        if (item.getId().equals(index.getId())) {
                            index.setBedCount(item.getBedCount());
                        }
                    });
                });
            }
        }
        PageInfo<StudentInfo> studentInfoPageInfo = new PageInfo<StudentInfo>(studentInfos);
        return studentInfoPageInfo;
    }

    /**
     * 编辑学生信息
     *
     * @param studentInfo
     * @return
     */
    @Transactional
    @Override
    public int updateStudent(StudentInfo studentInfo) {
        studentInfo.setUpdateTime(new Date());
        studentInfo.setUpdateUser(userController.getUser());
        String userName = studentDao.getDataById(studentInfo.getId());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setFullName(studentInfo.getStudentName());
        userInfo.setRoleId(studentInfo.getRoleId());
        userInfo.setUpdateUser(userController.getUser());
        userService.updateUser(userInfo);
        return studentDao.updateStudent(studentInfo);
    }

    /**
     * 删除学生信息
     *
     * @param studentInfo
     * @return
     */
    @Transactional
    @Override
    public int deleteStudent(StudentInfo studentInfo) {
        studentInfo.setUpdateTime(new Date());
        studentInfo.setUpdateUser(userController.getUser());
        //批量删除操作
        List<String> list = studentInfo.getDeleteStudentList();
        if (AirUtils.hv(list)) {
            //批量删除用户表
            studentDao.batchDeleteUser(list);
            //批量删除学生表
            return studentDao.batchDelete(list);
        }
        //删除用户表
        String userName = studentDao.getDataById(studentInfo.getId());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setUpdateUser(userController.getUser());
        userService.deleteUser(userInfo);
        return studentDao.deleteStudent(studentInfo);
    }

    /**
     * 新增学生信息
     *
     * @param studentInfo
     * @return
     */
    @Transactional
    @Override
    public int addStudent(StudentInfo studentInfo) {
        if (AirUtils.hv(userDao.getDataByCode(studentInfo.getStudentCode()))) {
            throw new SaleBusinessException("编码已经存在");
        }
        studentInfo.setId(UUIDGenerator.getUUID());
        studentInfo.setCreateTime(new Date());
        studentInfo.setCreateUser(userController.getUser());
        studentInfo.setUpdateTime(new Date());
        studentInfo.setUpdateUser(userController.getUser());
        //同时将用户名，账号，角色写入user库
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(studentInfo.getStudentName());
        userInfo.setUserName(studentInfo.getStudentCode());
        userInfo.setUserPass("SJ123456");
        userInfo.setRoleId(studentInfo.getRoleId());
        userInfo.setCreateUser(userController.getUser());
        userInfo.setUpdateUser(userController.getUser());
        userService.userRegister(userInfo);
        return studentDao.addStudent(studentInfo);
    }

    /**
     * 导出学生信息
     */
    @Override
    public void exportStudent(HttpServletResponse response){
        StudentInfo studentInfo = new StudentInfo();
        //获取到学生列表
        List<StudentInfo> studentInfos = studentDao.getStudentForPage(studentInfo);
        String header[] = {"学号","姓名","学院","班级","性别","手机号"};
        String title = "学生信息表";
        String fileName = "学生信息表";
        int rowNum = 1;
        HSSFWorkbook workbook = ExcelUtils.exportExcel(title,header);
        HSSFSheet sheet = workbook.getSheet(title);
        for (StudentInfo e : studentInfos) {
            HSSFRow rows = sheet.createRow(rowNum);
            rows.createCell(0).setCellValue(e.getStudentCode());
            rows.createCell(1).setCellValue(e.getStudentName());
            rows.createCell(2).setCellValue(e.getCollegeName());
            rows.createCell(3).setCellValue(e.getClassName());
            rows.createCell(4).setCellValue(e.getStudentSex());
            rows.createCell(5).setCellValue(e.getStudentPhone());
            rowNum++;
        };
        ExcelUtils.returnExport(workbook,response,fileName);
    }
}
