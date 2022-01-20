package com.ixiamen.activity.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author luoyongbin
 * @since 2018-06-25
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("tb_role_to_menu")
@ApiModel(value = "角色对象")
public class RoleToMenu extends Model<RoleToMenu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "role_to_menu_id", type = IdType.AUTO)
    private Integer roleToMenuId;
    /**
     * 角色代号
     */
    @ApiModelProperty(value = "角色代号")
    @TableField("role_code")
    private String roleCode;
    /**
     * 菜单代号,规范权限标识
     */
    @ApiModelProperty(value = "菜单代号,规范权限标识")
    @TableField("menu_code")
    private String menuCode;

    @Override
    protected Serializable pkVal() {
        return this.roleToMenuId;
    }

}
