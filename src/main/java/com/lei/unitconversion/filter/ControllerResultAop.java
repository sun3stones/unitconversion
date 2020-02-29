package com.lei.unitconversion.filter;

import com.alibaba.fastjson.JSONObject;
import com.lei.unitconversion.common.ResultCode;
import com.lei.unitconversion.common.UnitResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 控制层方法aop
 * 记录日志，统计处理时间，处理返回结果
 */
@Component
@Aspect
public class ControllerResultAop {

    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerResultAop.class);

    @Around("execution(* com.lei.unitconversion.controller.*.*(..))")
    public JSONObject aroundMethod(ProceedingJoinPoint pjd) {
        long time = System.currentTimeMillis();
        JSONObject jsonResult = null;
        String methodName = pjd.getSignature().getName();
        try {
            //前置通知
            LOGGER.info("方法调用开始： {} 参数：{} ",methodName, Arrays.asList(pjd.getArgs()));
            //执行目标方法
            Object result = pjd.proceed();
            jsonResult = (JSONObject) result;
            //返回通知
            LOGGER.info("方法调用完成： {} 结果：{} ",methodName, jsonResult.toJSONString());
        } catch (Throwable e) {
            //异常通知
            LOGGER.info("方法调用异常： {} 结果：{} ",methodName, e.getMessage());
            UnitResult unitResult = new UnitResult();
            if (e.getCause().toString().contains("jdbc")){//数据库异常
                unitResult.setCode(ResultCode.DataSource.getCode());
                unitResult.setMsg(e.getCause().toString());
            }else{
                unitResult.setCode(ResultCode.Program.getCode());
                unitResult.setMsg(ResultCode.Program.getMsg());
            }
            jsonResult = unitResult.toJson();
        }finally {
            //结束通知
            time = System.currentTimeMillis() - time;
            LOGGER.info("方法调用结束： {} 耗时：{}ms ",methodName, time);
            return jsonResult;
        }
    }
}