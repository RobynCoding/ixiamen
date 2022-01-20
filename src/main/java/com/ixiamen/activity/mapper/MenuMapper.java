package com.ixiamen.activity.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ixiamen.activity.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author luoyongbin
 * @since 2020-04-26
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findMenuByRoleCode(@Param("roleCode") String roleCode);

    List<Menu> findMenuTree(@Param("powerType") Integer[] powerType);

    List<Menu> findMenuByRoleCodeAdmin(@Param("roleCode") String roleCode, @Param("powerType") Integer[] powerType);

    List<Menu> selectMenuByParentId(@Param("parentId") String parentId);

}
