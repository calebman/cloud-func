package com.cloudfunc.protocol;

/**
 * @author chenjianhui
 * @description Cftp协议响应体
 * @date 2018/12/13
 */
public class CftpResponse {
    /**
     * 结果码
     */
    private CftpStatus cftpStatus;
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
        response.setCftpStatus(CftpStatus.OK);
        response.setData(data);
        return response;
    }

    public static CftpResponse buildError(CftpStatus cftpStatus) {
        CftpResponse response = new CftpResponse();
        response.setCftpStatus(cftpStatus);
        return response;
    }

    public static CftpResponse buildError(Exception ex) {
        return buildError(ex.getMessage());
    }

    /**
     * 构建一个异常的响应
     *
     * @param errorMsg 异常信息
     * @return 响应体
     */
    public static CftpResponse buildError(String errorMsg) {
        CftpResponse response = new CftpResponse();
        CftpStatus cftpStatus = CftpStatus.SERVER_ERROR;
        cftpStatus.setStatusMsg(errorMsg);
        response.setCftpStatus(cftpStatus);
        return response;
    }

    public CftpStatus getCftpStatus() {
        return cftpStatus;
    }

    public void setCftpStatus(CftpStatus cftpStatus) {
        this.cftpStatus = cftpStatus;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CftpResponse{" +
                "cftpStatus=" + cftpStatus +
                ", data=" + data +
                '}';
    }
}
