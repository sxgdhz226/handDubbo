package com.handdubbo;



import com.handdubbo.framework.servlet.DispatcherServlet;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * @author: sxx
 * @Date 2020/6/26 14:08
 * @Version 1.0
 **/
public class HttpServer {

    public void start(String hostName,int port){
        Tomcat tomcat = new Tomcat();
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");
        Connector connector = new Connector();
        connector.setPort(port);
        Engine engine = new StandardEngine();
        engine.setDefaultHost(hostName);

        Host host = new StandardHost();
        host.setName(hostName);
        //设置上下文
        String contextPath="";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);
        //设置拦截servlet
        tomcat.addServlet(contextPath,"dispather",new DispatcherServlet());
        context.addServletMappingDecoded("/*","dispather");
        try {
            //启动tomcat
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
