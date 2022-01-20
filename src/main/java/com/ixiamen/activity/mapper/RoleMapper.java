package com.ixiamen.activity.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ixiamen.activity.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectPageByConditionRole(Page<Role> page, @Param("content") String content,
                                         @Param("status") Integer[] status);


}
