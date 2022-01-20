package com.ixiamen.activity.aspect;

import com.ixiamen.activity.base.Constant;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * 基本被装饰类,做一些公共处理
 * Created by luoyongbin on 2018/10/12.
 */
public class AspectApiImpl implements AspectApi {

    @Override
    public Object doHandlerAspect(ProceedingJoinPoint pjp, Method method) {
        Constant.isPass = false;
        return null;
    }
}
