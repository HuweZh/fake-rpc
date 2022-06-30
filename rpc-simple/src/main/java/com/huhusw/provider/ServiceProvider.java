package com.huhusw.provider;

import com.huhusw.config.RpcServiceConfig;

/**
 * 保存和提供服务对象
 */
public interface ServiceProvider {
    /**
     * @param rpcServiceConfig rpc服务相关属性
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * @param rpcServiceName rpc服务名称
     * @return rpc服务
     */
    Object getService(String rpcServiceName);

    /**
     * @param rpcServiceConfig rpc服务的相关属性
     */
    void publishService(RpcServiceConfig rpcServiceConfig);
}
