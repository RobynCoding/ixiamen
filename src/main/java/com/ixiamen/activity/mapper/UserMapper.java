package com.ixiamen.activity.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ixiamen.activity.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author luoyongbin
 * @since 2018-05-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    //等同于编写一个普通 list 查询，mybatis-plus 自动替你分页
    List<User> selectPageByConditionUser(Page<User> page, @Param("info") String info,
                                         @Param("status") Integer[] status, String roleCode);

    List<User> selectPageByLoginHistory(Page<User> page, @Param("info") String info,
                                        @Param("status") Integer[] status, String startTime, String endTime);


    /**
     * 角色分配用户，查询用户信息，不包含已有该角色的用户
     */
    List<User> selectPageByRoleConfUser(Page<User> page, @Param("info") String info,
                                        @Param("status") Integer[] status, @Param("roleCode") String roleCode);


}
