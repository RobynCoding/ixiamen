package com.ixiamen.activity.controller;


import com.ixiamen.activity.annotation.Pass;
import com.ixiamen.activity.config.ResponseHelper;
import com.ixiamen.activity.config.ResponseModel;
import com.ixiamen.activity.entity.Menu;
import com.ixiamen.activity.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页配置表 前端控制器
 * </p>
 *
 * @author dfdn123
 * @since 2020-04-26
 */
@RestController
@Api(description = "菜单模块")
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    IMenuService vueMenuService;

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @GetMapping("/admin/menuListTree")
    @ApiOperation(value = "获取全部菜单列表，以树形结构展示", notes = "需要header里加入Authorization")
    @ApiImplicitParam(name = "powerType", value = "菜单权限类型 1：管理端菜单 2：用户端菜单 "
            , dataType = "Integer", paramType = "query", dataTypeClass = Integer.class)
    @Pass
    //@Cacheable(value = "MenuListTree",keyGenerator="wiselyKeyGenerator")
    public ResponseModel<List<Menu>> findMenuListTree(@RequestParam(name = "powerType", required = false) Integer powerType) {

        logger.info("-----------enter findMenuListTree-------------");
        logger.debug("-----------参数：" + powerType);

        Integer[] powerTypes = null;
        if (null != powerType) {
            powerTypes = new Integer[]{0, powerType};
        }
        List<Menu> menuList = vueMenuService.findMenuTree(powerTypes);
        return ResponseHelper.buildResponseModel(menuList);
    }


    @GetMapping("/admin/roleMenuListTree")
    @ApiOperation(value = "获取全部菜单列表，并标注角色具有的权限", notes = "需要header里加入Authorization")
    @ApiImplicitParam(name = "roleCode", value = "角色编码"
            , dataType = "String", paramType = "query", dataTypeClass = String.class)
    @Pass
    public ResponseModel<List<Menu>> findRoleMenuListTree(@RequestParam(name = "roleCode") String roleCode) throws Exception {

        logger.info("-----------enter findRoleMenuListTree-------------");
        logger.debug("-----------参数：" + roleCode);
        List<Menu> menuList = vueMenuService.findMenuByRoleCodeTree(roleCode);
        return ResponseHelper.buildResponseModel(menuList);
    }
}

