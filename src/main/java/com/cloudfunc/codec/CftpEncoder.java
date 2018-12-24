package com.cloudfunc.codec;

import com.cloudfunc.protocol.CftpResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/14
 */
public class CftpEncoder extends MessageToByteEncoder<CftpResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, CftpResponse msg, ByteBuf out) throws Exception {
        out.writeBytes((msg.toString() + "\n").getBytes());
    }
}
