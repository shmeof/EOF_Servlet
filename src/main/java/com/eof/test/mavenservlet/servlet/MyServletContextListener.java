package com.eof.test.mavenservlet.servlet;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 初始化log4j：方法一
 */
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        ServletContext ctx = servletContextEvent.getServletContext();
//        String prefix = ctx.getRealPath("/");
//        // Log4J
//        String log4jFile = ctx.getInitParameter("log4j");
//        String log4jConfigPath = prefix + log4jFile;
//        PropertyConfigurator.configure(log4jConfigPath);
//        System.out.println("initialized log4j finish");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}