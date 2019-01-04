package com.cloudfunc.exception;

import com.cloudfunc.protocol.CftpStatus;

/**
 * @author chenjianhui
 * @description CFTP服务异常
 * @date 2018/12/13
 */
public class CftpException extends Exception {
    /**
     * 错误状态
     */
    private CftpStatus cftpStatus;

    /**
     * 使用错误状态初始化异常信息
     *
     * @param cftpStatus 错误状态
     */
    public CftpException(CftpStatus cftpStatus) {
        this.cftpStatus = cftpStatus;
    }

    public CftpException(CftpStatus cftpStatus, String errMsg) {
        super(errMsg);
        this.cftpStatus = cftpStatus;
    }

    public CftpStatus getCftpStatus() {
        return cftpStatus;
    }
}
