package com.cloudfunc.factory.impl;

import com.cloudfunc.annotation.Register;
import com.cloudfunc.factory.ICftpMethodFactory;
import com.cloudfunc.method.CftpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenjianhui
 * @description 函数工厂
 * @date 2018/12/24
 */
public class CftpMethodFactory implements ICftpMethodFactory {

    private Logger logger = LoggerFactory.getLogger(CftpMethodFactory.class);

    /**
     * 存储函数对象信息
     */
    private Map<String, CftpMethod> cftpMethodMap;

    /**
     * 内置函数支持
     */
    private List<Object> internalFuncSupport;

    public CftpMethodFactory() {
        this.cftpMethodMap = new ConcurrentHashMap<>();
        this.internalFuncSupport = new ArrayList<>();
    }

    public CftpMethodFactory(List<Object> internalFuncSupport) {
        this.cftpMethodMap = new ConcurrentHashMap<>();
        this.internalFuncSupport = internalFuncSupport;
        this.buildInternalFunc();
    }

    @Override
    public void registerMehod(Object obj) {
        Class<?> clazz = obj.getClass();
        if (clazz.isAnnotationPresent(Register.class)) {
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(Register.class)) {
                    CftpMethod cftpMethod = new CftpMethod();
                    // wired group
                    cftpMethod.setObj(obj);
                    Register classRegister = clazz.getAnnotation(Register.class);
                    cftpMethod.setGroupName("".equalsIgnoreCase(classRegister.value()) ? clazz.getName() : classRegister.value());
                    cftpMethod.setGroupDesc(classRegister.desc());
                    // wired method
                    Register methodRegister = method.getAnnotation(Register.class);
                    cftpMethod.setMethodName("".equalsIgnoreCase(methodRegister.value()) ? method.getName() : methodRegister.value());
                    cftpMethod.setMethodDesc(methodRegister.desc());
                    cftpMethod.setMethod(method);
                    String key = this.generatorMethodKey(cftpMethod.getGroupName(), cftpMethod.getMethodName());
                    logger.info("register method {}", cftpMethod);
                    this.cftpMethodMap.put(key, cftpMethod);
                }
            }
        }
    }

    @Override
    public void remoteMethod(String groupName, String methodName) {
        String key = this.generatorMethodKey(groupName, methodName);
        this.cftpMethodMap.remove(key);
    }

    @Override
    public List<CftpMethod> getCftpMethodList() {
        return new ArrayList<>(this.cftpMethodMap.values());
    }

    @Override
    public CftpMethod getCftpMethod(String groupName, String methodName) {
        String key = this.generatorMethodKey(groupName, methodName);
        return this.cftpMethodMap.get(key);
    }

    /**
     * 构造云函数的唯一键值
     *
     * @param groupName  组名称
     * @param methodName 方法名称
     * @return 键值
     */
    private String generatorMethodKey(String groupName, String methodName) {
        return groupName + "-" + methodName;
    }

    /**
     * 添加内置的函数支持
     */
    private void buildInternalFunc() {
        for (Object obj : internalFuncSupport) {
            registerMehod(obj);
        }
    }
}
