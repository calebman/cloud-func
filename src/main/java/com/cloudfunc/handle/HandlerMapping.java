package com.cloudfunc.handle;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.method.CftpMethod;
import com.cloudfunc.protocol.CftpRequest;

/**
 * @author chenjianhui
 * @description 云函数匹配器
 * @date 2018/12/13
 */
public interface HandlerMapping {

    /**
     * 找到匹配的云函数
     *
     * @param request 请求体
     * @return 云函数
     */
    CftpMethod mapping(CftpRequest request) throws CftpException;
}
