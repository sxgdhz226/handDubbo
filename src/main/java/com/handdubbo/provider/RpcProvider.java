package com.handdubbo.provider;

import com.handdubbo.provider.impl.HelloServiceImpl;
import com.handdubbo.register.RegisterCenter;
import com.handdubbo.register.RpcServer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: sxx
 * @Date 2020/6/26 15:51
 * @Version 1.0
 **/
public class RpcProvider {

    private static Map<String, Class> serviceMap = new HashMap<>();
    private static String tcpHost = "127.0.0.1:12000";

    static {
        /**
         * 模拟service配置处理逻辑
         * <dubbo:service interface="com.client.service.IBookService" ref="bookService" />
         * <bean id="bookService" class="com.provider.service.BookServiceImpl" />
         */
        serviceMap.put(HelloService.class.getName(), HelloServiceImpl.class);
    }

    public static void main(String[] args) {
        //将服务和服务提供者URL注册到注册中心
        RegisterCenter registerCenter = new RegisterCenter();
        for (Map.Entry<String, Class> entry : serviceMap.entrySet()) {
            registerCenter.register(entry.getKey(), tcpHost);
        }

        //监听Consumer的远程调用(为了简化代码, 这里使用TCP代替Netty)
        RpcServer rpcServer = new RpcServer(serviceMap);
        rpcServer.start();
    }
}
