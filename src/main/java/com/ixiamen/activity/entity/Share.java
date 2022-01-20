package com.ixiamen.activity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author luoyongbin123
 * @since 2022-01-20
 */
@TableName("tb_share")
public class Share extends Model<Share> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户分享主键ID
     */
    @TableId(value = "share_id", type = IdType.AUTO)
    private Integer shareId;
    /**
     * 用户主键
     */
    @TableField("user_id")
    private String userId;
    /**
     * 用户登录名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 电话
     */
    private String phone;
    /**
     * 移动电话
     */
    private String mobile;
    /**
     * 用户分享具体时间
     */
    @TableField("share_time")
    private Date shareTime;


    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.shareId;
    }

    @Override
    public String toString() {
        return "Share{" +
        "shareId=" + shareId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", phone=" + phone +
        ", mobile=" + mobile +
        ", shareTime=" + shareTime +
        "}";
    }
}
