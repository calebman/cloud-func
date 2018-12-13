package com.cloudfunc.handle;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.method.CftpMethod;
import com.cloudfunc.protocol.CftpRequest;
import com.cloudfunc.protocol.CftpResponse;

/**
 * @author chenjianhui
 * @description 负责调用云函数
 * @date 2018/12/13
 */
public interface HandlerAdapter {

    /**
     * 调用云函数
     *
     * @param cftpMethod  云函数对象
     * @param cftpRequest 请求体
     * @return 响应内容
     */
    CftpResponse handle(CftpMethod cftpMethod, CftpRequest cftpRequest) throws CftpException;
}
