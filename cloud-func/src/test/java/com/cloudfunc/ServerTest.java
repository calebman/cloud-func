package com.cloudfunc;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/13
 */
public class ServerTest {

    public static void main(String[] args) {
        RemoteFunc remoteFunc = new RemoteFunc();
        CftpServer cftpServer = new CftpServer(7000);
        cftpServer.start();
    }
}
