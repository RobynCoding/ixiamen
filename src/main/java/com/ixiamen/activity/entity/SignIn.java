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
 * @author luoyongbin
 * @since 2022-01-20
 */
@TableName("tb_sign_in")
public class SignIn extends Model<SignIn> {

    private static final long serialVersionUID = 1L;

    /**
     * 签到（放烟花）主键ID
     */
    @TableId(value = "sign_in_id", type = IdType.AUTO)
    private Integer signInId;
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
     * 放烟花时间（判断条件，每天只可以放一次）
     */
    @TableField("fireworks_time")
    private Date fireworksTime;
    /**
     * 签到（放烟花）具体时间
     */
    @TableField("sign_in_time")
    private Date signInTime;


    public Integer getSignInId() {
        return signInId;
    }

    public void setSignInId(Integer signInId) {
        this.signInId = signInId;
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

    public Date getFireworksTime() {
        return fireworksTime;
    }

    public void setFireworksTime(Date fireworksTime) {
        this.fireworksTime = fireworksTime;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.signInId;
    }

    @Override
    public String toString() {
        return "SignIn{" +
        "signInId=" + signInId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", phone=" + phone +
        ", mobile=" + mobile +
        ", fireworksTime=" + fireworksTime +
        ", signInTime=" + signInTime +
        "}";
    }
}
