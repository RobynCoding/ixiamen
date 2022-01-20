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

import java.util.List;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author luoyongbin
 * @since 2020-04-26
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "菜单对象")
@TableName("tb_menu")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 父菜单主键
     */
    @ApiModelProperty(value = "父菜单主键")
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 菜单编号
     */
    @ApiModelProperty(value = "菜单编号")
    @TableField("menu_code")
    private String menuCode;

    /**
     * 图标
     */
    @ApiModelProperty(value = "菜单图标")
    @TableField("icon")
    private String icon;


    @ApiModelProperty(value = "菜单地址")
    @TableField("path")
    private String path;

    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    @TableField("component")
    private String component;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    @TableField("menu_name")
    private String menuName;
    /**
     * 分类  menu/view  菜单/视图
     */
    @ApiModelProperty(value = "分类  menu/view  菜单/视图")
    @TableField("menu_name")
    private String category;
    /**
     * 代码控制权限标识符
     */
    @ApiModelProperty(value = "代码控制权限标识符")
    @TableField("code")
    private String code;
    /**
     * 菜单权限类型 1：管理端菜单 2：用户端菜单
     */
    @ApiModelProperty(value = "菜单权限类型 1：管理端菜单 2：用户端菜单 ")
    @TableField("power_type")
    private Integer powerType;
    /**
     * 排列顺序
     */
    @ApiModelProperty(value = "排列顺序")
    @TableField("display_order")
    private Integer displayOrder;
    /**
     * 状态值（1：启用，2：禁用，3：删除）
     */
    @ApiModelProperty(value = "状态值（1：启用，2：禁用，3：删除）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 角色是否有选中此菜单标识（查询角色菜单的时候使用）
     * 0：否 1：是
     */
    @ApiModelProperty(value = "角色是否有选中此菜单标识（查询角色菜单的时候使用）0：否 1：是")
    @TableField(exist = false)
    private Integer selectFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
