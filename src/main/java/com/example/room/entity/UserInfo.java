package com.example.room.entity;

import com.example.room.common.advice.validatorGroup.Add;
import com.example.room.common.advice.validatorGroup.Update;
import com.example.room.entity.common.Base;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yangna
 * @date 2019/2/11
 */
@Data
public class UserInfo extends Base implements Serializable {

    private static final long serialVersionUID = -4401367127805427404L;
    @NotBlank(message = "手机号不能为空")
    private String userName;
    @NotBlank(message = "注册姓名不能为空",groups = Add.class)
    private String fullName;
    @NotBlank(message = "密码不能为空")
    private String userPass;
    @NotBlank(message = "旧密码不能为空",groups = Update.class)
    private String oldUserPass;
}
