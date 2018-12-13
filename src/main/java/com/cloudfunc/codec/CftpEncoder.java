package com.cloudfunc.codec;

import com.cloudfunc.protocol.CftpResponse;

/**
 * @author chenjianhui
 * @description 编码器
 * @date 2018/12/13
 */
public interface CftpEncoder {
    /**
     * 编码返回给客户端
     *
     * @param cftpResponse 响应体
     * @return 编码后的响应信息
     */
    String encode(CftpResponse cftpResponse);
}
