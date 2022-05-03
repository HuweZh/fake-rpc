package com.huhusw.loadbalance.loadbalancer;

import com.huhusw.loadbalance.AbstractLoadBalance;
import com.huhusw.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * 随机的负载均衡策略
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        //从列表中随机选择一个策略
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
