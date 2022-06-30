package com.huhusw.remoting.transport;

import com.huhusw.extension.SPI;
import com.huhusw.remoting.dto.RpcRequest;

@SPI
public interface RpcRequestTransport {
    /**
     * 发送rpc请求到远程服务并获取结果
     *
     * @param rpcRequest message body
     * @return data from server
     */

    Object sendRpcRequest(RpcRequest rpcRequest);
}
