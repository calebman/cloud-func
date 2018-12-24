package com.cloudfunc;

import com.cloudfunc.pipeline.CftpPipelineInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetSocketAddress;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/13
 */
public class CftpServer {
    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(CftpServer.class);

    private final static int DEFAULT_SERVER_PORT = 7000;

    /**
     * 服务端监听的端口
     */
    private int serverPort;

    /**
     * 处理管道
     */
    private CftpPipelineInitializer cftpPipelineInitializer;


    public CftpServer() {
        this(DEFAULT_SERVER_PORT);
    }

    public CftpServer(int serverPort) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("cftp-context.xml");
        context.start();
        this.cftpPipelineInitializer = context.getBean("cftpPipelineInitializer", CftpPipelineInitializer.class);
        this.serverPort = serverPort;
    }


    /**
     * 启动服务器
     */
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(serverPort))
                    .childHandler(cftpPipelineInitializer);
            ChannelFuture f = b.bind().sync();
            logger.info("cftp sever started successful，listen in {}", serverPort);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                workerGroup.shutdownGracefully().sync();
                bossGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
