package com.handdubbo.customer;

import com.handdubbo.framework.Invocation;
import com.handdubbo.provider.HelloService;
import com.handdubbo.utils.HttpClient;

/**
 * @author: sxx
 * @Date 2020/6/26 14:38
 * @Version 1.0
 **/
public class consumer {

    public static void main(String[] args) {

        // 调用哪个方法
        Invocation invocation = new Invocation(
                HelloService.class.getName(),
                "sayHello",
                new Object[]{"yukang"},
                new Class[]{String.class});

        // 发现服务器
        String result = new HttpClient().post("localhost",8080,invocation);
        System.out.println(result);

    }
}
