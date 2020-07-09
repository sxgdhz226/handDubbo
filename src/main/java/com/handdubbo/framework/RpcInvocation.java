package com.handdubbo.framework;

import java.io.Serializable;

/**
 * @author: sxx
 * @Date 2020/6/26 16:30
 * @Version 1.0
 **/
public class RpcInvocation implements Serializable {

    private static final long serialVersionUID = -2798340582119604989L;

    /**
     * 接口名
     */
    private String interfaceName;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数字节码数组
     */
    private Class[] paramTypes;
    /**
     * 参数对象
     */
    private Object[] params;


    public RpcInvocation(String interfaceName, String methodName, Class[] paramTypes, Object[] params) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.params = params;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
