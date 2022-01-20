package com.ixiamen.activity.base;

import java.util.HashSet;
import java.util.Set;

/**
 * @author luoyongbin
 * @since 2018-05-03
 */
public class Constant {

    public static final int BYTE_BUFFER = 1024;

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASS = "123456";

    /**
     * .转义 Constant.ESCAPE_POINT
     */
    public static final String ESCAPE_POINT = "\\.";

    /**
     * controller请求信息set，在MyCommandLineRunner中初始化
     */
    public static Set<String> METHOD_URL_SET = new HashSet<>();

    /**
     * 用户注册默认角色
     */
    public static final int DEFAULT_REGISTER_ROLE = 5;

    public static final int BUFFER_MULTIPLE = 10;

    //验证码过期时间
    public static final Long PASS_TIME = 50000 * 60 * 1000L;

    //根菜单节点
    public static final String ROOT_MENU = "0";

    //菜单分类  menu/view  菜单/视图
    public static final String CATEGORY_MENU = "menu";

    //菜单分类  menu/view  菜单/视图
    public static final String CATEGORY_BUTTON = "view";

    public static Boolean isPass = false;

    //启用
    public static final int ENABLE = 1;
    //禁用/草稿
    public static final int DISABLE = 2;
    //删除
    public static final int DELETE = 3;


    //待审核
    public static final int CHECK_WAIT = 1;
    //审核通过
    public static final int CHECK_PASSED = 2;
    //审核不通过
    public static final int CHECK_NOPASSED = 3;

    public static class RoleType {
        //超级管理员角色编码
        public static final String ROLE_ADMIN = "role-admin";
        //管理员角色名称
        public static final String ADMIN = "admin";
        //普通用户角色名称
        public static final String USER = "user";
        //普通用户角色编码
        public static final String ROLE_USER = "role-user";
        //内置角色标识
        public static final String ROLE_BUILT_IN = "1";
    }

    //用户token在redis中缓存的后缀key  Constant.USER_TOKEN_KEY
    public static final String USER_TOKEN_KEY = "_login";


}
