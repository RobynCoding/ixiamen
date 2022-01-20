package com.ixiamen.activity.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ixiamen.activity.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author luoyongbin
 * @since 2018-05-08
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {

}
