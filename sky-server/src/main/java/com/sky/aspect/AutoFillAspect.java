package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    private final Map<Class<?>, AutoFillMetadata> metadataCache = new ConcurrentHashMap<>();
    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {

    }

    @Before("autoFillPointCut()")
    public void autoFillBefore(JoinPoint joinPoint) {
        //获得执行方法类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);//获得注解对象
        OperationType value = annotation.value(); //获得操作类型

        //获得修改参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object arg = args[0];
        AutoFillMetadata metadata = getMetadata(arg.getClass());
        //准备赋值数据
        Long currentId = BaseContext.getCurrentId();
        LocalDateTime now = LocalDateTime.now();
        //反射赋值
        if (value == OperationType.INSERT) {
            try {
                metadata.getSetCreateTime().invoke(arg, now);
                metadata.getSetUpdateTime().invoke(arg, now);
                metadata.getSetCreateUser().invoke(arg, currentId);
                metadata.getSetUpdateUser().invoke(arg, currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (value == OperationType.UPDATE) {

            try {
                metadata.getSetUpdateTime().invoke(arg, now);
                metadata.getSetUpdateUser().invoke(arg, currentId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private AutoFillMetadata getMetadata(Class<?> targetClass) {
        return metadataCache.computeIfAbsent(targetClass, this::buildMetadata);
    }

    private AutoFillMetadata buildMetadata(Class<?> targetClass) {
        try {
            Method setCreateTime = resolveMethod(targetClass, AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setUpdateTime = resolveMethod(targetClass, AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setCreateUser = resolveMethod(targetClass, AutoFillConstant.SET_CREATE_USER, Long.class);
            Method setUpdateUser = resolveMethod(targetClass, AutoFillConstant.SET_UPDATE_USER, Long.class);
            return new AutoFillMetadata(setCreateTime, setUpdateTime, setCreateUser, setUpdateUser);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Method resolveMethod(Class<?> targetClass, String methodName, Class<?> parameterType) throws NoSuchMethodException {
        Method method = targetClass.getDeclaredMethod(methodName, parameterType);
        method.setAccessible(true);
        return method;
    }

    private static class AutoFillMetadata {
        private final Method setCreateTime;
        private final Method setUpdateTime;
        private final Method setCreateUser;
        private final Method setUpdateUser;

        AutoFillMetadata(Method setCreateTime, Method setUpdateTime, Method setCreateUser, Method setUpdateUser) {
            this.setCreateTime = setCreateTime;
            this.setUpdateTime = setUpdateTime;
            this.setCreateUser = setCreateUser;
            this.setUpdateUser = setUpdateUser;
        }

        Method getSetCreateTime() {
            return setCreateTime;
        }

        Method getSetUpdateTime() {
            return setUpdateTime;
        }

        Method getSetCreateUser() {
            return setCreateUser;
        }

        Method getSetUpdateUser() {
            return setUpdateUser;
        }
    }
}
