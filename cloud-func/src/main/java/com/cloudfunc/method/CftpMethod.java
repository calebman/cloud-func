package com.cloudfunc.method;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author chenjianhui
 * @description 云函数信息类
 * @date 2018/12/13
 */
public class CftpMethod {
    /**
     * 类名
     */
    private String groupName;
    /**
     * 类描述
     */
    private String groupDesc;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 方法描述
     */
    private String methodDesc;
    /**
     * 实例对象
     */
    private Object obj;
    /**
     * 方法对象
     */
    private Method method;
    /**
     * 执行次数
     */
    private int executeCount;
    /**
     * 最近一次的执行时间
     */
    private Date lastExecuteTime;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public int getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }

    public Date getLastExecuteTime() {
        return lastExecuteTime;
    }

    public void setLastExecuteTime(Date lastExecuteTime) {
        this.lastExecuteTime = lastExecuteTime;
    }

    @Override
    public String toString() {
        return "CftpMethod{" +
                "groupName='" + groupName + '\'' +
                ", groupDesc='" + groupDesc + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodDesc='" + methodDesc + '\'' +
                ", obj=" + obj +
                ", method=" + method +
                ", executeCount=" + executeCount +
                ", lastExecuteTime=" + lastExecuteTime +
                '}';
    }
}
