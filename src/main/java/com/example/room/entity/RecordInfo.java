package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

/**
 * @author yangna
 * @date 2019/4/4
 */
@Data
public class RecordInfo extends Base {
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
}
