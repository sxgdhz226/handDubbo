package com.handdubbo.framework.handler;

import com.handdubbo.framework.Invocation;
import com.handdubbo.framework.URL;
import com.handdubbo.register.Register;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

/**
 * @author: sxx
 * @Date 2020/6/26 14:36
 * @Version 1.0
 **/
public class HttpServerHandler {
    public void handler(HttpServletRequest req, HttpServletResponse resp){
        try{
            // Http请求流转为对象
            InputStream is = req.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            Invocation invocation = (Invocation)ois.readObject();

            // 寻找注册中心的实现类，通过反射执行方法
            Class implClass = Register.get(new URL("localhost",8080),invocation.getInterfaceName());
            Method method = implClass.getMethod(invocation.getMethodName(),invocation.getParamTypes());
            String result = (String) method.invoke(implClass.newInstance(),invocation.getParams());

            // 将结果返回
            IOUtils.write(result,resp.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
