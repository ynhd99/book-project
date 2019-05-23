package com.example.room.controller;

import com.example.room.common.exception.SaleBusinessException;
import com.example.room.entity.BuildingInfo;
import com.example.room.entity.dto.MessageBody;
import com.example.room.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yangna
 * @date 2019/3/18
 */
@RestController
@RequestMapping("/building")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    /**
     * 分页查询宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    @PostMapping("findBuildingForPage")
    public MessageBody findBuildingForPage(@RequestBody BuildingInfo buildingInfo) {
        return MessageBody.getMessageBody(true, buildingService.getBuildingForPage(buildingInfo));
    }

    /**
     * 新增宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    @PostMapping("addBuilding")
    public MessageBody addBuilding(@RequestBody BuildingInfo buildingInfo) {
        if (buildingService.addBuilding(buildingInfo) <= 0) {
            throw new SaleBusinessException("新增失败");
        }
        return MessageBody.getMessageBody(true, "新增成功");
    }

    /**
     * 更新宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    @PostMapping("updateBuilding")
    public MessageBody updateBuilding(@RequestBody BuildingInfo buildingInfo) {
        if (buildingService.updateBuilding(buildingInfo) <= 0) {
            throw new SaleBusinessException("更新失败");
        }
        return MessageBody.getMessageBody(true, "更新成功");
    }

    /**
     * 删除宿舍楼信息
     *
     * @param buildingInfo
     * @return
     */
    @PostMapping("deleteBuilding")
    public MessageBody deleteBuilding(@RequestBody BuildingInfo buildingInfo) {
        if (buildingService.deleteBuilding(buildingInfo) <= 0) {
            throw new SaleBusinessException("删除失败");
        }
        return MessageBody.getMessageBody(true, "删除成功");
    }
    /**
     * 导出宿舍楼信息
     */
    @RequestMapping(value = "/exportBuilding", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response){
        buildingService.exportBuilding( response);
    }
}
