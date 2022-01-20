package com.ixiamen.activity.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ixiamen.activity.annotation.Log;
import com.ixiamen.activity.entity.OperationLog;
import com.ixiamen.activity.service.IOperationLogService;
import com.ixiamen.activity.service.SpringContextBeanService;
import com.ixiamen.activity.util.ComUtil;
import com.ixiamen.activity.util.DateTimeUtil;
import com.ixiamen.activity.util.JWTUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * 记录日志切面
 *
 * @author luoyongbin
 * @since on 2018/5/10.
 */
public class RecordLogAspect extends AbstractAspectManager {

    public RecordLogAspect(AspectApi aspectApi) {
        super(aspectApi);
    }

    @Override
    public Object doHandlerAspect(ProceedingJoinPoint pjp, Method method) throws Throwable {
        super.doHandlerAspect(pjp, method);
        return execute(pjp, method);
    }

    private final Logger logger = LoggerFactory.getLogger(RecordLogAspect.class);

    @Override
    @Async
    protected Object execute(ProceedingJoinPoint pjp, Method method) throws Throwable {
        Log log = method.getAnnotation(Log.class);
        // 异常日志信息
        String actionLog = null;
        StackTraceElement[] stackTrace = null;
        // 是否执行异常
        boolean isException = false;
        // 接收时间戳
        long endTime;
        // 开始时间戳
        //long operationTime = System.currentTimeMillis();
        //开始时间
        String operationTime = DateTimeUtil.formatDateTimetoString(new Date());
        try {
            return pjp.proceed(pjp.getArgs());
        } catch (Throwable throwable) {
            isException = true;
            actionLog = throwable.getMessage();
            stackTrace = throwable.getStackTrace();
            throw throwable;
        } finally {
            // 日志处理
            logHandle(pjp, method, log, actionLog, operationTime, isException, stackTrace);
        }
    }

    private void logHandle(ProceedingJoinPoint joinPoint,
                           Method method,
                           Log log,
                           String actionLog,
                           String startTime,
                           boolean isException,
                           StackTraceElement[] stackTrace) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        IOperationLogService operationLogService = SpringContextBeanService.getBean(IOperationLogService.class);
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        String authorization = request.getHeader("Authorization");
        OperationLog operationLog = new OperationLog();
        if (!ComUtil.isEmpty(authorization)) {
            String userNo = JWTUtil.getUserNo(authorization);
            operationLog.setUserId(userNo);
        }
        operationLog.setIp(getIpAddress(request));
        operationLog.setClassName(joinPoint.getTarget().getClass().getName());
        operationLog.setCreateTime(startTime);
        operationLog.setLogDescription(log.description());
        operationLog.setModelName(log.modelName());
        operationLog.setAction(log.action());
        if (isException) {
            StringBuilder sb = new StringBuilder();
            sb.append(actionLog).append(" &#10; ");
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(stackTraceElement).append(" &#10; ");
            }
            operationLog.setMessage(sb.toString());
        }
        operationLog.setMethodName(method.getName());
        operationLog.setSucceed(isException ? 2 : 1);
        Object[] args = joinPoint.getArgs();
        StringBuilder sb = new StringBuilder();
        boolean isJoint = false;
        for (Object arg : args) {
            if (arg instanceof JSONObject) {
                JSONObject parse = (JSONObject) JSONObject.parse(arg.toString());
                if (!ComUtil.isEmpty(parse.getString("passWord"))) {
                    parse.put("passWord", "*******");
                }
                if (!ComUtil.isEmpty(parse.getString("password"))) {
                    parse.put("password", "*******");
                }
                if (!ComUtil.isEmpty(parse.getString("rePassword"))) {
                    parse.put("rePassword", "*******");
                }
                if (!ComUtil.isEmpty(parse.getString("oldPassword"))) {
                    parse.put("oldPassword", "*******");
                }
                operationLog.setActionArgs(parse.toString());
                if (log.action().equals("login") && ComUtil.isEmpty(parse.getString("userName"))) {
                    operationLog.setUserId(parse.getString("userName"));
                }
            } else if (arg instanceof String
                    || arg instanceof Long
                    || arg instanceof Integer
                    || arg instanceof Double
                    || arg instanceof Float
                    || arg instanceof Byte
                    || arg instanceof Short
                    || arg instanceof Character) {
                isJoint = true;
            } else if (arg instanceof String[]
                    || arg instanceof Long[]
                    || arg instanceof Integer[]
                    || arg instanceof Double[]
                    || arg instanceof Float[]
                    || arg instanceof Byte[]
                    || arg instanceof Short[]
                    || arg instanceof Character[]) {
                Object[] strs = (Object[]) arg;
                StringBuilder sbArray = new StringBuilder();
                sbArray.append("[");
                for (Object str : strs) {
                    sbArray.append(str.toString()).append(",");
                }
                sbArray.deleteCharAt(sbArray.length() - 1);
                sbArray.append("]");
                operationLog.setActionArgs(sbArray.toString());
            }
        }
        if (isJoint) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (String key : parameterMap.keySet()) {
                if (key.toUpperCase().contains("PASSWORD")) {
                    sb.append(key).append("=*******&");

                } else {
                    String[] strings = parameterMap.get(key);
                    for (String str : strings) {
                        sb.append(key).append("=").append(str).append("&");
                    }
                }
            }
            if (sb.length() > 0) {
                String actionArgs = sb.deleteCharAt(sb.length() - 1).toString();
                operationLog.setActionArgs(actionArgs);
            }

        }
        logger.info("执行方法信息:" + JSONObject.toJSON(operationLog));
        operationLogService.insert(operationLog);
    }


    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip + ":" + request.getRemotePort();
    }
}
