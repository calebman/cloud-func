package com.cloudfunc.protocol;

import java.util.Arrays;

/**
 * @author chenjianhui
 * @description CFTP协议请求体
 * @date 2018/12/13
 */
public class CftpRequest {
    /**
     * 类名
     */
    private String groupName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数列表
     */
    private Object[] params;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "CftpRequest{" +
                "groupName='" + groupName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
