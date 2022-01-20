package com.ixiamen.activity.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ixiamen.activity.entity.OperationLog;
import com.ixiamen.activity.mapper.OperationLogMapper;
import com.ixiamen.activity.service.IOperationLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author luoyongbin
 * @since 2018-05-08
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

}
