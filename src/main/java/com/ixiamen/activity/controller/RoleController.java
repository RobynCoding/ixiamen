package com.ixiamen.activity.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ixiamen.activity.annotation.CurrentUser;
import com.ixiamen.activity.annotation.Log;
import com.ixiamen.activity.base.BusinessException;
import com.ixiamen.activity.base.PublicResultConstant;
import com.ixiamen.activity.config.ResponseHelper;
import com.ixiamen.activity.config.ResponseModel;
import com.ixiamen.activity.entity.Role;
import com.ixiamen.activity.entity.User;
import com.ixiamen.activity.service.IRoleService;
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
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色前端控制器
 * </p>
 *
 * @author dfdn
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/role")
@Api(description = "角色模块")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    IRoleService roleService;

    @PostMapping("/admin/roleList")
    @ApiOperation(value = "获取全部角色列表", notes = "需要header里加入Authorization")
    public ResponseModel<List<Role>> findRoleList() {

        Wrapper<Role> wrapper = new EntityWrapper<Role>().where("status = 1").orderBy("create_time", true);
        List<Role> roleList = roleService.selectList(wrapper);
        return ResponseHelper.buildResponseModel(roleList);
    }

    @PostMapping("/admin/rolePageList")
    @ApiOperation(value = "获取分页角色列表", notes = "需要header里加入Authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "第几页"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageSize", value = "每页多少条"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "content", value = "角色名称或描述"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    public ResponseModel<Page<Role>> findRolePageList(@RequestParam(name = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                                                      @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                                      @RequestParam(name = "content", defaultValue = "", required = false) String content) {
        //状态
        Integer[] status = {1};
        //自定义分页关联查询列表
        Page<Role> rolePage = roleService.selectPageByConditionRole(new Page<>(pageIndex, pageSize), content, status);
        return ResponseHelper.buildResponseModel(rolePage);
    }

    @Log(action = "/admin/add", modelName = "Role", description = "添加角色")
    @PostMapping("/admin/add")
    @ApiOperation(value = "添加角色", notes = "需要header里加入Authorization")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "roleName", value = "角色名称"
                    , dataType = "String", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "roleDesc", value = "角色描述"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "powerType", value = "角色权限类型  1：管理端角色 2：用户端角色"
                    , dataType = "String", paramType = "query", required = true, dataTypeClass = String.class)
    })
    public ResponseModel<Role> addRole(HttpServletRequest request,
                                       @ApiIgnore @CurrentUser User currentUser) throws Exception {

        Role role = new Role();
        HttpRequestUtil.bind(request, role);

        if (ComUtil.isEmpty(role.getRoleName())) {
            throw new BusinessException(PublicResultConstant.RoleResult.NULL_ROLE_NAME);
        }

        role.setCreater(currentUser.getUserId());
        return ResponseHelper.buildResponseModel(roleService.insertRoleByAdmin(role));
    }

    @Log(action = "/admin/update", modelName = "User", description = "修改角色")
    @ApiOperation(value = "修改角色", notes = "需要header里加入Authorization")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "roleName", value = "角色名称"
                    , dataType = "String", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "roleDesc", value = "角色描述"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "roleCode", value = "角色编码"
                    , dataType = "String", paramType = "query", required = true, dataTypeClass = String.class)
    })
    @PostMapping("/admin/update")
    public ResponseModel<Role> updateRole(HttpServletRequest request,
                                          @ApiIgnore @CurrentUser User currentUser) throws Exception {

        Role role = new Role();
        HttpRequestUtil.bind(request, role);

        if (ComUtil.isEmpty(role.getRoleCode())) {
            throw new BusinessException(PublicResultConstant.RoleResult.NULL_ROLE_CODE);
        }

        if (ComUtil.isEmpty(role.getRoleName())) {
            throw new BusinessException(PublicResultConstant.RoleResult.NULL_ROLE_NAME);
        }

        role.setUpdater(currentUser.getUserId());
        return ResponseHelper.buildResponseModel(roleService.updateRoleByAdmin(role));
    }

    @Log(action = "/admin/delete", modelName = "Role", description = "删除角色")
    @PostMapping(value = "/admin/delete")
    @ApiOperation(value = "删除角色", notes = "需要header里加入Authorization")
    @ApiImplicitParam(name = "roleCode", value = "角色id"
            , dataType = "String", paramType = "query", dataTypeClass = String.class)
    //用户需要有删除角色的按钮权限
    public ResponseModel deleteRole(
            @RequestParam(name = "roleCode") String roleCode,
            @ApiIgnore @CurrentUser User currentUser) throws Exception {
        if (ComUtil.isEmpty(roleCode)) {
            throw new BusinessException(PublicResultConstant.RoleResult.NULL_ROLE_CODE);
        }
        roleService.deleteByRoleCode(roleCode);
        return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCEED);
    }

    @ApiOperation(value = "获取角色详细信息", notes = "根据url的roleCode来获取角色详细信息，需要header里加入Authorization")
    @ApiImplicitParam(name = "roleCode", value = "角色ID", required = true, dataType = "String", paramType = "path", dataTypeClass = String.class)
    @GetMapping(value = "/admin/{roleCode}")
    @RequiresPermissions(value = {"role:list"})
    public ResponseModel<Role> findOneRole(@PathVariable("roleCode") String roleCode) throws Exception {
        if (ComUtil.isEmpty(roleCode)) {
            throw new BusinessException(PublicResultConstant.RoleResult.NULL_ROLE_CODE);
        }
        Role user = roleService.selectById(roleCode);
        return ResponseHelper.buildResponseModel(user);
    }


    @Log(action = "/admin/confUser", modelName = "UserToRole", description = "角色分配用户")
    @PostMapping(value = "/admin/confUser")
    @ApiOperation(value = "角色分配用户", notes = "需要header里加入Authorization")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "roleCode", value = "角色id"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "addUserNos", value = "分配角色的用户id字符串，多个用户使用','隔开"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    public ResponseModel confUserByRoleCode(
            @RequestParam(name = "roleCode") String roleCode,
            @RequestParam(name = "addUserNos", defaultValue = "") String addUserNos) throws Exception {

        if (ComUtil.isEmpty(roleCode)) {
            throw new BusinessException(PublicResultConstant.RoleResult.NULL_ROLE_CODE);
        }
        List<String> addUserNoList;
        if (!ComUtil.isEmpty(addUserNos)) {
            addUserNoList = Arrays.asList(addUserNos.split(","));
        } else {
            throw new BusinessException(PublicResultConstant.RoleResult.NULL_ROLE_CONF_USERNO);
        }

        roleService.confUsers(roleCode, addUserNoList);
        return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCEED);
    }


    @Log(action = "/admin/confMenu", modelName = "Role", description = "角色分配菜单权限")
    @PostMapping(value = "/admin/confMenu")
    @ApiOperation(value = "角色分配菜单权限", notes = "需要header里加入Authorization")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "roleCode", value = "角色id"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "menuCodes", value = "菜单编码字符串，多个用户使用','隔开"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)
    })
    public ResponseModel confMenuByRoleCode(
            @RequestParam(name = "roleCode") String roleCode, @RequestParam(name = "menuCodes") String menuCodes) throws Exception {

        if (ComUtil.isEmpty(menuCodes)) {
            throw new BusinessException(PublicResultConstant.RoleResult.NULL_RMENU_CODES);
        }
        if (ComUtil.isEmpty(roleCode)) {
            throw new BusinessException(PublicResultConstant.RoleResult.NULL_ROLE_CODE);
        }
        List<String> menuCodeList = Arrays.asList(menuCodes.split(","));
        roleService.confMenus(roleCode, menuCodeList);
        return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCEED);
    }
}

