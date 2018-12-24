package com.cloudfunc;

import com.cloudfunc.annotation.Register;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/13
 */

@Register("fun")
public class RemoteFunc {

    @Register
    public String add(String a, String b) {
        return a + b;
    }

    @Register
    public String save() {
        return "shabi??";
    }
}
