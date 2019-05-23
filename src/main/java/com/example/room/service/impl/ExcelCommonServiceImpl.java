package com.example.room.service.impl;

import com.example.room.common.excel.data.ExcelBaseData;
import com.example.room.dao.ClassDao;
import com.example.room.dao.CollegeDao;
import com.example.room.entity.ClassInfo;
import com.example.room.entity.CollegeInfo;
import com.example.room.service.ExcelCommonService;
import com.example.room.utils.common.AirUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelCommonServiceImpl implements ExcelCommonService {
    @Autowired
    private CollegeDao collegeDao;
    @Autowired
    private ClassDao classDao;
    /**
     * 校验学院名称和编码是否一致
     * @param baseDataList
     * @param errorList
     */
    @Override
    public Map<String,CollegeInfo> checkCellege(List<ExcelBaseData> baseDataList, List<String> errorList) {
        Map<String,CollegeInfo> map = new HashMap<>();
        List<String> nameList =  baseDataList.stream().map(e->e.getCollegeName()).distinct().collect(Collectors.toList());
        List<CollegeInfo> collegeInfos = collegeDao.getCollegeList(nameList);
        if(AirUtils.hv(collegeInfos)){
            collegeInfos.forEach(e->{
                baseDataList.forEach(item->{
                    if(e.getCollegeName().equals(item.getCollegeName()) && e.getStatus() == 0){
                        map.put(e.getCollegeName(),e);
                    }
                    else if(e.getCollegeName().equals(item.getCollegeName()) && e.getStatus() == 1){
                        errorList.add("第"+item.getRow()+"行学院，已经停用");
                    }else{
                        errorList.add("第"+item.getRow()+"行学院在系统中不存在");
                    }
                });
            });
        }else{
            errorList.add("该表中学院在系统中全不存在");
        }
        return map;
    }
    /**
     * 校验班级名称是否和编辑是否一致
     * @param baseDataList
     * @param errorList
     */
    @Override
    public Map<String, ClassInfo> checkClass(List<ExcelBaseData> baseDataList, List<String> errorList) {
        Map<String,ClassInfo> map = new HashMap<>();
        List<String> nameList = baseDataList.stream().map(e->e.getClassName()).distinct().collect(Collectors.toList());
        List<ClassInfo> classInfos = classDao.getClassByName(nameList);
        if(AirUtils.hv(classInfos)){
            classInfos.forEach(e->{
                baseDataList.forEach(item->{
                    if(e.getClassName().equals(item.getClassName()) && e.getStatus() == 0){
                        map.put(e.getCollegeName(),e);
                    }
                    else if(e.getClassName().equals(item.getClassName()) && e.getStatus() == 1){
                        errorList.add("第"+item.getRow()+"行班级，已经停用");
                    }else{
                        errorList.add("第"+item.getRow()+"行班级在系统中不存在");
                    }
                });
            });
        }else{
            errorList.add("该表中班级在系统中全不存在");
        }
     return map;
    }
}
