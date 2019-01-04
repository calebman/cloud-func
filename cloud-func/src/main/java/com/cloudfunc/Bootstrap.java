package com.cloudfunc;

/**
 * @author chenjianhui
 * @description 快速启动类
 * @date 2019/1/4
 */
public class Bootstrap {
    public static void main(String[] args) {
        CftpServer cftpServer = new CftpServer(7000);
        cftpServer.start();
    }
}
