package com.cloudfunc.protocol;

/**
 * @author chenjianhui
 * @description CFTP协议结果码含义
 * @date 2018/12/13
 */
public enum CftpStatus {

    OK(200, "OK"),
    SERVER_ERROR(500, "Server Exception"),
    CLIENT_ERROR(400, "Client Exception"),
    REDIRECT(300, "Redirect"),
    PENDING(100, "Pending");

    /**
     * 类型码
     */
    private Integer statusCode;
    /**
     * 类型详情
     */
    private String statusMsg;

    CftpStatus(Integer statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    @Override
    public String toString() {
        return "CftpStatus{" +
                "statusCode=" + statusCode +
                ", statusMsg='" + statusMsg + '\'' +
                '}';
    }
}