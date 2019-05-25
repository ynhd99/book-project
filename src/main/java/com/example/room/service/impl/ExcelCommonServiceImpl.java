package com.example.room.service.impl;

import com.example.room.common.excel.data.ExcelBaseData;
import com.example.room.dao.*;
import com.example.room.entity.*;
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
    @Autowired
    private AuthorityDao authorityDao;
    @Autowired
    private RoomCateDao roomCateDao;
    @Autowired
    private BuildingDao buildingDao;
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
                map.put(e.getCollegeName(),e);
            });
            baseDataList.forEach(item->{
                if(!AirUtils.hv(map.get(item.getCollegeName()))){
                    errorList.add("第"+item.getRow()+"行学院在系统中不存在");
                }else if(map.get(item.getCollegeName()).getStatus() == 1){
                    errorList.add("第"+item.getRow()+"行学院，已经停用");
                }
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
                map.put(e.getClassName(),e);
            });
                baseDataList.forEach(item->{
                    if(!AirUtils.hv(map.get(item.getClassName()))){
                        errorList.add("第"+item.getRow()+"行班级在系统中不存在");
                    }else if(map.get(item.getClassName()).getStatus() == 1){
                        errorList.add("第"+item.getRow()+"行班级，已经停用");
                    }
                });
        }else{
            errorList.add("该表中班级在系统中全不存在");
        }
     return map;
    }

    /**
     * 校验角色是否存在
     * @param baseDataList
     * @param errorList
     * @return
     */
    @Override
    public Map<String, RoleInfo> checkRole(List<ExcelBaseData> baseDataList, List<String> errorList) {
        Map<String,RoleInfo> map = new HashMap<>();
        List<String> nameList = baseDataList.stream().map(e->e.getRole()).distinct().collect(Collectors.toList());
        List<RoleInfo> roleInfos = authorityDao.getRoleByName(nameList);
        if(AirUtils.hv(roleInfos)){
            roleInfos.forEach(e->{
                map.put(e.getRoleName(),e);
            });
            baseDataList.forEach(item->{
                if(!AirUtils.hv(map.get(item.getRole()))){
                    errorList.add("第"+item.getRow()+"行角色在系统中不存在");
                }else if(map.get(item.getRole()).getStatus() == 1){
                    errorList.add("第"+item.getRow()+"行角色，已经停用");
                }
            });
        }else{
            errorList.add("该表中角色在系统中全不存在");
        }
        return map;
    }

    /**
     * 校验宿舍类别是否存在
     * @param baseDataList
     * @param errorList
     * @return
     */
    @Override
    public Map<String, RoomCategory> checkRoomCate(List<ExcelBaseData> baseDataList, List<String> errorList) {
        Map<String,RoomCategory> map = new HashMap<>();
        List<String> nameList = baseDataList.stream().map(e->e.getCateName()).distinct().collect(Collectors.toList());
        List<RoomCategory> roleInfos = roomCateDao.getCateByName(nameList);
        if(AirUtils.hv(roleInfos)){
            roleInfos.forEach(e->{
                map.put(e.getCateName(),e);
            });
            baseDataList.forEach(item->{
                if(!AirUtils.hv(map.get(item.getCateName()))){
                    errorList.add("第"+item.getRow()+"行类别在系统中不存在");
                }else if(map.get(item.getCateName()).getStatus() == 1){
                    errorList.add("第"+item.getRow()+"行类别，已经停用");
                }
            });
        }else{
            errorList.add("该表中类别在系统中全不存在");
        }
        return map;
    }

    /**
     * 校验宿舍楼是否存在
     * @param baseDataList
     * @param errorList
     * @return
     */
    @Override
    public Map<String, BuildingInfo> checkBuilding(List<ExcelBaseData> baseDataList, List<String> errorList) {

        Map<String,BuildingInfo> map = new HashMap<>();
        List<String> nameList = baseDataList.stream().map(e->e.getBuilidngName()).distinct().collect(Collectors.toList());
        List<BuildingInfo> roleInfos = buildingDao.getBuildingByName(nameList);
        if(AirUtils.hv(roleInfos)){
            roleInfos.forEach(e->{
                map.put(e.getBuildingName(),e);
            });
            baseDataList.forEach(item->{
                if(!AirUtils.hv(map.get(item.getBuilidngName()))){
                    errorList.add("第"+item.getRow()+"行楼号在系统中不存在");
                }else if(map.get(item.getBuilidngName()).getStatus() == 1){
                    errorList.add("第"+item.getRow()+"行楼号，已经停用");
                }
            });
        }else{
            errorList.add("该表中楼号在系统中全不存在");
        }
        return map;
}}
