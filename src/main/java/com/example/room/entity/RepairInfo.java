package com.example.room.entity;

import com.example.room.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author yangna
 * @date 2019/4/8
 */
@Data
public class RepairInfo extends Base {
    /**
     * 宿舍id
     */
    private String roomId;
    /**
     * 宿舍名称
     */
    private String roomCode;
    /**
     * 物品id
     */
    private String goodsId;
    /**
     * 物品名称
     */
    private String goodsName;
    /**
     * 维修原因
     */
    private String remark;
    /**
     * w维修日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date repairDate;
    /**
     * 模糊搜索字段
     */
    private String queryString;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    /**
     * 申请人
     */
    private String repairPerson;
}
