package com.huhusw.registry;

import com.huhusw.extension.SPI;

import java.net.InetSocketAddress;

/**
 * service registration
 * 服务注册
 */
@SPI
public interface ServiceRegistry {
    /**
     * 注册服务
     *
     * @param rpcServiceName    rpc service name 服务名称
     * @param inetSocketAddress service address 服务地址
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);

}