package com.cloudfunc.handle.impl;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.handle.HandlerAdapter;
import com.cloudfunc.method.CftpMethod;
import com.cloudfunc.protocol.CftpRequest;
import com.cloudfunc.protocol.CftpResponse;

import java.lang.reflect.Method;

/**
 * @author chenjianhui
 * @description 云函数调用实现
 * @date 2018/12/13
 */
public class CftpHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(CftpMethod handler) {
        return true;
    }

    @Override
    public CftpResponse handle(CftpRequest cftpRequest, CftpMethod handler) throws CftpException {
        try {
            Method method = handler.getMethod();
            Object result = method.invoke(handler.getObj(), cftpRequest.getParams());
            return CftpResponse.buildSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            return CftpResponse.buildError(e);
        }
    }
}
