package com.ixiamen.activity.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ixiamen.activity.annotation.ValidationParam;
import com.ixiamen.activity.exception.ParamJsonException;
import com.ixiamen.activity.util.ComUtil;
import com.ixiamen.activity.util.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 验证参数切面
 *
 * @author luoyongbin
 * @since on 2018/5/10.
 */
public class ValidationParamAspect extends AbstractAspectManager {

    public ValidationParamAspect(AspectApi aspectApi) {
        super(aspectApi);
    }

    @Override
    public Object doHandlerAspect(ProceedingJoinPoint pjp, Method method) throws Throwable {
        super.doHandlerAspect(pjp, method);
        execute(pjp, method);
        return null;
    }


    protected Object execute(ProceedingJoinPoint pjp, Method method) throws Throwable {
        //获取注解的value值返回
        String validationParamValue = StringUtil.getMethodAnnotationOne(method, ValidationParam.class.getSimpleName());
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        String requestURI = request.getRequestURI();
        //获取类名上的url
        String url = getMethodUrl(method, request.getContextPath());
        Object[] obj = pjp.getArgs();
        if (requestURI.equals(url)) {
            if (!ComUtil.isEmpty(validationParamValue)) {
                for (Object o : obj) {
                    if (o instanceof JSONObject) {
                        JSONObject jsonObject = JSONObject.parseObject(o.toString());
                        //是否有所有必须参数
                        hasAllRequired(jsonObject, validationParamValue);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取方法上的url地址
     */
    private String getMethodUrl(Method method, String contextPath) {
        Class<?> declaringClass = method.getDeclaringClass();
        Annotation[] annotations = declaringClass.getAnnotations();
        StringBuilder url = new StringBuilder();
        url.append(contextPath);
        for (Annotation annotation : annotations) {
            if (annotation instanceof RequestMapping) {
                String[] value = ((RequestMapping) annotation).value();
                for (String tempUrl : value) {
                    url.append(tempUrl);
                }
            }
        }
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            String tempAnnotations = annotation.toString();
            if (tempAnnotations.indexOf("Mapping") > 0) {
                url.append(tempAnnotations, tempAnnotations.indexOf("value=[") + 7, tempAnnotations.lastIndexOf("],"));
            }
        }
        return url.toString().replaceAll("/+", "/");
    }


    /**
     * 验证前端传入参数,没有抛出异常
     */
    private void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
        if (!ComUtil.isEmpty(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            StringBuilder missCol = new StringBuilder();
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (ComUtil.isEmpty(val)) {
                    missCol.append(column).append("  ");
                }
            }
            if (!ComUtil.isEmpty(missCol.toString())) {
                jsonObject.clear();
                throw new ParamJsonException("缺少必填参数:" + missCol.toString().trim());
            }
        }
    }
}
