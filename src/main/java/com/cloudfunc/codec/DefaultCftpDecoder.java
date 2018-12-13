package com.cloudfunc.codec;

import com.cloudfunc.exception.CftpException;
import com.cloudfunc.protocol.CftpRequest;
import com.cloudfunc.protocol.CftpStatus;

import java.util.Arrays;

/**
 * @author chenjianhui
 * @description
 * @date 2018/12/13
 */
public class DefaultCftpDecoder implements CftpDecoder {
    @Override
    public CftpRequest decode(String request) throws CftpException {
        String[] arr = request.split(" ");
        if (arr.length >= 2) {
            String groupName = arr[0];
            String methodName = arr[1];
            String[] params = Arrays.copyOfRange(arr, 2, arr.length);
            CftpRequest cftpRequest = new CftpRequest();
            cftpRequest.setGroupName(groupName);
            cftpRequest.setMethodName(methodName);
            cftpRequest.setParams(params);
            return cftpRequest;
        }
        throw new CftpException(CftpStatus.CLIENT_ERROR, "command invalid");
    }
}
