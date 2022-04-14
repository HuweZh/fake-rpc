package com.huhusw.remoting.transport.socket;

import com.huhusw.registry.ServiceDiscovery;
import com.huhusw.remoting.dto.RpcRequest;
import com.huhusw.remoting.transport.RpcRequestTransport;

public class SocketRpcClient implements RpcRequestTransport {
    private final ServiceDiscovery serviceDiscovery;

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        return null;
    }
}
