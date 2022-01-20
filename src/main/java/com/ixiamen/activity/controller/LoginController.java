package com.ixiamen.activity.controller;

import com.alibaba.fastjson.JSONObject;
import com.ixiamen.activity.annotation.CurrentUser;
import com.ixiamen.activity.annotation.Log;
import com.ixiamen.activity.annotation.Pass;
import com.ixiamen.activity.base.Constant;
import com.ixiamen.activity.config.ResponseHelper;
import com.ixiamen.activity.config.ResponseModel;
import com.ixiamen.activity.entity.LoginResultModel;
import com.ixiamen.activity.entity.User;
import com.ixiamen.activity.service.IRedisService;
import com.ixiamen.activity.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 登录接口
 *
 * @author dfdn
 * @since 2018-05-03
 */
@RestController
@Api(description = "身份认证模块")
public class LoginController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRedisService redisServie;

    @ApiOperation(value = "用户名密码登录", notes = "不需要Authorization", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "密码"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    @PostMapping("/login")
    @Log(action = "login", modelName = "login", description = "用户名密码登录接口")
    @Pass
    public ResponseModel<LoginResultModel> login(
            @RequestParam(name = "userName") String userName, @RequestParam(name = "password") String password) throws Exception {
        JSONObject requestJson = new JSONObject();
        requestJson.put("userName", userName);
        requestJson.put("password", password);
        return ResponseHelper.buildResponseModel(userService.checkUsernameAndPasswd(requestJson));
    }

    @ApiOperation(value = "退出登录", notes = "需要Authorization")
    @PostMapping("/signOut")
    @Log(action = "SignIn", modelName = "signOut", description = "退出登录")
    public ResponseModel signOut(@ApiIgnore @CurrentUser User currentUser) throws Exception {
        redisServie.del(currentUser.getUserId() + Constant.USER_TOKEN_KEY);
        return ResponseHelper.buildResponseModel("退出登录");
    }

}
