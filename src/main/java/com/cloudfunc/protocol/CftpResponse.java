package com.cloudfunc.protocol;

import com.cloudfunc.exception.CftpException;

/**
 * @author chenjianhui
 * @description Cftp协议响应体
 * @date 2018/12/13
 */
public class CftpResponse {
    /**
     * 结果码
     */
    private Integer statusCode;
    /**
     * 结果信息
     */
    private String statusMsg;
    /**
     * 响应数据
     */
    private Object data;

    /**
     * 构建一个正确的响应
     *
     * @param data 数据信息
     * @return 响应体
     */
    public static CftpResponse buildSuccess(Object data) {
        CftpResponse response = new CftpResponse();
        response.setStatusCode(CftpStatus.OK.getStatusCode());
        response.setStatusMsg(CftpStatus.OK.getStatusMsg());
        response.setData(data);
        return response;
    }

    public static CftpResponse buildError(CftpStatus cftpStatus) {
        CftpResponse response = new CftpResponse();
        response.setStatusCode(cftpStatus.getStatusCode());
        response.setStatusMsg(cftpStatus.getStatusMsg());
        return response;
    }

    public static CftpResponse buildError(CftpException ex) {
        return buildError(ex.getCftpStatus().getStatusCode(), ex.getMessage());
    }

    /**
     * 构建一个异常的响应
     *
     * @param errorMsg 异常信息
     * @return 响应体
     */
    public static CftpResponse buildError(Integer statusCode, String errorMsg) {
        CftpResponse response = new CftpResponse();
        response.setStatusMsg(errorMsg);
        response.setStatusCode(statusCode);
        return response;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
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
        return "CftpResponse{" +
                "statusCode=" + statusCode +
                ", statusMsg='" + statusMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public String toResponse() {
        if (getStatusCode() == CftpStatus.OK.getStatusCode()) {
            return String.format("%s %s\n\n%s\n", CftpStatus.OK.getStatusCode(), CftpStatus.OK.getStatusMsg(), getData());
        } else {
            return String.format("%s %s\n\n", getStatusCode(), getStatusMsg());
        }
    }
}
