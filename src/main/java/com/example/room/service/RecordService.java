package com.example.room.service;

import com.example.room.entity.RecordInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangna
 * @date 2019/4/4
 */
public interface RecordService {
    /**
     * 新增公告内容
     * @param recordInfo
     * @return
     */
    int addRecord(RecordInfo recordInfo);

    /**
     * 更新公告内容
     * @param recordInfo
     * @return
     */
    int updateRecord(RecordInfo recordInfo);

    /**
     * 删除公告
     * @param recordInfo
     * @return
     */
    int deleteRecord(RecordInfo recordInfo);

    /**
     * 获取公告内容
     * @return
     */
    List<RecordInfo> findRecordList(RecordInfo recordInfo);
}
