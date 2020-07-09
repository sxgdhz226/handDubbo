package com.handdubbo.register;

import com.handdubbo.framework.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: sxx
 * @Date 2020/6/26 14:36
 * @Version 1.0
 **/
public class Register {

    private static Map<String,Map<URL,Class>> REGISTER = new HashMap<String, Map<URL, Class>>();

    /**
     * 注册服务（暴露接口）
     * @param url
     * @param interfaceName
     * @param implClass
     */
    public static void regist(URL url,String interfaceName,Class implClass){
        Map<URL,Class> map = new HashMap<URL, Class>();
        map.put(url,implClass);
        REGISTER.put(interfaceName,map);
    }

    /**
     * 从注册中心获取实现类（发现服务）
     * @param url
     * @param interfaceName
     * @return
     */
    public static Class get(URL url,String interfaceName){
        return REGISTER.get(interfaceName).get(url);
    }
}
