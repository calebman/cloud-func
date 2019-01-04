package com.cloudfunc.fun;

import com.cloudfunc.annotation.Register;

/**
 * @author chenjianhui
 * @description 内置函数支持
 * @date 2018/12/13
 */
@Register(value = "cftp", desc = "internal function support")
public class InternalFunc {

    @Register(value = "help", desc = "operation helps")
    public String help() {
        return String.format(
                "you can run this commands\n%s:%s",
                "cftp funcs", "it can tells you all functions"
        );
    }

    @Register(value = "funcs", desc = "it can tells you all functions")
    public String funcs() {
        return "empty";
    }
}
