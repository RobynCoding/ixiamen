package com.ixiamen.activity.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ixiamen.activity.entity.Role;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author luoyongbin
 * @since 2018-05-03
 */
public interface IRoleService extends IService<Role> {


    /**
     * 管理端新增角色
     */
    Role insertRoleByAdmin(Role role) throws Exception;

    /**
     * 管理端修改角色
     */
    Role updateRoleByAdmin(Role role) throws Exception;

    boolean deleteByRoleCode(String roleCode) throws Exception;

    /**
     * 角色分配用户
     *
     * @param addUserList 添加角色的用户主键列表
     * @param delUserList 删除角色的用户主键列表
     * @param roleCode    角色主键
     */
    boolean confUsers(String roleCode, List<String> addUserList, List<String> delUserList) throws Exception;


    /**
     * 角色分配用户
     *
     * @param addUserList 添加角色的用户主键列表
     * @param roleCode    角色主键
     */
    boolean confUsers(String roleCode, List<String> addUserList) throws Exception;

    /**
     * 查询角色列表
     */
    Page<Role> selectPageByConditionRole(Page<Role> rolePage, String content, Integer[] status);


    /**
     * 角色分配菜单和按钮权限
     *
     * @param menuList 菜单和按钮编码列表
     * @param roleCode 角色主键
     */
    boolean confMenus(String roleCode, List<String> menuList) throws Exception;
}
