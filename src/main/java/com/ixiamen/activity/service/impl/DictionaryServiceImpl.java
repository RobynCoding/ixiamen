package com.ixiamen.activity.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ixiamen.activity.entity.Dictionary;
import com.ixiamen.activity.mapper.DictionaryMapper;
import com.ixiamen.activity.service.IDictionaryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author luoyongbin
 * @since 2020-04-28
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

}
