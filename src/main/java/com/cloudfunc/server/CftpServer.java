package com.cloudfunc.server;

import com.cloudfunc.codec.DefaultCftpDecoder;
import com.cloudfunc.codec.DefaultCftpEncoder;
import com.cloudfunc.connect.CftpSocketConnect;
import com.cloudfunc.fun.InternalFunc;
import com.cloudfunc.handle.CftpHandlerAdapter;
import com.cloudfunc.handle.CftpHandlerMapping;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/13
 */
public class CftpServer {

    /**
     * 默认服务端口
     */
    private final static int DEFAULT_SERVER_PORT = 8080;

    /**
     * 服务端监听的端口
     */
    private int serverPort;

    /**
     * 上下文对象
     */
    private CftpContext cftpContext;

    public CftpServer() {
        this(DEFAULT_SERVER_PORT);
    }

    public CftpServer(int serverPort) {
        this.serverPort = serverPort;
        this.cftpContext = buildContext();
        this.buildInternalFunc();
    }


    /**
     * 启动服务器
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println(String.format("CftpServer start successful , listen in %s", serverSocket.getLocalPort()));
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println(String.format("Client %s connect successful", clientSocket.getLocalSocketAddress()));
                    CftpSocketConnect socketConnect = new CftpSocketConnect(clientSocket, cftpContext);
                    socketConnect.run();
//                    new Thread(socketConnect).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建上下文对象
     *
     * @return CFTP服务上下文
     */
    private CftpContext buildContext() {
        CftpApplicationContext cftpApplicationContext = new CftpApplicationContext();
        cftpApplicationContext.setHandlerMapping(new CftpHandlerMapping());
        cftpApplicationContext.setHandlerAdapter(new CftpHandlerAdapter());
        cftpApplicationContext.setCftpDecoder(new DefaultCftpDecoder());
        cftpApplicationContext.setCftpEncoder(new DefaultCftpEncoder());
        return cftpApplicationContext;
    }

    /**
     * 添加内置的函数支持
     */
    private void buildInternalFunc() {
        cftpContext.registerObj(new InternalFunc());
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public CftpContext getCftpContext() {
        return cftpContext;
    }
}
