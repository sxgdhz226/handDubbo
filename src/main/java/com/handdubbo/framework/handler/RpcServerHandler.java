package com.handdubbo.framework.handler;

import com.handdubbo.framework.Invocation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author: sxx
 * @Date 2020/6/26 15:46
 * @Version 1.0
 **/
public class RpcServerHandler implements Runnable{

    private Socket socket;
    private Map<String, Class> serviceMap;

    public RpcServerHandler(Socket socket, Map<String, Class> serviceMap) {
        this.socket = socket;
        this.serviceMap = serviceMap;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            //获取Invocation对象
            Invocation invocation = (Invocation) in.readObject();

            //执行对应方法
            Class clazz = serviceMap.get(invocation.getInterfaceName());
            Method method = clazz.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            Object invoke = method.invoke(clazz.newInstance(), invocation.getParams());

            //返回方法执行结果
            out.writeObject(invoke);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket = null;
        }
    }
}
