package com.example.room.entity;

import com.example.room.entity.common.Base;
import lombok.Data;

/**
 * @author yangna
 * @date 2019/4/11
 */
@Data
public class RoleAuthority extends Base {
    private String roleId;
    private String authorityId;
}
