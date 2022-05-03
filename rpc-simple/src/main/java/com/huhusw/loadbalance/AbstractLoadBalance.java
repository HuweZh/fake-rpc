package com.huhusw.loadbalance;

import com.huhusw.remoting.dto.RpcRequest;
import com.huhusw.utils.CollectionUtil;

import java.util.List;

/**
 * Abstract class for a load balancing policy
 * 负载均衡策略的抽象类
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest) {
        if (CollectionUtil.isEmpty(serviceAddresses)) {
            return null;
        }
        if (serviceAddresses.size() == 1) {
            return serviceAddresses.get(0);
        }
        return doSelect(serviceAddresses, rpcRequest);
    }

    /**
     * 选择一个均衡策略
     *
     * @param serviceAddresses
     * @param rpcRequest
     * @return
     */
    protected abstract String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest);

}
