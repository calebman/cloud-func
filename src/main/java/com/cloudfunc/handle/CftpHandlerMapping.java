package com.cloudfunc.handle;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.method.CftpMethod;
import com.cloudfunc.protocol.CftpRequest;
import com.cloudfunc.protocol.CftpStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenjianhui
 * @description 处理器映射器
 * @date 2018/12/13
 */
public class CftpHandlerMapping implements HandlerMapping {
    /**
     * 存储云函数列表
     */
    private Map<String, CftpMethod> cftpMethodMap;

    /**
     * 构造云函数列表
     */
    public CftpHandlerMapping() {
        cftpMethodMap = new ConcurrentHashMap<>();
    }

    @Override
    public CftpMethod mapping(CftpRequest request) throws CftpException {
        String key = generatorMethodKey(request.getGroupName(), request.getMethodName());
        CftpMethod cftpMethod = cftpMethodMap.get(key);
        if (cftpMethod == null) {
            throw new CftpException(CftpStatus.CLIENT_ERROR, "Function Not Found");
        }
        return cftpMethod;
    }

    @Override
    public void register(CftpMethod cftpMethod) {
        String key = generatorMethodKey(cftpMethod.getGroupName(), cftpMethod.getMethodName());
        System.out.println(String.format("register %s : %s", key, cftpMethod));
        cftpMethodMap.put(key, cftpMethod);
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
}
