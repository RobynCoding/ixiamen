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
@TableName("tb_win_prize")
public class WinPrize extends Model<WinPrize> {

    private static final long serialVersionUID = 1L;

    /**
     * 赢大奖ID
     */
    @TableId(value = "win_prize_id", type = IdType.AUTO)
    private Integer winPrizeId;
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
     * 放烟花数量
     */
    @TableField("sign_in_count")
    private Integer signInCount;
    /**
     * 用户分享数量
     */
    @TableField("share_count")
    private Integer shareCount;
    /**
     * 答题成就数量
     */
    @TableField("answer_achievement_count")
    private Integer answerAchievementCount;
    /**
     * 答题成就主题馆集合
     */
    @TableField("answer_achievement_themes")
    private String answerAchievementThemes;
    /**
     * 加入赢大奖时间
     */
    @TableField("win_prize_time")
    private Date winPrizeTime;


    public Integer getWinPrizeId() {
        return winPrizeId;
    }

    public void setWinPrizeId(Integer winPrizeId) {
        this.winPrizeId = winPrizeId;
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

    public Integer getSignInCount() {
        return signInCount;
    }

    public void setSignInCount(Integer signInCount) {
        this.signInCount = signInCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getAnswerAchievementCount() {
        return answerAchievementCount;
    }

    public void setAnswerAchievementCount(Integer answerAchievementCount) {
        this.answerAchievementCount = answerAchievementCount;
    }

    public String getAnswerAchievementThemes() {
        return answerAchievementThemes;
    }

    public void setAnswerAchievementThemes(String answerAchievementThemes) {
        this.answerAchievementThemes = answerAchievementThemes;
    }

    public Date getWinPrizeTime() {
        return winPrizeTime;
    }

    public void setWinPrizeTime(Date winPrizeTime) {
        this.winPrizeTime = winPrizeTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.winPrizeId;
    }

    @Override
    public String toString() {
        return "WinPrize{" +
        "winPrizeId=" + winPrizeId +
        ", userId=" + userId +
        ", userName=" + userName +
        ", phone=" + phone +
        ", mobile=" + mobile +
        ", signInCount=" + signInCount +
        ", shareCount=" + shareCount +
        ", answerAchievementCount=" + answerAchievementCount +
        ", answerAchievementThemes=" + answerAchievementThemes +
        ", winPrizeTime=" + winPrizeTime +
        "}";
    }
}
