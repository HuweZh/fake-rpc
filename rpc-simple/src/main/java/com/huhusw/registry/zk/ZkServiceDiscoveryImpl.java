package com.huhusw.registry.zk;

import com.huhusw.enums.RpcErrorMessageEnum;
import com.huhusw.exception.RpcException;
import com.huhusw.extension.ExtensionLoader;
import com.huhusw.loadbalance.LoadBalance;
import com.huhusw.registry.ServiceDiscovery;
import com.huhusw.registry.zk.util.CuratorUtils;
import com.huhusw.remoting.dto.RpcRequest;
import com.huhusw.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 基于zookeeper的服务发现
 */
@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {
    //负载均衡策略
    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        //获取负载均衡策略
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
    }

    /**
     * 服务发现
     *
     * @param rpcRequest rpc service pojo
     * @return
     */
    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        //获取服务名称
        String rpcServiceName = rpcRequest.getRpcServiceName();
        //获取zk客户端
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        //根据名称获取服务
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        // load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        //获取地址和端口
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
