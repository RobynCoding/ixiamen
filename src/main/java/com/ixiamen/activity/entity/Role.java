package com.ixiamen.activity.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 角色表
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
@TableName("tb_role")
@ApiModel(value = "角色对象")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

    /**
     * 角色代号主键
     */
    @ApiModelProperty(value = "角色代号主键")
    @TableId("role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @TableField("role_name")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述")
    @TableField("role_desc")
    private String roleDesc;

    /**
     * 角色类型 1：系统内置角色 0：普通角色
     */
    @ApiModelProperty(value = "角色类型 1：系统内置角色 0：普通角色")
    @TableField("role_type")
    private String roleType;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private String createTime;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者id")
    @TableField("creater")
    private String creater;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private String updateTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者id")
    @TableField("updater")
    private String updater;

    /**
     * 状态值（1：启用，2：禁用，3：删除）
     */
    @ApiModelProperty(value = "状态值（1：启用，2：禁用，3：删除）")
    @TableField("status")
    private Integer status;


    /**
     * 角色权限类型  1：管理端角色 2：用户端角色
     */
    @ApiModelProperty(value = "角色权限类型  1：管理端角色 2：用户端角色")
    @TableField("power_type")
    private Integer powerType;

    @Override
    protected Serializable pkVal() {
        return this.roleCode;
    }

}
