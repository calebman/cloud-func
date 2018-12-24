package com.cloudfunc;

import com.cloudfunc.client.CftpClient;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/24
 */
public class ClientTest {

    public static void main(String[] args) {
        CftpClient cftpClient = new CftpClient("localhost", 7000);
        cftpClient.run();
    }
}
