package com.hzih.ra.web.action.mc;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;

/**
 * VPN调用 Created by IntelliJ IDEA. User: bluesky Date: 11-8-8 Time: 下午3:02 To
 * change this template use File | Settings | File Templates.
 */
public class JqpgHttpclient {
    public static Logger logger = Logger.getLogger(JqpgHttpclient.class);
    protected HttpClient client;
    protected String host;
    protected int port;
    public static String Str_urlHttp = "http://";
    public static String project_name = "/ra";    //项目名称
    public static String noblockurl = "/DoTermStatus_block.action";    //恢复
    public static String blockurl = "/DoTermStatus_block.action";     //阻断
    public static String pnourl = "&policeno=";    //
    public static String allUrl = "/DoTermStatusAll_getAllUser.action"; //所有用户
    public static String onLineUrl = "/DoTermStatusOnLine_online.action";   //在线用户列表
    protected GetMethod getMethod;     //


    /*
      * public String vpnOnline(String beginno, String endno, int pagesize)
      * throws Exception { String result = null;
      * getMethod.setRequestHeader("command", "onlinevpn");
      * getMethod.setRequestHeader("beginno", beginno);
      * getMethod.setRequestHeader("endno", endno);
      * getMethod.setRequestHeader("pagesize", String.valueOf(pagesize));
      * getMethod.setHttp11(true); int code = client.executeMethod(getMethod); if
      * (code == 200 && getMethod.getResponseContentLength() > 0) {
      *
      * result = new String(getMethod.getResponseBody(), "gbk");
      * logger.info("vpnOnline request command:onlinevpn,beginno:" + beginno +
      * ",endno:" + endno + ",pagesize:" + pagesize + "response body:" + result);
      * } else { result = new String(getMethod.getResponseBody(), "gbk");
      * logger.warn("vpnOnline request command:onlinevpn,beginno:" + beginno +
      * ",endno:" + endno + ",pagesize:" + pagesize + "response body:" + result);
      * } return result; }
      */

    /*
      * block 终端阻断
      */
    public boolean vpnblock(String pno, String cn, String ip) throws Exception {
        boolean result = false;
        getMethod.setURI(new URI(Str_urlHttp + host +":"+port+project_name+ blockurl));
        getMethod.addRequestHeader("opername", "yw");
        getMethod.addRequestHeader("command", "block");
        getMethod.addRequestHeader("cn", cn);
        getMethod.addRequestHeader("policeno", cn + " " + pno);
        int code = client.executeMethod(getMethod);
        if (code == 200) {

            result = true;

            logger.info("vpnOnline request command:block,cn:" + cn + ",ip:"
                    + ip + "response code:" + code + " response body:"
                    + new String(getMethod.getResponseBody(),"gbk"));

        } else {
            logger.info("vpnOnline request command:block,cn:" + cn + ",ip:"
                    + ip + "response body code:" + code + " response body:"
                    + new String(getMethod.getResponseBody(),"gbk"));
        }
        return result;
    }

    /*
      * 终端阻断恢复
      */
    public boolean vpnnoblock(String pno, String cn, String ip) throws Exception {
        boolean result = false;
        getMethod.setURI(new URI(Str_urlHttp + host +":"+port+project_name+ noblockurl));
        getMethod.addRequestHeader("cn", cn);
        getMethod.addRequestHeader("opername", "yw");
        getMethod.addRequestHeader("command", "noblock");
        getMethod.addRequestHeader("policeno", cn + " " + pno);
        getMethod.setHttp11(true);
        int code = client.executeMethod(getMethod);
        if (code == 200) {

            result = true;

            logger.info("vpnOnline request command:noblock,cn:" + cn + ",ip:"
                    + ip + "response body:"
                    + new String(getMethod.getResponseBody(),"gbk"));

        } else {
            logger.info("vpnOnline request command:noblock,cn:" + cn + ",ip:"
                    + ip + "response body:"
                    + new String(getMethod.getResponseBody(),"gbk"));
        }
        return result;
    }


    /**
     * @param beginno
     * @param endno
     * @param pagesize
     * @return
     * @throws Exception
     */
    public String vpnAll(String beginno, String endno, int pagesize)
            throws Exception {
        String result = null;
        getMethod = new GetMethod(Str_urlHttp + host +":"+port+project_name+ allUrl);
        getMethod.addRequestHeader("command", "allvpn");
        getMethod.addRequestHeader("beginno", beginno);
        getMethod.addRequestHeader("endno", endno);
        getMethod.addRequestHeader("pagesize", String.valueOf(pagesize));
        getMethod.setHttp11(true);
        int code = client.executeMethod(getMethod);
        if (code == 200 && getMethod.getResponseContentLength() > 0) {

            result = getMethod.getResponseBodyAsString();
            logger.info("vpnOnline request command:allvpn,beginno:" + beginno
                    + ",endno:" + endno + ",pagesize:" + pagesize
                    + "response body:" + result);
        } else {
            result = new String(getMethod.getResponseBody(), "gbk");
            logger.warn("vpnOnline request command:allvpn,beginno:" + beginno
                    + ",endno:" + endno + ",pagesize:" + pagesize
                    + "response body:" + result);
        }
        return result;

    }


    /**
     * @param beginno
     * @param endno
     * @param pagesize
     * @return
     * @throws Exception
     */
    public String vpnOnLine(String beginno, String endno, int pagesize) throws Exception {
        String result = null;
        getMethod = new GetMethod(Str_urlHttp + host +":"+port+project_name+ onLineUrl);
        getMethod.addRequestHeader("command", "onlinevpn");
        getMethod.addRequestHeader("beginno", beginno);
        getMethod.addRequestHeader("endno", endno);
        getMethod.addRequestHeader("pagesize", String.valueOf(pagesize));
        getMethod.setHttp11(true);
        int code = client.executeMethod(getMethod);
        if (code == 200 && getMethod.getResponseContentLength() > 0) {

            result = getMethod.getResponseBodyAsString();
            logger.info("vpnOnline request command:onlinevpn,beginno:" + beginno
                    + ",endno:" + endno + ",pagesize:" + pagesize
                    + "response body:" + result);
        } else {
            result = new String(getMethod.getResponseBody(), "gbk");
            logger.warn("vpnOnline request command:onlinevpn,beginno:" + beginno
                    + ",endno:" + endno + ",pagesize:" + pagesize
                    + "response body:" + result);
        }
        return result;

    }

    /*
      * public String vpnNew(String beginno, String endno, int pagesize) throws
      * Exception { String result = null; getMethod.setRequestHeader("command",
      * "newvpn"); getMethod.setRequestHeader("beginno", beginno);
      * getMethod.setRequestHeader("endno", endno);
      * getMethod.setRequestHeader("pagesize", String.valueOf(pagesize));
      * getMethod.setHttp11(true); int code = client.executeMethod(getMethod); if
      * (code == 200 && getMethod.getResponseContentLength() > 0) {
      *
      * result = new String(getMethod.getResponseBody(), "gbk");
      * logger.info("vpnOnline request command:onlinevpn,beginno:" + beginno +
      * ",endno:" + endno + ",pagesize:" + pagesize + "response body:" + result);
      * } else { result = new String(getMethod.getResponseBody(), "gbk");
      * logger.warn("vpnOnline request command:onlinevpn,beginno:" + beginno +
      * ",endno:" + endno + ",pagesize:" + pagesize + "response body:" + result);
      * } return result;
      *
      * }
      */

    public void init(String host,int port) {
        HttpClientParams clientparams = new HttpClientParams();
        clientparams.setConnectionManagerTimeout(5 * 60 * 1000);
        clientparams.setContentCharset("utf-8");
        clientparams.setHttpElementCharset("utf-8");
        client = new HttpClient(clientparams);
        getMethod = new GetMethod();
        this.port = port;
        this.host = host;

    }
    

    public void close() {
        getMethod.releaseConnection();
    }

    public static void main(String arg[]) throws Exception {
        JqpgHttpclient vpn = new JqpgHttpclient();
        vpn.init("192.168.1.8",8080);
//        vpn.vpnblock("0","User","10.0.0.2");
        vpn.vpnnoblock("0","User","10.0.0.2");
//        System.out.print(vpn.vpnAll("0","1",15));
//        System.out.print(vpn.vpnOnLine("0","1",15));
    }
}
