package com.handdubbo.provider.impl;

import com.handdubbo.provider.HelloService;

/**
 * @author: sxx
 * @Date 2020/6/26 14:12
 * @Version 1.0
 **/
public class HelloServiceImpl implements HelloService {
    public void sayHello(String username) {
        System.out.println("Hello:"+username);
    }
}
