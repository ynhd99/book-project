package com.example.room.controller;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.StudentInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangna
 * @date 2019/3/15
 */
@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 分页查询宿管员信息
     *
     * @param studentInfo
     * @return
     */
    @PostMapping("findStudentForPage")
    public MessageBody findstudentForPage(@RequestBody StudentInfo studentInfo) {
        return MessageBody.getMessageBody(true, studentService.getStudentForPage(studentInfo));
    }

    /**
     * 新增宿管员信息
     *
     * @param studentInfo
     * @return
     */
    @PostMapping("addStudent")
    public MessageBody addStudent(@RequestBody StudentInfo studentInfo) {
        if (studentService.addStudent(studentInfo) <= 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 更新宿管员信息
     *
     * @param studentInfo
     * @return
     */
    @PostMapping("updateStudent")
    public MessageBody updatestudent(@RequestBody StudentInfo studentInfo) {
        if (studentService.updateStudent(studentInfo) <= 0) {
            throw new SaleBusinessException("更新失败");
        }
        return MessageBody.getMessageBody(true, "更新成功");
    }

    /**
     * 删除宿管员信息
     *
     * @param studentInfo
     * @return
     */
    @PostMapping("deleteStudent")
    public MessageBody deleteStudent(@RequestBody StudentInfo studentInfo) {
        if (studentService.deleteStudent(studentInfo) <= 0) {
            throw new SaleBusinessException("删除失败");
        }
        return MessageBody.getMessageBody(true, "删除成功");
    }
}
