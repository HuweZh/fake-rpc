package com.huhusw.config;

import com.huhusw.registry.zk.util.CuratorUtils;
import com.huhusw.remoting.transport.netty.server.NettyRpcServer;
import com.huhusw.utils.concurrent.threadpool.ThreadPoolFactoryUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * When the server  is closed, do something such as unregister all services
 * 关闭服务时调用的钩子方法
 * 单例模式
 */
@Slf4j
public class CustomShutdownHook {
    //单例模式，饿汉式，直接创建对象
    private static final CustomShutdownHook CUSTOM_SHUTDOWN_HOOK = new CustomShutdownHook();

    //返回对象
    public static CustomShutdownHook getCustomShutdownHook() {
        return CUSTOM_SHUTDOWN_HOOK;
    }

    /**
     * 清除所有的已注册服务
     * 新建线程清除所有已注册服务
     */
    public void clearAll() {
        log.info("addShutdownHook for clearAll");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), NettyRpcServer.PORT);
                CuratorUtils.clearRegistry(CuratorUtils.getZkClient(), inetSocketAddress);
            } catch (UnknownHostException ignored) {
            }
            ThreadPoolFactoryUtil.shutDownAllThreadPool();
        }));
    }
}