package com.cloudfunc;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.handle.HandlerAdapter;
import com.cloudfunc.handle.HandlerMapping;
import com.cloudfunc.method.CftpMethod;
import com.cloudfunc.protocol.CftpRequest;
import com.cloudfunc.protocol.CftpResponse;
import com.cloudfunc.protocol.CftpStatus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author chenjianhui
 * @description 请求分发器
 * @date 2018/12/24
 */
public class CftpHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(CftpHandler.class);

    /**
     * 处理器适配器列表
     */
    private List<HandlerAdapter> handlerAdapterList;

    /**
     * 处理器匹配器列表
     */
    private List<HandlerMapping> handlerMappingList;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.info("client register {}", ctx.name());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CftpRequest cftpRequest = (CftpRequest) msg;
        logger.info("CftpHandler read msg from client :" + cftpRequest);
        CftpMethod cftpMethod = null;
        for (HandlerMapping handlerMapping : handlerMappingList) {
            cftpMethod = handlerMapping.mapping(cftpRequest);
            if (cftpMethod != null) {
                break;
            }
        }
        if (cftpMethod == null) {
            this.writeResponse(ctx, CftpResponse.buildError(CftpStatus.CLIENT_ERROR));
            return;
        }
        for (HandlerAdapter handlerAdapter : handlerAdapterList) {
            if (handlerAdapter.supports(cftpMethod)) {
                CftpResponse cftpResponse = handlerAdapter.handle(cftpRequest, cftpMethod);
                this.writeResponse(ctx, cftpResponse);
                return;
            }
        }
        this.writeResponse(ctx, CftpResponse.buildError(CftpStatus.SERVER_ERROR));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.toString());
        if (cause instanceof CftpException) {
            CftpException cftpException = (CftpException) cause;
            this.writeResponse(ctx, CftpResponse.buildError(cftpException));
        } else {
            this.writeResponse(ctx, CftpResponse.buildError(CftpStatus.SERVER_ERROR));
        }
    }

    public void setHandlerAdapterList(List<HandlerAdapter> handlerAdapterList) {
        this.handlerAdapterList = handlerAdapterList;
    }

    public void setHandlerMappingList(List<HandlerMapping> handlerMappingList) {
        this.handlerMappingList = handlerMappingList;
    }

    /**
     * 响应信息
     *
     * @param ctx          上下文
     * @param cftpResponse 响应内容
     */
    private void writeResponse(ChannelHandlerContext ctx, CftpResponse cftpResponse) {
        ctx.writeAndFlush(cftpResponse);
    }
}
