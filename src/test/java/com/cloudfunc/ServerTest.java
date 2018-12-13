package com.cloudfunc;

import com.cloudfunc.server.CftpServer;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/13
 */
public class ServerTest {

    public static void main(String[] args) {
        RemoteFunc remoteFunc = new RemoteFunc();
        CftpServer cftpServer = new CftpServer();
        cftpServer.getCftpContext().registerObj(remoteFunc);
        cftpServer.start();
    }
}
