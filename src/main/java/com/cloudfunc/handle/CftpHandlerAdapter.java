package com.cloudfunc.handle;

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
    public CftpResponse handle(CftpMethod cftpMethod, CftpRequest cftpRequest) {
        try {
            Method method = cftpMethod.getMethod();
            Object result = method.invoke(cftpMethod.getObj(), cftpRequest.getParams());
            return CftpResponse.buildSuccess(result);
        } catch (Exception e) {
            e.printStackTrace();
            return CftpResponse.buildError(e);
        }
    }
}
