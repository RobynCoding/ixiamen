package com.ixiamen.activity.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ixiamen.activity.entity.Dictionary;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author luoyongbin
 * @since 2020-04-28
 */
@Mapper
public interface DictionaryMapper extends BaseMapper<Dictionary> {

}
