package com.handdubbo.framework.servlet;

import com.handdubbo.framework.handler.HttpServerHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: sxx
 * @Date 2020/6/26 14:34
 * @Version 1.0
 **/
public class DispatcherServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 方便后期在此拓展服务
        new HttpServerHandler().handler(req, resp);
    }
}
