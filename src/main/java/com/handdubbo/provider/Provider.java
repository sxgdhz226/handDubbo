package com.handdubbo.provider;

import com.handdubbo.HttpServer;
import com.handdubbo.framework.URL;
import com.handdubbo.provider.impl.HelloServiceImpl;
import com.handdubbo.register.Register;


/**
 * @author: sxx
 * @Date 2020/6/26 14:13
 * @Version 1.0
 **/
public class Provider {

    public static void main(String[] args) {
        // 注册服务
        URL url = new URL("localhost",8080);
        Register.regist(url, HelloService.class.getName(), HelloServiceImpl.class);

        // 启动tomcat
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(),url.getPort());
    }
}
