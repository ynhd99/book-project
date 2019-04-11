package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

import java.util.List;

/**
 * @author yangna
 * @date 2019/3/13
 */
@Data
public class AuthorityInfo {
    private String id;
    private String authorityName;
    private String authorityCode;
    private int isSelect;
    private List<AuthorityInfo> children;
    private String parentId;
}
