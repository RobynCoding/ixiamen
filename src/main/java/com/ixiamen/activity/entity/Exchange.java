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
@TableName("tb_exchange")
public class Exchange extends Model<Exchange> {

    private static final long serialVersionUID = 1L;

    /**
     * 兑换优惠券主键ID
     */
    @TableId(value = "exchange_id", type = IdType.AUTO)
    private Integer exchangeId;
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
     * 优惠券ID
     */
    @TableField("coupon_id")
    private Integer couponId;
    /**
     * 消耗积分（团团）数量
     */
    @TableField("integral_count")
    private Integer integralCount;
    /**
     * 消耗积分（团团）时间
     */
    @TableField("exchange_time")
    private Date exchangeTime;


    public Integer getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Integer exchangeId) {
        this.exchangeId = exchangeId;
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

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getIntegralCount() {
        return integralCount;
    }

    public void setIntegralCount(Integer integralCount) {
        this.integralCount = integralCount;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.exchangeId;
    }

    @Override
    public String toString() {
        return "Exchange{" +
        "exchangeId=" + exchangeId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", phone=" + phone +
        ", mobile=" + mobile +
        ", couponId=" + couponId +
        ", integralCount=" + integralCount +
        ", exchangeTime=" + exchangeTime +
        "}";
    }
}
