package com.example.room.entity;

import com.example.room.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author yangna
 * @date 2019/4/10
 */
@Data
public class RoomDetailInfo extends Base {
    private String roomId;
    private String roomCode;
    private String studentId;
    private String studentCode;
    private String buildingId;
    private String buildingCode;
    private Integer bedCount;
    private String collegeName;
    private String collegeId;
    private String className;
    private String classId;
    private String studentName;
    private String studentSex;
    private String studentPhone;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date checkDate;
}
