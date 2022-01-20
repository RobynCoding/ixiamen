package com.ixiamen.activity.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ixiamen.activity.base.BusinessException;
import com.ixiamen.activity.base.Constant;
import com.ixiamen.activity.base.PublicResultConstant;
import com.ixiamen.activity.entity.Role;
import com.ixiamen.activity.entity.RoleToMenu;
import com.ixiamen.activity.entity.UserToRole;
import com.ixiamen.activity.mapper.RoleMapper;
import com.ixiamen.activity.service.IRoleService;
import com.ixiamen.activity.service.IRoleToMenuService;
import com.ixiamen.activity.service.IUserToRoleService;
import com.ixiamen.activity.util.ComUtil;
import com.ixiamen.activity.util.DateTimeUtil;
import com.ixiamen.activity.util.GenerationSequenceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色服务实现类
 * </p>
 *
 * @author luoyongbin
 * @since 2018-05-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    private final RoleMapper mapper;
    final
    IUserToRoleService userToRoleService;
    final
    IRoleToMenuService roleToMenuService;

    public RoleServiceImpl(RoleMapper mapper, IUserToRoleService userToRoleService, IRoleToMenuService roleToMenuService) {
        this.mapper = mapper;
        this.userToRoleService = userToRoleService;
        this.roleToMenuService = roleToMenuService;
    }

    @Override
    public Role insertRoleByAdmin(Role role) {
        //Role role = requestJson.toJavaObject(Role.class);

        //角色类型 1：系统内置角色 0：普通角色
        role.setRoleType("0");
        role.setRoleCode(GenerationSequenceUtil.generateUUID("role"));
        String createTime = DateTimeUtil.formatDateTimetoString(new Date());
        role.setCreateTime(createTime);
        role.setUpdateTime(createTime);
        role.setStatus(Constant.ENABLE);
        this.insert(role);
        return role;
    }

    @Override
    public Role updateRoleByAdmin(Role role) throws Exception {

        //Role role = requestJson.toJavaObject(Role.class);
        //检查角色是否存在
        Role roleTemp = this.selectById(role.getRoleCode());
        if (ComUtil.isEmpty(roleTemp)) {
            throw new BusinessException(PublicResultConstant.INVALID_ROLE);
        }
       /* roleTemp.setRoleName(requestJson.getString("roleName"));
        roleTemp.setRoleDesc(requestJson.getString("roleDesc"));
        roleTemp.setUpdater(requestJson.getString("updater"));*/
        role.setUpdateTime(DateTimeUtil.formatDateTimetoString(new Date()));
        this.updateById(role);
        return this.selectById(role.getRoleCode());
    }

    @Override
    public boolean deleteByRoleCode(String roleCode) throws Exception {
        Role role = this.selectById(roleCode);
        if (ComUtil.isEmpty(role)) {
            throw new BusinessException(PublicResultConstant.INVALID_ROLE);
        }

        if (Constant.RoleType.ROLE_BUILT_IN.equals(role.getRoleType())) {
            throw new BusinessException(PublicResultConstant.ROLE_BUILT_IN);
        }
        Wrapper<UserToRole> wrapper = new EntityWrapper<UserToRole>().where("role_code = {0}", roleCode);
        List<UserToRole> list = userToRoleService.selectList(wrapper);
        if (!ComUtil.isEmpty(list)) {
            throw new BusinessException(PublicResultConstant.ROLE_USER_USED);
        }
        role.setStatus(Constant.DELETE);
        return this.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confUsers(String roleCode, List<String> addUserList, List<String> delUserList) throws Exception {
        boolean result = true;
        if (!ComUtil.isEmpty(roleCode)) {
            Role role = this.selectById(roleCode);
            if (ComUtil.isEmpty(role)) {
                throw new BusinessException(PublicResultConstant.INVALID_ROLE);
            }
            List<UserToRole> userToRoleList = new ArrayList<>();
            result = isResult(roleCode, addUserList, result, userToRoleList);

            if (!ComUtil.isEmpty(delUserList)) {
                for (String userNo : delUserList) {
                    //删除用户已有的当前角色
                    Wrapper<UserToRole> wrapper = new EntityWrapper<UserToRole>().where("user_no = {0}", userNo);
                    wrapper.and("role_code = {0}", roleCode);
                    userToRoleService.delete(wrapper);
                }
            }

        } else {
            throw new BusinessException(PublicResultConstant.INVALID_ROLE);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confUsers(String roleCode, List<String> addUserList) throws Exception {
        boolean result = true;
        if (!ComUtil.isEmpty(roleCode)) {
            Role role = this.selectById(roleCode);
            if (ComUtil.isEmpty(role)) {
                throw new BusinessException(PublicResultConstant.INVALID_ROLE);
            }
            List<UserToRole> userToRoleList = new ArrayList<>();
            result = isResult(roleCode, addUserList, result, userToRoleList);


        } else {
            throw new BusinessException(PublicResultConstant.INVALID_ROLE);
        }
        return result;
    }

    private boolean isResult(String roleCode, List<String> addUserList, boolean result, List<UserToRole> userToRoleList) {
        if (!ComUtil.isEmpty(addUserList)) {
            for (String userId : addUserList) {
                //删除用户已有角色
                Wrapper<UserToRole> wrapper = new EntityWrapper<UserToRole>().where("user_id = {0}", userId);
                userToRoleService.delete(wrapper);
                userToRoleList.add(UserToRole.builder().roleCode(roleCode).userId(userId).build());
            }

            result = userToRoleService.insertBatch(userToRoleList);
        }
        return result;
    }

    @Override
    public Page<Role> selectPageByConditionRole(Page<Role> rolePage, String content, Integer[] status) {
        return rolePage.setRecords(mapper.selectPageByConditionRole(rolePage, content, status));
    }

    @Override
    public boolean confMenus(String roleCode, List<String> menuList) throws Exception {
        boolean result;
        if (!ComUtil.isEmpty(roleCode)) {

            Role role = this.selectById(roleCode);
            if (ComUtil.isEmpty(role)) {
                throw new BusinessException(PublicResultConstant.INVALID_ROLE);
            }
            //删除角色已有菜单
            Wrapper<RoleToMenu> wrapper = new EntityWrapper<RoleToMenu>().where("role_code = {0}", roleCode);
            roleToMenuService.delete(wrapper);
            List<RoleToMenu> roleToMenuList = new ArrayList<>();
            for (String menuCode : menuList) {
                roleToMenuList.add(RoleToMenu.builder().roleCode(roleCode).menuCode(menuCode).build());
            }
            result = roleToMenuService.insertBatch(roleToMenuList);
        } else {
            throw new BusinessException(PublicResultConstant.INVALID_ROLE);
        }
        return result;
    }
}
