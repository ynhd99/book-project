package com.example.room.controller;

import java.util.List;

import com.example.room.common.response.MessageBody;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.room.entity.ClassInfo;
import com.example.room.service.ClassService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 班级管理控制层
 *
 * @author yangna
 * @Date 2019-03-12 03:20:47
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    /**
     * 通过ID查询单条班级管理数据
     *
     * @param classInfo 查询实体对象
     * @return
     */
    @PostMapping("findClassById")
    public MessageBody getClass(@RequestBody ClassInfo classInfo) {
        ClassInfo result = classService.findClassById( classInfo.getId(),"");
        return MessageBody.getMessageBody(true, result);
    }

    /**
     * 根据条件查询班级管理数据（不分页）
     *
     * @param classInfo 查询实体对象
     * @return
     */
    @PostMapping("findAllClass")
    public MessageBody findAllClass(@RequestBody ClassInfo classInfo) {
        List<ClassInfo> result = classService.findAllClass(classInfo);
        return MessageBody.getMessageBody(true, result);
    }

    /**
     * 根据条件查询班级管理数据（含有分页）
     *
     * @param classInfo 查询实体对象
     * @return 分页对象
     */
    @PostMapping("findClassForPage")
    public MessageBody getClasss(@RequestBody ClassInfo classInfo) {
        PageInfo<ClassInfo> result = classService.findClassForPage(classInfo);
        return MessageBody.getMessageBody(true, result);
    }

    /**
     * 编辑班级管理数据
     *
     * @param classInfo 班级管理实体对象
     * @return
     */
    @PostMapping("updateClass")
    public MessageBody updateClass(@RequestBody ClassInfo classInfo) {
        if (!classService.updateClass( classInfo)){
            return MessageBody.getErrorMessageBody("操作失败");
        }
        return MessageBody.getInfoMessageBody(true, "操作成功");
    }

    /**
     * 新增班级管理数据
     *
     * @param classInfo 班级管理实体对象
     * @return
     */
    @PostMapping("addClass")
    public MessageBody addClass(@RequestBody ClassInfo classInfo) {
        if (!classService.addClass(classInfo)){
            return MessageBody.getErrorMessageBody("操作失败");
        }
        return MessageBody.getInfoMessageBody(true, "操作成功");
    }

    /**
     * 删除班级管理数据
     *
     * @param classInfo 班级管理实体对象
     * @return 是否成功
     */
    @PostMapping("deleteClass")
    public MessageBody deleteClassById(@RequestBody ClassInfo classInfo) {
        if (!classService.deleteClassById(classInfo)) {
            return MessageBody.getErrorMessageBody("操作失败");
        }
        return MessageBody.getInfoMessageBody(true, "操作成功");
    }
    /**
     * 导出班级信息
     */
    @RequestMapping(value = "/exportClass", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){
        classService.exportClass( response);
    }
}
