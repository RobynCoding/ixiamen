package com.ixiamen.activity.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ixiamen.activity.annotation.Pass;
import com.ixiamen.activity.base.BusinessException;
import com.ixiamen.activity.config.ResponseHelper;
import com.ixiamen.activity.config.ResponseModel;
import com.ixiamen.activity.entity.Dictionary;
import com.ixiamen.activity.service.IDictionaryService;
import com.ixiamen.activity.util.ComUtil;
import com.ixiamen.activity.util.HttpRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author luoyongbin
 * @since 2020-04-28
 */
@RestController
@Api(description = "字典模块")
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    IDictionaryService dictionaryService;

    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    @GetMapping("/getDictionary")
    @ApiOperation(value = "根据字典编码获取字典信息", notes = "不需要header里加入Authorization")
    @ApiImplicitParam(name = "dictionaryCode", value = "字典编码"
            , dataType = "String", paramType = "query", dataTypeClass = String.class)
    @Pass
    //@Cacheable(value = "Dictionary",keyGenerator="wiselyKeyGenerator")
    public ResponseModel<Dictionary> findDictionary(@RequestParam(name = "dictionaryCode") String dictionaryCode) {

        logger.info("-----------enter findDictionary-------------");
        logger.debug("-----------参数：" + dictionaryCode);
        Wrapper<Dictionary> wrapper = new EntityWrapper<Dictionary>().where("dictionary_code={0}", dictionaryCode);
        Dictionary dictionary = dictionaryService.selectOne(wrapper);
        return ResponseHelper.buildResponseModel(dictionary);
    }

    @PostMapping("/updateDictionary")
    @ApiOperation(value = "编辑字典", notes = "根据字典id修改字典信息，不需要header里加入Authorization")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "字典id"
                    , dataType = "Integer", paramType = "query", required = true, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "dictionaryValue", value = "字典值"
                    , dataType = "String", paramType = "query", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "dictionaryName", value = "字典名称"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "dictionaryCode", value = "字典编码"
                    , dataType = "String", paramType = "query", dataTypeClass = String.class)

    })
    @Pass
    public ResponseModel<Dictionary> updateDictionary(
            HttpServletRequest request) throws Exception {
        logger.info("-----------enter updateDictionary-------------");

        Dictionary dictionary = new Dictionary();
        HttpRequestUtil.bind(request, dictionary);
        if (ComUtil.isEmpty(dictionary.getId())) {
            throw new BusinessException("id不能为空");
        }

        dictionaryService.updateById(dictionary);
        return ResponseHelper.buildResponseModel(dictionaryService.selectById(dictionary.getId()));
    }
}

