package com.huhusw.loadbalance.loadbalancer;


import com.huhusw.loadbalance.AbstractLoadBalance;
import com.huhusw.remoting.dto.RpcRequest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一致性hash负载均衡策略
 * 参考dubbo
 * refer to dubbo consistent hash load balance: https://github.com/apache/dubbo/blob/2d9583adf26a2d8bd6fb646243a9fe80a77e65d5/dubbo-cluster/src/main/java/org/apache/dubbo/rpc/cluster/loadbalance/ConsistentHashLoadBalance.java
 */
public class ConsistentHashLoadBalance extends AbstractLoadBalance {
    private final ConcurrentHashMap<String, ConsistentSelector> selectors = new ConcurrentHashMap<>();

    /**
     * 内部类，实现一致性负载均衡策略选择
     */
    static class ConsistentSelector {
        private final TreeMap<Long, String> virtualInvokers;
        private final int identityHashCode;

        ConsistentSelector(List<String> invokes, int replicaNumber, int identityHashCode) {
            this.virtualInvokers = new TreeMap<>();
            this.identityHashCode = identityHashCode;

            for (String invoke : invokes) {
                for (int i = 0; i < replicaNumber / 4; i++) {
                    byte[] digest = md5(invoke + 1);
                    for (int h = 0; h < 4; h++) {
                        Long m = hash(digest, h);
                        virtualInvokers.put(m, invoke);
                    }
                }
            }
        }

        static byte[] md5(String key) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
                byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
                md.update(bytes);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            return md.digest();
        }

        static long hash(byte[] digest, int idx) {
            return ((long) (digest[3 + idx * 4] & 255) << 24 | (long) (digest[2 + idx * 4] & 255) << 16 | (long) (digest[1 + idx * 4] & 255) << 8 | (long) (digest[idx * 4] & 255)) & 4294967295L;
        }

        public String select(String rpcServiceKey) {
            byte[] bytes = md5(rpcServiceKey);
            return selectForKey(hash(bytes, 0));
        }

        public String selectForKey(long hashCode) {
            Map.Entry<Long, String> entry = virtualInvokers.tailMap(hashCode, true).firstEntry();
            if (entry == null) {
                entry = virtualInvokers.firstEntry();
            }
            return entry.getValue();
        }
    }

    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        int identityHashCode = System.identityHashCode(serviceAddresses);
        // 通过rpc请求构建rpc服务
        String rpcServiceName = rpcRequest.getRpcServiceName();
        ConsistentSelector consistentSelector = selectors.get(rpcServiceName);
        // 检查更新
        if (consistentSelector == null || consistentSelector.identityHashCode != identityHashCode) {
            selectors.put(rpcServiceName, new ConsistentSelector(serviceAddresses, 160, identityHashCode));
            consistentSelector = selectors.get(rpcServiceName);
        }
        return consistentSelector.select(rpcServiceName + Arrays.stream(rpcRequest.getParameters()));
    }
}
