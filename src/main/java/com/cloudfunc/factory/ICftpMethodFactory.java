package com.cloudfunc.factory;

import com.cloudfunc.method.CftpMethod;

import java.util.List;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/24
 */
public interface ICftpMethodFactory {

    /**
     * 注册函数
     *
     * @param obj 任意对象
     */
    void registerMehod(Object obj);

    /**
     * 移除函数
     *
     * @param groupName  组名
     * @param methodName 函数名
     */
    void remoteMethod(String groupName, String methodName);

    /**
     * 获取函数列表
     *
     * @return 函数列表
     */
    List<CftpMethod> getCftpMethodList();

    /**
     * 获取函数信息
     *
     * @param groupName  组名
     * @param methodName 函数名
     * @return 函数信息
     */
    CftpMethod getCftpMethod(String groupName, String methodName);
}
