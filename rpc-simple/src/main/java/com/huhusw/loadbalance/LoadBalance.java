package com.huhusw.loadbalance;

import com.huhusw.extension.SPI;
import com.huhusw.remoting.dto.RpcRequest;

import java.util.List;

/**
 * Interface to the load balancing policy
 * 负载均衡策略的接口
 */
@SPI
public interface LoadBalance {
    /**
     * Choose one from the list of existing service addresses list
     *
     * @param serviceUrlList Service address list
     * @param rpcRequest
     * @return target service address
     */
    String selectServiceAddress(List<String> serviceUrlList, RpcRequest rpcRequest);
}
