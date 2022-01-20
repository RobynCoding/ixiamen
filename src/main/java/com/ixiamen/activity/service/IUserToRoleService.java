package com.ixiamen.activity.service;

import com.baomidou.mybatisplus.service.IService;
import com.ixiamen.activity.entity.UserToRole;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author luoyongbin
 * @since 2018-05-03
 */
public interface IUserToRoleService extends IService<UserToRole> {

    /**
     * 根据用户ID查询人员角色
     *
     * @param userNo 用户ID
     * @return 结果
     */
    UserToRole selectByUserNo(String userNo);

}
