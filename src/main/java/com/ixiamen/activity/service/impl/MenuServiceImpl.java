package com.ixiamen.activity.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ixiamen.activity.base.BusinessException;
import com.ixiamen.activity.base.Constant;
import com.ixiamen.activity.base.PublicResultConstant;
import com.ixiamen.activity.entity.Menu;
import com.ixiamen.activity.entity.Role;
import com.ixiamen.activity.mapper.MenuMapper;
import com.ixiamen.activity.mapper.RoleMapper;
import com.ixiamen.activity.service.IMenuService;
import com.ixiamen.activity.util.ComUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 首页配置表 服务实现类
 * </p>
 *
 * @author dfdn123
 * @since 2020-04-26
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    private final MenuMapper menuMapper;

    private final RoleMapper roleMapper;

    public MenuServiceImpl(MenuMapper menuMapper, RoleMapper roleMapper) {
        this.menuMapper = menuMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    //redis方法级别的缓存，需要做缓存打开改注解即可
    // @Cacheable(value = "UserToRole",keyGenerator="wiselyKeyGenerator")
    public List<Menu> findMenuByRoleCode(String roleId)  {
        return menuMapper.findMenuByRoleCode(roleId);
    }

    @Override
    public  List<Menu> treeMenuList(String pId, List<Menu> list) {
        List<Menu> IteratorMenuList = new ArrayList<>();
        for (Menu m : list) {
            if (String.valueOf(m.getParentId()).equals(pId)) {
                List<Menu> childMenuList = treeMenuList(String.valueOf(m.getId()), list);
                m.setChildren(childMenuList);
                if(m.getCategory().equals(Constant.CATEGORY_MENU)){
                    IteratorMenuList.add(m);
                }
            }
        }
        return IteratorMenuList;
    }

    @Override
    public List<Menu> findMenuTree(Integer [] powerType)   {
        return menuMapper.findMenuTree(powerType);
    }

    @Override
    public List<Menu> findMenuByRoleCodeTree(String roleCode)  throws Exception {

        //查询角色管理端权限类型
        Role role = roleMapper.selectById(roleCode);
        if(ComUtil.isEmpty(role)){
            throw new BusinessException(PublicResultConstant.INVALID_ROLE);
        }
        Integer[] powerTypes = {0,role.getPowerType()};
        //查询所有菜单，并标注角色是否有此权限
        //List<VueMenu> menuList =  this.treeRoleMenuList(Constant.ROOT_MENU, list);
        return menuMapper.findMenuByRoleCodeAdmin(roleCode,powerTypes);
    }
    private List<Menu> treeRoleMenuList(String pId, List<Menu> list) {
        List<Menu> IteratorMenuList = new ArrayList<>();
        for (Menu m : list) {
            if (String.valueOf(m.getParentId()).equals(pId)) {
                List<Menu> childMenuList = treeRoleMenuList(String.valueOf(m.getId()), list);
                m.setChildren(childMenuList);
                IteratorMenuList.add(m);
            }
        }
        return IteratorMenuList;
    }

}
