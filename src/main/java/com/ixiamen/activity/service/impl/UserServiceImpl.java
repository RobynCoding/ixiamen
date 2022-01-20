package com.ixiamen.activity.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ixiamen.activity.base.BusinessException;
import com.ixiamen.activity.base.Constant;
import com.ixiamen.activity.base.PublicResultConstant;
import com.ixiamen.activity.entity.*;
import com.ixiamen.activity.mapper.UserMapper;
import com.ixiamen.activity.service.*;
import com.ixiamen.activity.util.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author luoyongbin
 * @since 2018-05-03
 */
@Service
@Configuration
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Value("${token.time.out}")
    private static String token_time_out;

    private final IUserToRoleService userToRoleService;
    private final IMenuService menuService;

    private final UserMapper userMapper;

    private final IRoleService roleService;

    private final IRedisService redisServie;


    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(IUserToRoleService userToRoleService, IMenuService menuService, UserMapper userMapper, IRoleService roleService, IRedisService redisServie) {
        this.userToRoleService = userToRoleService;
        this.menuService = menuService;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.redisServie = redisServie;
    }

    @Override
    // @Cacheable(value = "UserToRole",keyGenerator="wiselyKeyGenerator")
    public User getUserByUserName(String username) {
        System.out.println("执行getUserByUserName方法了.....");
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.where("user_name={0}", username);
        return this.selectOne(ew);
    }


    @Override
    public LoginResultModel getLoginUserAndMenuInfo(User user) {
        LoginResultModel model = new LoginResultModel();

        //user.getUserNo()
        user.setToken(JWTUtil.sign(user.getUserId(), user.getUserId()));
        UserToRole userToRole = userToRoleService.selectByUserNo(user.getUserId());
        if (ComUtil.isEmpty(userToRole)) {
            userToRole = UserToRole.builder().roleCode(Constant.RoleType.ROLE_USER).userId(user.getUserId()).build();
            userToRoleService.insert(userToRole);
        }
        Role role = roleService.selectById(userToRole.getRoleCode());

        /*if(!ComUtil.isEmpty(role)){
            user.setRoleName(role.getRoleName());
        }*/
        //将token放入redis缓存中
        Long timeOut = Long.parseLong(token_time_out);
        redisServie.set(user.getUserId() + Constant.USER_TOKEN_KEY, user.getToken(), timeOut);

        //根据角色主键查询启用的菜单权限
        List<Menu> menuList = menuService.findMenuByRoleCode(userToRole.getRoleCode());
        /*List<Menu> retMenuList = menuService.treeMenuList(Constant.ROOT_MENU, menuList);
        for (Menu buttonMenu : menuList) {
            if (buttonMenu.getMenuType() == Constant.TYPE_BUTTON) {
                buttonList.add(buttonMenu);
            }
        }*/
        model.setRole(role);
        model.setUser(user);
        model.setMenuList(menuList);
        //model.setButtonList(buttonList);
        return model;
    }

    @Override
    public boolean deleteByUserNo(String userNo) throws Exception {
        User user = this.selectById(userNo);
        if (ComUtil.isEmpty(user)) {
            throw new BusinessException(PublicResultConstant.INVALID_USER);
        }
        EntityWrapper<UserToRole> ew = new EntityWrapper<>();
        ew.eq("user_no", userNo);
        userToRoleService.delete(ew);
        return this.deleteById(userNo);
    }

    @Override
    public Page<User> selectPageByConditionUser(Page<User> userPage, String info, Integer[] status, String roleCode) {
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        return userPage.setRecords(userMapper.selectPageByConditionUser(userPage, info, status, roleCode));
    }

    @Override
    public Page<User> selectPageByLoginHistory(Page<User> userPage, String info, Integer[] status, String startTime, String endTime) {
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        return userPage.setRecords(userMapper.selectPageByLoginHistory(userPage, info, status, startTime, endTime));
    }

    @Override
    public Page<User> selectPageByRoleConfUser(Page<User> userPage, String info, Integer[] status, String roleCode) {

        return userPage.setRecords(userMapper.selectPageByRoleConfUser(userPage, info, status, roleCode));
    }

    @Override
    public LoginResultModel checkUsernameAndPasswd(JSONObject requestJson) throws Exception {
        //由于 @ValidationParam注解已经验证过userName和password参数，所以可以直接get使用没毛病。
        String userName = requestJson.getString("userName");

        PasswordSecurity passwordSercurity = new MD5Security();
        User user = this.selectOne(new EntityWrapper<User>().where("user_name = {0} and status = 1 and user_type = 1", userName));
        /*if (ComUtil.isEmpty(user) || !BCrypt.checkpw(requestJson.getString("passWord"), user.getPassWord())) {
            throw new BusinessException(PublicResultConstant.INVALID_USERNAME_PASSWORD);
        }*/

        if (ComUtil.isEmpty(user) || !passwordSercurity.encode(requestJson.getString("password")).equals(user.getPassword())) {
            throw new BusinessException(PublicResultConstant.INVALID_USERNAME_PASSWORD);
        }

        return this.getLoginUserAndMenuInfo(user);
    }

    @Override
    public User insertUserByAdmin(User user) throws Exception {
        //User user = requestJson.toJavaObject(User.class);

        //检查用户名是否已经存在
        User temp = this.selectOne(new EntityWrapper<User>().where("user_name={0}", user.getUserName()));

        if (!ComUtil.isEmpty(temp)) {
            throw new BusinessException(PublicResultConstant.INVALID_USER_EXIST);
        }
        user.setUserId(GenerationSequenceUtil.generateUUID("user"));

        String createTime = DateTimeUtil.formatDateTimetoString(new Date());
        user.setCreateTime(createTime);
        user.setUpdateTime(createTime);
        user.setStatus(Constant.ENABLE);
        this.insert(user);
        return user;
    }

    @Override
    public User updateUserByAdmin(User user) throws Exception {

        //User user = requestJson.toJavaObject(User.class);
        //检查用户是否存在
        User userTemp = this.selectById(user.getUserId());
        if (ComUtil.isEmpty(userTemp)) {
            throw new BusinessException(PublicResultConstant.INVALID_USER);
        }
       /* userTemp.setRealName(requestJson.getString("realName"));
        userTemp.setEmail(requestJson.getString("email"));
        userTemp.setPhone(requestJson.getString("phone"));
        userTemp.setMobile(requestJson.getString("mobile"));
        *//*userTemp.setCompany(requestJson.getString("company"));*//*
        userTemp.setUpdater(requestJson.getString("updater"));*/
        user.setUpdateTime(DateTimeUtil.formatDateTimetoString(new Date()));
        this.updateById(user);
        return this.selectById(user.getUserId());
    }

    @Override
    public boolean disableByUserNo(String userNo, String currUserNo) throws Exception {
        //检查用户是否存在
        User userTemp = this.selectById(userNo);
        if (ComUtil.isEmpty(userTemp)) {
            throw new BusinessException(PublicResultConstant.INVALID_USER);
        }

        if (userNo.equals(currUserNo)) {
            throw new BusinessException(PublicResultConstant.DISABLE_MYSELF);
        }
        /*if(Constant.DISABLE == userTemp.getStatus().intValue()){
            throw new BusinessException(PublicResultConstant.INVALID_USER);
        }*/
        userTemp.setStatus(Constant.DISABLE);
        userTemp.setUpdater(currUserNo);
        userTemp.setUpdateTime(DateTimeUtil.formatDateTimetoString(new Date()));
        return this.updateById(userTemp);
    }

    @Override
    public boolean enableByUserNo(String userNo, String currUserNo) throws Exception {
        //检查用户是否存在
        User userTemp = this.selectById(userNo);
        if (ComUtil.isEmpty(userTemp)) {
            throw new BusinessException(PublicResultConstant.INVALID_USER);
        }

        userTemp.setStatus(Constant.ENABLE);
        userTemp.setUpdater(currUserNo);
        userTemp.setUpdateTime(DateTimeUtil.formatDateTimetoString(new Date()));
        return this.updateById(userTemp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confRole(String userNo, String roleCode) {

        //删除用户已有角色
        Wrapper<UserToRole> wrapper = new EntityWrapper<UserToRole>().where("user_no = {0}", userNo);
        userToRoleService.delete(wrapper);
       /* UserToRole userToRole = userToRoleService.selectByUserNo(userNo);
        if (!ComUtil.isEmpty(userToRole)) {

            throw new BusinessException(PublicResultConstant.INVALID_USER_ROLE_EXIST);
        }*/
        return userToRoleService.insert(UserToRole.builder().roleCode(roleCode).userId(userNo).build());

    }

}
