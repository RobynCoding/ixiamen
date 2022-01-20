package com.ixiamen.activity.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.ixiamen.activity.annotation.CurrentUser;
import com.ixiamen.activity.annotation.Log;
import com.ixiamen.activity.base.BusinessException;
import com.ixiamen.activity.base.PublicResultConstant;
import com.ixiamen.activity.config.ResponseHelper;
import com.ixiamen.activity.config.ResponseModel;
import com.ixiamen.activity.entity.User;
import com.ixiamen.activity.service.IUserService;
import com.ixiamen.activity.util.ComUtil;
import com.ixiamen.activity.util.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户前端控制器
 * </p>
 *
 * @author dfdn
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/user")
@Api(description = "用户服务模块")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService userService;

    @ApiOperation(value = "获取当前登录用户信息", notes = "获取当前登录用户信息，需要header里加入Authorization")
    @GetMapping("/admin/currentUser")
    public ResponseModel<User> getUser(@ApiIgnore @CurrentUser User currentUser) {
        return ResponseHelper.buildResponseModel(currentUser);
    }


    @PostMapping("/admin/userList")
    @ApiOperation(value = "获取用户列表", notes = "需要header里加入Authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "第几页"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "info", value = "用户名或姓名"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "roleCode", value = "角色编码"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    //@RequiresPermissions("user:list")
    public ResponseModel<Page<User>> findUserList(@RequestParam(name = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                                                  @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                  @RequestParam(name = "info", defaultValue = "", required = false) String info,
                                                  @RequestParam(name = "roleCode", required = false) String roleCode) {
        //启用或禁用的用户
        Integer[] status = {1, 2};
        //自定义分页关联查询列表
        Page<User> userPage = userService.selectPageByConditionUser(new Page<>(pageIndex, pageSize), info, status, roleCode);
        return ResponseHelper.buildResponseModel(userPage);
    }

    @PostMapping("/admin/user/loginHistory")
    @ApiOperation(value = "获取用户列表", notes = "需要header里加入Authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "第几页"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "info", value = "用户名或姓名"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "startTime", value = "开始时间"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    //@RequiresPermissions("user:list")
    public ResponseModel<Page<User>> findUserLoginHistory(@RequestParam(name = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                                                          @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                          @RequestParam(name = "info", defaultValue = "", required = false) String info,
                                                          @RequestParam(name = "startTime", required = false) String startTime,
                                                          @RequestParam(name = "endTime", required = false) String endTime) {
        //启用或禁用的用户
        Integer[] status = {1, 2};
        //自定义分页关联查询列表
        Page<User> userPage = userService.selectPageByLoginHistory(new Page<>(pageIndex, pageSize), info, status, startTime, endTime);
        return ResponseHelper.buildResponseModel(userPage);
    }

    @PostMapping("/admin/userListConfigRole")
    @ApiOperation(value = "角色分配用户时获取用户列表", notes = "需要header里加入Authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "第几页"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "roleCode", value = "角色编码"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "info", value = "用户名或姓名"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    //@RequiresPermissions("user:list")
    public ResponseModel<Page<User>> userListConfigRole(@RequestParam(name = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                                                        @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                        @RequestParam(name = "roleCode", required = false) String roleCode,
                                                        @RequestParam(name = "info", defaultValue = "", required = false) String info) {
        //启用的用户
        Integer[] status = {1};
        //自定义分页关联查询列表
        Page<User> userPage = userService.selectPageByRoleConfUser(new Page<>(pageIndex, pageSize), info, status, roleCode);
        return ResponseHelper.buildResponseModel(userPage);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "path", dataTypeClass = String.class)
    @GetMapping(value = "/admin/{userId}")
    @RequiresPermissions(value = {"user:list"})
    public ResponseModel<User> findOneUser(@PathVariable("userId") String userId, @ApiIgnore @CurrentUser User currentUser) {
        User user = userService.selectById(userId);
        return ResponseHelper.buildResponseModel(user);
    }

    @Log(action = "/admin/add", modelName = "User", description = "添加用户")
    @PostMapping("/admin/add")
    @ApiOperation(value = "添加用户", notes = "需要header里加入Authorization")
    @ApiImplicitParam(name = "requestJson", value = "{\"userName\":\"用户名称\",\"realName\":\"姓名\",\"email\":\"邮箱\",\"phone\":\"用户电话\",\"mobile\":\"移动电话\"}", required = true, dataType = "String", paramType = "body", dataTypeClass = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "realName", value = "真实姓名"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "email", value = "邮箱"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "phone", value = "用户电话"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "mobile", value = "移动电话"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    //@RequiresPermissions("user:add")
    public ResponseModel<User> addUser(
            HttpServletRequest request,
            @ApiIgnore @CurrentUser User currentUser) throws Exception {


        User user = new User();
        HttpRequestUtil.bind(request, user);

        user.setCreator(currentUser.getUserId());

        return ResponseHelper.buildResponseModel(userService.insertUserByAdmin(user));
    }

    @Log(action = "/admin/update", modelName = "User", description = "编辑用户")
    @ApiOperation(value = "编辑用户", notes = "编辑用户，需要header里加入Authorization")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "userId", value = "用户编码"
                    , dataType = "String", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "userName", value = "用户名称"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "realName", value = "真实姓名"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "email", value = "邮箱"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "phone", value = "用户电话"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "mobile", value = "移动电话"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    @PostMapping("/admin/update")
    //@RequiresPermissions("user:edit")
    public ResponseModel<User> updateUser(
            HttpServletRequest request,
            @ApiIgnore @CurrentUser User currentUser) throws Exception {

        User user = new User();
        HttpRequestUtil.bind(request, user);

        if (ComUtil.isEmpty(user.getUserId())) {
            throw new BusinessException(PublicResultConstant.UserResult.NULL_USER_USERNO);
        }
        user.setUpdater(currentUser.getUserId());

        return ResponseHelper.buildResponseModel(userService.updateUserByAdmin(user));
    }

    @Log(action = "/admin/updateCurr", modelName = "User", description = "编辑当前登录用户")
    @ApiOperation(value = "编辑当前登录用户", notes = "编辑当前用户，需要header里加入Authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名"
                    , dataType = "String", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "realName", value = "真实姓名"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "email", value = "邮箱"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "phone", value = "用户电话"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "mobile", value = "移动电话"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    @PostMapping("/admin/updateCurr")
    public ResponseModel<User> updateCurrUser(
            HttpServletRequest request,
            @ApiIgnore @CurrentUser User currentUser) throws Exception {

        User user = new User();
        HttpRequestUtil.bind(request, user);
        String userName = user.getUserName();
        if (ComUtil.isEmpty(userName)) {
            throw new BusinessException(PublicResultConstant.UserResult.NULL_USER_USERNAME);
        }

        if (!userName.equals(currentUser.getUserName())) {
            throw new BusinessException(PublicResultConstant.UserResult.INVALID_USER_SELF);
        }

        /*if(ComUtil.isEmpty(user.getUserNo())){
            throw new BusinessException(PublicResultConstant.UserResult.NULL_USER_USERNO);
        }*/
        user.setUserId(currentUser.getUserId());
        user.setUpdater(currentUser.getUserId());

        return ResponseHelper.buildResponseModel(userService.updateUserByAdmin(user));
    }

    @Log(action = "/admin/disable", modelName = "User", description = "冻结用户")
    @GetMapping(value = "/admin/disable")
    @ApiOperation(value = "冻结用户", notes = "需要header里加入Authorization")
    @ApiImplicitParam(name = "userId", value = "用户id"
            , dataType = "String", paramType = "query", dataTypeClass = String.class)
    //@RequiresPermissions("user:disable")
    public ResponseModel disableByUserNo(
            @RequestParam(name = "userId") String userId,
            @ApiIgnore @CurrentUser User currentUser) throws Exception {
        logger.info("-----------enter disableByUserNo-------------");
        if (ComUtil.isEmpty(userId)) {
            throw new BusinessException(PublicResultConstant.UserResult.NULL_USER_USERNO);
        }
        String currUserNo = currentUser.getUserId();
        userService.disableByUserNo(userId, currUserNo);
        return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCEED);
    }


    @Log(action = "/admin/enable", modelName = "User", description = "启用用户")
    @GetMapping(value = "/admin/enable")
    @ApiOperation(value = "启用用户", notes = "需要header里加入Authorization")
    @ApiImplicitParam(name = "userId", value = "用户id"
            , dataType = "String", paramType = "query", dataTypeClass = String.class)
    //@RequiresPermissions("user:enable")
    public ResponseModel enableByUserNo(
            @RequestParam(name = "userNo") String userId,
            @ApiIgnore @CurrentUser User currentUser) throws Exception {
        logger.info("-----------enter enableByUserNo-------------");
        if (ComUtil.isEmpty(userId)) {
            throw new BusinessException(PublicResultConstant.UserResult.NULL_USER_USERNO);
        }
        String currUserId = currentUser.getUserId();
        userService.enableByUserNo(userId, currUserId);
        return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCEED);
    }


    @Log(action = "/admin/confRole", modelName = "UserToRole", description = "用户分配角色")
    @PostMapping(value = "/admin/confRole")
    @ApiOperation(value = "用户分配角色", notes = "需要header里加入Authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "roleCode", value = "角色id"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    //@RequiresPermissions("user:confRole")
    public ResponseModel confRoleByUserNo(
            @RequestParam(name = "userId") String userId, @RequestParam(name = "roleCode") String roleCode) throws Exception {

        if (ComUtil.isEmpty(userId)) {
            throw new BusinessException(PublicResultConstant.UserResult.NULL_USER_USERNO);
        }

        if (ComUtil.isEmpty(roleCode)) {
            throw new BusinessException(PublicResultConstant.UserResult.NULL_USER_ROLECODE);
        }
        userService.confRole(userId, roleCode);
        return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCEED);
    }

}