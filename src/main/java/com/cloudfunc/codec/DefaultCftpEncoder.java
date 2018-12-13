package com.cloudfunc.codec;

import com.cloudfunc.protocol.CftpResponse;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/13
 */
public class DefaultCftpEncoder implements CftpEncoder {
    @Override
    public String encode(CftpResponse cftpResponse) {
        String res = String.format("%s %s\n%s\n", cftpResponse.getCftpStatus().getStatusCode(), cftpResponse.getCftpStatus().getStatusMsg(), cftpResponse.getData());
        return res;
    }
}
