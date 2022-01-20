package com.ixiamen.activity.service;

import com.baomidou.mybatisplus.service.IService;
import com.ixiamen.activity.entity.Menu;

import java.util.List;

/**
 * <p>
 * 首页配置表 服务类
 * </p>
 *
 * @author luoyongbin
 * @since 2020-04-26
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据角色查询菜单(包含视图)
     *
     * @param roleCode 角色主键
     */
    List<Menu> findMenuByRoleCode(String roleCode);

    /**
     * 获取菜单树形结构
     */
    List<Menu> treeMenuList(String pId, List<Menu> list) throws Exception;


    /**
     * 查询所有菜单，以树形结构展示
     * 菜单——》按钮操作
     */
    List<Menu> findMenuTree(Integer[] powerType);

    /**
     * 根据角色查询所有菜单，以树形结构展示
     * 菜单——》按钮操作 以selectFlag标注角色是否有此权限
     * param roleCode 角色主键
     */
    List<Menu> findMenuByRoleCodeTree(String roleCode) throws Exception;

}
