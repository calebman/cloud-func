package com.cloudfunc.handle.impl;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.factory.ICftpMethodFactory;
import com.cloudfunc.handle.HandlerMapping;
import com.cloudfunc.method.CftpMethod;
import com.cloudfunc.protocol.CftpRequest;
import com.cloudfunc.protocol.CftpStatus;

/**
 * @author chenjianhui
 * @description 处理器映射器
 * @date 2018/12/13
 */
public class CftpHandlerMapping implements HandlerMapping {

    private ICftpMethodFactory cftpMethodFactory;

    public CftpHandlerMapping(ICftpMethodFactory cftpMethodFactory) {
        this.cftpMethodFactory = cftpMethodFactory;
    }

    @Override
    public CftpMethod mapping(CftpRequest request) throws CftpException {
        CftpMethod cftpMethod = cftpMethodFactory.getCftpMethod(request.getGroupName(), request.getMethodName());
        if (cftpMethod == null) {
            throw new CftpException(CftpStatus.CLIENT_ERROR, "Function Not Found");
        }
        return cftpMethod;
    }

}
