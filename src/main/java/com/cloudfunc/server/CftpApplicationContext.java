package com.cloudfunc.server;

import com.cloudfunc.annotation.Register;
import com.cloudfunc.codec.CftpDecoder;
import com.cloudfunc.codec.CftpEncoder;
import com.cloudfunc.handle.HandlerAdapter;
import com.cloudfunc.handle.HandlerMapping;
import com.cloudfunc.method.CftpMethod;

import java.lang.reflect.Method;

/**
 * @author chenjianhui
 * @description 应用上下文对象
 * @date 2018/12/13
 */
public class CftpApplicationContext implements CftpContext {
    /**
     * 处理器映射器
     */
    private HandlerMapping handlerMapping;

    /**
     * 云函数适配器
     */
    private HandlerAdapter handlerAdapter;
    /**
     * 协议编码器
     */
    private CftpEncoder cftpEncoder;
    /**
     * 协议解码器
     */
    private CftpDecoder cftpDecoder;

    @Override
    public void registerObj(Object obj) {
        Class<?> clazz = obj.getClass();
        if (clazz.isAnnotationPresent(Register.class)) {
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(Register.class)) {
                    CftpMethod cftpMethod = new CftpMethod();
                    // wired group
                    cftpMethod.setObj(obj);
                    Register classRegister = clazz.getAnnotation(Register.class);
                    cftpMethod.setGroupName("".equalsIgnoreCase(classRegister.value()) ? clazz.getName() : classRegister.value());
                    cftpMethod.setGroupDesc(classRegister.desc());
                    // wired method
                    Register methodRegister = method.getAnnotation(Register.class);
                    cftpMethod.setMethodName("".equalsIgnoreCase(methodRegister.value()) ? method.getName() : methodRegister.value());
                    cftpMethod.setMethodDesc(methodRegister.desc());
                    cftpMethod.setMethod(method);
                    handlerMapping.register(cftpMethod);
                }

            }
        }
    }

    @Override
    public HandlerMapping getHandlerMapping() {
        return handlerMapping;
    }

    public void setHandlerMapping(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    public HandlerAdapter getHandlerAdapter() {
        return handlerAdapter;
    }

    public void setHandlerAdapter(HandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
    }

    @Override
    public CftpEncoder getCftpEncoder() {
        return cftpEncoder;
    }

    public void setCftpEncoder(CftpEncoder cftpEncoder) {
        this.cftpEncoder = cftpEncoder;
    }

    @Override
    public CftpDecoder getCftpDecoder() {
        return cftpDecoder;
    }

    public void setCftpDecoder(CftpDecoder cftpDecoder) {
        this.cftpDecoder = cftpDecoder;
    }
}
