package com.hzih.ra.syslog;

import com.hzih.ra.entity.IpPort;
import com.hzih.ra.utils.StringContext;
import com.inetec.common.exception.Ex;
import org.apache.log4j.Logger;
import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogConfigIF;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.impl.net.udp.UDPNetSyslogConfig;
import java.util.ArrayList;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: 钱晓盼
 * Date: 12-8-27
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
public class SysLogSendService implements Runnable{
    private static final Logger logger = Logger.getLogger(SysLogSendService.class);
    private static final String xml = StringContext.systemPath + "/config/sysconfig.xml";
    private List<IpPort> sysLogs = new ArrayList<IpPort>();
    public static String log;
    private String charset;
    private boolean isRunning = false;
    public void init(){
        try {
            Configuration config = new Configuration(xml);
            this.sysLogs = config.getSysLogClient();
        } catch (Ex ex) {
            logger.error("获取日志接收服务器地址端口出错",ex);
        }
    }


    public void sysLogSend(String log,String charset) {
        this.log = log;
        this.charset = charset;
        work();
    }

    public void run(){
        logger.info("进入发送日志线程!!");
        isRunning = true;
        while (isRunning){
            work();
            try {
                Thread.sleep(1000*5);
            } catch (InterruptedException e) {
            }
        }
    }

    private void work() {
        if(log!=null){
            if(sysLogs!=null){
                int i = 0;
                for(IpPort ipPort : sysLogs){
                    SyslogConfigIF config = new UDPNetSyslogConfig();
                    config.setHost(ipPort.getIp());
                    config.setCharSet(charset);
                    config.setPort(ipPort.getPort());
                    int j = i++;
                    SyslogIF syLog;
                    try{
                        syLog = Syslog.getInstance(String.valueOf(j));
                    } catch (Exception e){
                        syLog = null;
                    }
                    if(syLog==null){
                        syLog = Syslog.createInstance(String.valueOf(j), config);
                    }
                    syLog.info(log);
                    syLog.flush();
                    syLog.shutdown();
                }
            }
            log = null;
        }
    }


    public boolean isRunning(){
        return isRunning;
    }

    public void start(){
        isRunning = true;
    }

    public void close(){
        isRunning = false;
    }

    public static String getLog() {
        return log;
    }

    public void stop(){
        if(isRunning==true){
            isRunning=false;
        }
    }

    public static void setLog(String log) {
        SysLogSendService.log = log;
    }


}
