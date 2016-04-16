package com.hzih.ra.syslog;

import com.hzih.ra.web.servlet.SiteContextLoaderServlet;

/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 12-8-27
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
public class SysLogSend {

    public static void sysLog(String log) {
        if(SiteContextLoaderServlet.sysLogSendService.isRunning()){
            //传json
            SiteContextLoaderServlet.sysLogSendService.sysLogSend(log, "GBK");
        }
    }
}
