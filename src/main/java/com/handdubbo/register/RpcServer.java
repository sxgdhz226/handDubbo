package com.handdubbo.register;

import com.handdubbo.framework.handler.RpcServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * RPC监听服务, 监听consumer远程调用的tcp连接
 * @author: sxx
 * @Date 2020/6/26 15:35
 * @Version 1.0
 **/
public class RpcServer {

    private Map<String, Class> serviceMap;

    public RpcServer(Map<String, Class> serviceMap) {
        this.serviceMap = serviceMap;
    }

    /**
     * 启动rpc监听服务
     */
    public void start(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(12000);
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(new RpcServerHandler(socket, serviceMap)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
