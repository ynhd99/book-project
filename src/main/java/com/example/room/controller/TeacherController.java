package com.example.room.controller;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.TeacherInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangna
 * @date 2019/3/13
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * 分页查询宿管员信息
     *
     * @param teacherInfo
     * @return
     */
    @PostMapping("findTeacherForPage")
    public MessageBody findTeacherForPage(@RequestBody TeacherInfo teacherInfo) {
        return MessageBody.getMessageBody(true, teacherService.getTeacherForPage(teacherInfo));
    }

    /**
     * 新增宿管员信息
     *
     * @param teacherInfo
     * @return
     */
    @PostMapping("addTeacher")
    public MessageBody addTeacher(@RequestBody TeacherInfo teacherInfo) {
        if (teacherService.addTeacher(teacherInfo) <= 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 更新宿管员信息
     *
     * @param teacherInfo
     * @return
     */
    @PostMapping("updateTeacher")
    public MessageBody updateTeacher(@RequestBody TeacherInfo teacherInfo) {
        if (teacherService.updateTeacher(teacherInfo) <= 0) {
            throw new SaleBusinessException("更新失败");
        }
        return MessageBody.getMessageBody(true, "更新成功");
    }

    /**
     * 删除宿管员信息
     *
     * @param teacherInfo
     * @return
     */
    @PostMapping("deleteTeacher")
    public MessageBody deleteTeacher(@RequestBody TeacherInfo teacherInfo) {
        if (teacherService.deleteTeacher(teacherInfo) <= 0) {
            throw new SaleBusinessException("删除失败");
        }
        return MessageBody.getMessageBody(true, "删除成功");
    }
    /**
     * 导出辅导员信息
     */
    @RequestMapping(value = "/exportTeacher", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){
        teacherService.exportTeacher( response);
    }
}

