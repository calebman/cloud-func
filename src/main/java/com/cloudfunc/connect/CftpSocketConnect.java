package com.cloudfunc.connect;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.method.CftpMethod;
import com.cloudfunc.protocol.CftpRequest;
import com.cloudfunc.protocol.CftpResponse;
import com.cloudfunc.server.CftpContext;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author chenjianhui
 * @description socket连接器
 * @date 2018/12/13
 */
public class CftpSocketConnect implements Runnable {
    /**
     * socket通信对象
     */
    private Socket client;
    /**
     * 上下文对象
     */
    private CftpContext cftpContext;

    /**
     * 使用Cftp上下文对象构造连接器
     *
     * @param cftpContext
     */
    public CftpSocketConnect(Socket client, CftpContext cftpContext) {
        this.client = client;
        this.cftpContext = cftpContext;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 获取请求体
                String request = new Scanner(client.getInputStream()).nextLine();
                System.out.println("request:" + request);
                // 解码
                CftpRequest cftpRequest = null;
                CftpResponse cftpResponse = null;
                try {
                    cftpRequest = cftpContext.getCftpDecoder().decode(request);
                    // 映射云函数
                    CftpMethod cftpMethod = cftpContext.getHandlerMapping().mapping(cftpRequest);
                    // 适配调用
                    cftpResponse = cftpContext.getHandlerAdapter().handle(cftpMethod, cftpRequest);
                } catch (CftpException e) {
                    cftpResponse = CftpResponse.buildError(e.getCftpStatus());
                    e.printStackTrace();
                }
                // 编码
                String response = cftpContext.getCftpEncoder().encode(cftpResponse);
                // 响应
                client.getOutputStream().write(response.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
