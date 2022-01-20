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
@TableName("tb_integral")
public class Integral extends Model<Integral> {

    private static final long serialVersionUID = 1L;

    /**
     * 积分（团团）主键ID
     */
    @TableId(value = "integral_id", type = IdType.AUTO)
    private Integer integralId;
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
     * 获取积分（团团）数量
     */
    @TableField("integral_count")
    private Integer integralCount;
    /**
     * 获取积分（团团）来源（1：放烟花 2：用户分享 3：答题）
     */
    @TableField("integral_source")
    private Integer integralSource;
    /**
     * 获取积分（团团）时间
     */
    @TableField("integral_time")
    private Date integralTime;


    public Integer getIntegralId() {
        return integralId;
    }

    public void setIntegralId(Integer integralId) {
        this.integralId = integralId;
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

    public Integer getIntegralCount() {
        return integralCount;
    }

    public void setIntegralCount(Integer integralCount) {
        this.integralCount = integralCount;
    }

    public Integer getIntegralSource() {
        return integralSource;
    }

    public void setIntegralSource(Integer integralSource) {
        this.integralSource = integralSource;
    }

    public Date getIntegralTime() {
        return integralTime;
    }

    public void setIntegralTime(Date integralTime) {
        this.integralTime = integralTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.integralId;
    }

    @Override
    public String toString() {
        return "Integral{" +
        "integralId=" + integralId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", phone=" + phone +
        ", mobile=" + mobile +
        ", integralCount=" + integralCount +
        ", integralSource=" + integralSource +
        ", integralTime=" + integralTime +
        "}";
    }
}
