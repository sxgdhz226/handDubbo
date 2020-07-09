package com.handdubbo.framework;

/**
 * @author: sxx
 * @Date 2020/6/26 14:25
 * @Version 1.0
 **/
public class URL {
    private String hostname;
    private Integer port;
    public URL(String hostname,Integer port){
        this.hostname = hostname;
        this.port = port;
    }
    public String getHostname() {
        return hostname;
    }
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        URL url = (URL) o;
        if (hostname != null ? !hostname.equals(url.hostname) : url.hostname != null) return false;
        return port != null ? port.equals(url.port) : url.port == null;
    }
    @Override
    public int hashCode() {
        int result = hostname != null ? hostname.hashCode() : 0;
        result = 31 * result + (port != null ? port.hashCode() : 0);
        return result;
    }
}
