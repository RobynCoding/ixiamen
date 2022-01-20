package com.ixiamen.activity.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ixiamen.activity.entity.LoginResultModel;
import com.ixiamen.activity.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author luoyongbin
 * @since 2018-05-03
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    User getUserByUserName(String username);

    LoginResultModel getLoginUserAndMenuInfo(User user) throws Exception;

    boolean deleteByUserNo(String userNo) throws Exception;

    Page<User> selectPageByConditionUser(Page<User> userPage, String info, Integer[] status, String roleCode);

    Page<User> selectPageByLoginHistory(Page<User> userPage, String info, Integer[] status, String startTime, String endTime);

    /**
     * 角色分配用户，查询用户列表（不包含已经有该角色的用户）
     */
    Page<User> selectPageByRoleConfUser(Page<User> userPage, String info, Integer[] status, String roleCode);

    LoginResultModel checkUsernameAndPasswd(JSONObject requestJson) throws Exception;


    /**
     * 管理端新增用户
     */
    User insertUserByAdmin(User user) throws Exception;

    /**
     * 管理端修改用户
     */
    User updateUserByAdmin(User user) throws Exception;

    /**
     * 冻结用户
     *
     * @param userNo     用户主键
     * @param currUserNo 操作人主键
     */
    boolean disableByUserNo(String userNo, String currUserNo) throws Exception;

    /**
     * 启用用户
     *
     * @param userNo     用户主键
     * @param currUserNo 操作人主键
     */
    boolean enableByUserNo(String userNo, String currUserNo) throws Exception;


    /**
     * 用户配置角色
     *
     * @param userNo   用户主键
     * @param roleCode 角色主键
     */
    boolean confRole(String userNo, String roleCode) throws Exception;

}
