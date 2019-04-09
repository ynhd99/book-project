package com.example.room.entity;

import com.example.room.entity.common.Base;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yangna
 * @date 2019/4/9
 */
@Data
public class HealthInfo extends Base {
    private String roomId;
    private String roomCode;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date checkDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    private BigDecimal checkPoint;
    private String remark;
}
