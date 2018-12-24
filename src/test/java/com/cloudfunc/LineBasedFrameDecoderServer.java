package com.cloudfunc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/24
 */
public class LineBasedFrameDecoderServer {
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 使用LineBasedFrameDecoder解决粘包问题，其会根据"\n"或"\r\n"对二进制数据进行拆分，封装到不同的ByteBuf实例中
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024, true, true));
                            // 自定义这个ChannelInboundHandler打印拆包后的结果
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    if (msg instanceof ByteBuf) {
                                        ByteBuf packet = (ByteBuf) msg;
                                        System.out.println(
                                                new Date().toLocaleString() + ":" + packet.toString(Charset.defaultCharset()));
                                    }
                                }
                            });
                        }
                    });
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(8080).sync(); // (7)
            System.out.println("LineBasedFrameDecoderServer Started on 8080...");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
