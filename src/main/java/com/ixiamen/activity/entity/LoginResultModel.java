package com.ixiamen.activity.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "登录返回对象")
public class LoginResultModel {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录用户信息")
    private User user;

    @ApiModelProperty(value = "登录角色")
    private Role role;

    @ApiModelProperty(value = "用户有权限的菜单")
    private List<Menu> menuList;

   /* @ApiModelProperty(value = "用户有权限的按钮")
    private List<Menu> buttonList;*/
}
