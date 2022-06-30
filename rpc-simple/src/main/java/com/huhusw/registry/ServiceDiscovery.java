package com.huhusw.registry;

import com.huhusw.extension.SPI;
import com.huhusw.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * 服务发现
 */
@SPI
public interface ServiceDiscovery {
    /**
     * 通过服务名称进行服务发现
     *
     * @param rpcRequest rpc service pojo
     * @return service address
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
