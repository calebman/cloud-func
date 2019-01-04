package com.cloudfunc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author chenjianhui
 * @description Cftp协议客户端
 * @date 2018/12/24
 */
public class CftpClient {

    private Logger logger = LoggerFactory.getLogger(CftpClient.class);

    private final String host;
    private final int port;

    public CftpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        // 设置一个多线程循环器
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 启动附注类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        // 指定所使用的NIO传输channel
        bootstrap.channel(NioSocketChannel.class);
        // 指定客户端初始化处理
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast("handler", new SimpleChannelInboundHandler<String>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                        logger.info("[response]{}", msg);
                    }
                });
            }
        });
        try {
            Channel channel = bootstrap.connect(host, port).sync().channel();
            while (true) {
                // 向服务端发送内容
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String content = reader.readLine();
                    if (!content.equals("")) {
                        if ("quit".equalsIgnoreCase(content)) {
                            System.exit(1);
                        }
                        logger.info("[request]{}", content);
                        content += "\n";
                        channel.writeAndFlush(content);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
