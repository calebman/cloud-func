package com.cloudfunc.codec;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.protocol.CftpRequest;
import com.cloudfunc.protocol.CftpStatus;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/14
 */
public class CftpDecoder extends ByteToMessageDecoder {

    private final int maxLength;
    /**
     * 超长抛出异常
     */
    private final boolean failFast;
    private final boolean stripDelimiter;

    /**
     * 超长丢弃输入
     */
    private boolean discarding;
    private int discardedBytes;

    public CftpDecoder() {
        this.maxLength = 1024;
        this.failFast = true;
        this.stripDelimiter = true;
    }

    @Override
    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String request = decode(ctx, in);
        if (request == null) {
            return;
        }
        String[] arr = request.split(" ");
        System.out.println("request : " + request);
        if (arr.length >= 2) {
            String groupName = arr[0];
            String methodName = arr[1];
            String[] params = Arrays.copyOfRange(arr, 2, arr.length);
            CftpRequest cftpRequest = new CftpRequest();
            cftpRequest.setGroupName(groupName);
            cftpRequest.setMethodName(methodName);
            cftpRequest.setParams(params);
            out.add(cftpRequest);
        } else {
            ctx.fireExceptionCaught(new CftpException(CftpStatus.CLIENT_ERROR, "command invalid"));
        }
    }


    protected String decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        final int eol = findEndOfLine(buffer);
        if (!discarding) {
            if (eol >= 0) {
                final ByteBuf frame;
                final int length = eol - buffer.readerIndex();
                final int delimLength = buffer.getByte(eol) == '\r' ? 2 : 1;

                if (length > maxLength) {
                    buffer.readerIndex(eol + delimLength);
                    fail(ctx, length);
                    return null;
                }

                if (stripDelimiter) {
                    frame = buffer.readRetainedSlice(length);
                    buffer.skipBytes(delimLength);
                } else {
                    frame = buffer.readRetainedSlice(length + delimLength);
                }

                return frame.toString(Charset.defaultCharset());
            } else {
                final int length = buffer.readableBytes();
                if (length > maxLength) {
                    discardedBytes = length;
                    buffer.readerIndex(buffer.writerIndex());
                    discarding = true;
                    if (failFast) {
                        fail(ctx, "over " + discardedBytes);
                    }
                }
                return null;
            }
        } else {
            if (eol >= 0) {
                final int length = discardedBytes + eol - buffer.readerIndex();
                final int delimLength = buffer.getByte(eol) == '\r' ? 2 : 1;
                buffer.readerIndex(eol + delimLength);
                discardedBytes = 0;
                discarding = false;
                if (!failFast) {
                    fail(ctx, length);
                }
            } else {
                discardedBytes += buffer.readableBytes();
                buffer.readerIndex(buffer.writerIndex());
            }
            return null;
        }
    }

    private void fail(final ChannelHandlerContext ctx, int length) {
        fail(ctx, String.valueOf(length));
    }

    private void fail(final ChannelHandlerContext ctx, String length) {
        ctx.fireExceptionCaught(new CftpException(CftpStatus.CLIENT_ERROR, "frame length (" + length + ") exceeds the allowed maximum (" + maxLength + ')'));
    }

    private static int findEndOfLine(final ByteBuf buffer) {
        int i = buffer.forEachByte(ByteProcessor.FIND_LF);
        if (i > 0 && buffer.getByte(i - 1) == '\r') {
            i--;
        }
        return i;
    }
}
