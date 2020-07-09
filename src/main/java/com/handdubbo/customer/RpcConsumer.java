package com.handdubbo.customer;

import com.handdubbo.provider.HelloService;
import com.handdubbo.proxy.RpcServiceProxy;

/**
 * @author: sxx
 * @Date 2020/6/26 15:56
 * @Version 1.0
 **/
public class RpcConsumer {

    public static void main(String[] args) {
        //在注册中心订阅服务, 获取服务所在的url, 然后通过代理远程调用服务
        ServiceSubscribe serviceSubscribe = new ServiceSubscribe();
        RpcServiceProxy rpcServiceProxy = new RpcServiceProxy(serviceSubscribe);
        //获取RPC代理
        HelloService bookService = (HelloService) rpcServiceProxy.getProxy(HelloService.class);
        bookService.sayHello("rpc");
    }
}
