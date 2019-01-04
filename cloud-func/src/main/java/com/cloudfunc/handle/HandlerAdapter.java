package com.cloudfunc.handle;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.method.CftpMethod;
import com.cloudfunc.protocol.CftpRequest;
import com.cloudfunc.protocol.CftpResponse;

/**
 * @author chenjianhui
 * @description 负责调用云函数
 * @date 2018/12/13
 */
public interface HandlerAdapter {


    /**
     * 给定一个处理器实例，判断此实例是否被此适配器支持，每个适配器只支持一种类型的处理器
     *
     * @param handler 处理器实例
     * @return 此对象是否可以使用给定的处理程序
     */
    boolean supports(CftpMethod handler);

    /**
     * 使用给定的处理程序来处理此请求
     *
     * @param cftpRequest 请求体
     * @param handler     处理程序
     * @return 一个相应对象，包括结果码,调用结果等信息
     */
    CftpResponse handle(CftpRequest cftpRequest, CftpMethod handler) throws CftpException;
}
