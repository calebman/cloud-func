package com.cloudfunc.server;

import com.cloudfunc.codec.CftpDecoder;
import com.cloudfunc.codec.CftpEncoder;
import com.cloudfunc.handle.HandlerAdapter;
import com.cloudfunc.handle.HandlerMapping;

/**
 * @author chenjianhui
 * @description CFTP服务上下文
 * @date 2018/12/13
 */
public interface CftpContext {

    /**
     * 获取处理器映射器
     *
     * @return 处理器映射器
     */
    HandlerMapping getHandlerMapping();

    /**
     * 获取云函数适配器（调用）
     *
     * @return 云函数适配器
     */
    HandlerAdapter getHandlerAdapter();

    /**
     * 获取编码器
     *
     * @return CFTP协议编码器
     */
    CftpEncoder getCftpEncoder();

    /**
     * 获取解码器
     *
     * @return CFTP协议解码器
     */
    CftpDecoder getCftpDecoder();

    /**
     * 注册云函数
     *
     * @param obj 云函数类对象
     */
    void registerObj(Object obj);
}
