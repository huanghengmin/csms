package com.hzih.ra.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

public class OnLineUserListener implements ServletContextListener {
    private Timer timer = null;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        timer.cancel();
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        timer = new Timer(true);
        //设置任务计划，启动和间隔时间
        timer.schedule(new OnLineUserTask(), 0, 60*1000);
        //用户在线检查开始

    }
}
