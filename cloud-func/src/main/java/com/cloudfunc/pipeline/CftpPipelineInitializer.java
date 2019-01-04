package com.cloudfunc.pipeline;

import com.cloudfunc.CftpHandler;
import com.cloudfunc.codec.CftpDecoder;
import com.cloudfunc.codec.CftpEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * @author chenjianhui
 * @description 管道初始化
 * @date 2018/12/14
 */
public class CftpPipelineInitializer extends ChannelInitializer<Channel> {

    private CftpHandler cftpHandler;

    public CftpPipelineInitializer(CftpHandler cftpHandler) {
        this.cftpHandler = cftpHandler;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new CftpEncoder());
        pipeline.addLast(new CftpDecoder());
        pipeline.addLast(this.cftpHandler);
    }
}
