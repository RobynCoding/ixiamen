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
 * 用户表
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
@TableName("tb_user")
@ApiModel(value = "用户对象")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;
    /**
     * 用户主键
     */
    @ApiModelProperty(value = "用户主键")
    @TableId("user_id")
    private String userId;
    /**
     * 用户登录名
     */
    @ApiModelProperty(value = "用户登录名")
    @TableField("user_name")
    private String userName;
    /**
     * 真实名称
     */
    @ApiModelProperty(value = "真实名称")
    @TableField("real_name")
    private String realName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;
    /**
     * 移动电话
     */
    @ApiModelProperty(value = "移动电话")
    @TableField("mobile")
    private String mobile;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    @TableField("head_url")
    private String headUrl;
    /**
     * 状态值（1：启用，2：禁用，3：删除）
     */
    @ApiModelProperty(value = "状态值（1：启用，2：禁用，3：删除）")
    @TableField("status")
    private Integer status;

    /**
     * uid
     */
    @ApiModelProperty(value = "uuid")
    @TableField("uuid")
    private String uuid;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private String createTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人id")
    @TableField("creator")
    private String creator;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private String updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人id")
    @TableField("updater")
    private String updater;

    /**
     * 最新登录时间
     */
    @ApiModelProperty(value = "最新登录时间")
    @TableField("last_login_time")
    private String lastLoginTime;

    /**
     * 第三方登陆ID，仅保存信息
     */
    @ApiModelProperty(value = "第三方登陆ID，仅保存信息")
    @TableField("sso_id")
    private String ssoId;
    /**
     * 登陆来源（1：小程序 2：Web端 3：其他）
     */
    @ApiModelProperty(value = "登陆来源（1：小程序 2：Web端 3：其他）")
    @TableField("source")
    private Integer source;
    /**
     * 用户类型（1：系统用户 2：活动用户）
     */
    @ApiModelProperty(value = "用户类型（1：系统用户 2：活动用户）")
    @TableField("user_type")
    private Integer userType;


    /**
     * 登录token
     */
    @ApiModelProperty(value = "登录token")
    @TableField(exist = false)
    private String token;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称")
    @TableField(exist = false)
    private String roleName;

    /**
     * 角色编码
     */
    @ApiModelProperty(value = "角色编码")
    @TableField(exist = false)
    private String roleCode;

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
