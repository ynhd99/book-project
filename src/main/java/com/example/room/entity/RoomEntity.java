package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author yangna
 * @date 2019/2/19
 */
@Data
public class RoomEntity extends Base {
    /**
     * 宿舍号
     */
    @NotBlank(message = "宿舍号不能为空")
    private String roomCode;
    /**
     * 类别id
     */
    private String cateId;
    /**
     * 类别名称
     */
    private String cateName;
    /**
     * 父类名称
     */
    private String cateParentName;
    /**
     * 模糊查询字段
     */
    private String queryString;
    /**
     * 楼号id
     */
    private String buildingId;
    /**
     * 楼号
     */
    private String buildingName;
    /**
     * 宿舍人数
     */
    private Integer roomCount;
    /**
     * 宿舍当前人数
     */
    private Integer currentCount;
    /**
     * 容纳学生详情
     */
    private List<RoomDetailInfo> roomDetailInfoList;

}
