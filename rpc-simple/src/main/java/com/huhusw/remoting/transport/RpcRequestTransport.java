package com.huhusw.remoting.transport;

import com.huhusw.extension.SPI;
import com.huhusw.remoting.dto.RpcRequest;

@SPI
public interface RpcRequestTransport {
    /**
     * send rpc request to server and get result
     *
     * @param rpcRequest message body
     * @return data from server
     */

    Object sendRpcRequest(RpcRequest rpcRequest);
}
