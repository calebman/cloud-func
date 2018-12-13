package com.cloudfunc.codec;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.protocol.CftpRequest;

/**
 * @author chenjianhui
 * @description 解码器
 * @date 2018/12/13
 */
public interface CftpDecoder {

    /**
     * 组装成为一个请求体
     *
     * @param request
     * @return 请求体
     */
    CftpRequest decode(String request) throws CftpException;
}
