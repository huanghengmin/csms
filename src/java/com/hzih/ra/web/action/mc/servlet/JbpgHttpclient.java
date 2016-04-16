package com.hzih.ra.web.action.mc.servlet;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.Logger;

/**
 * VPN���� Created by IntelliJ IDEA. User: bluesky Date: 11-8-8 Time: ����3:02 To
 * change this template use File | Settings | File Templates.
 */
public class JbpgHttpclient {
    public static Logger logger = Logger.getLogger(JbpgHttpclient.class);
    protected HttpClient client;
    protected String host;
    public static String Str_urlHttp = "http://";
    public static String noblockurl = "/DoTermStatus";
    public static String blockurl = "/DoTermStatus";
    public static String pnourl = "&policeno=";
    public static String allUrl = "/DoTermStatusAll";


    protected GetMethod getMethod;

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
      * block �ն����
      */
    public boolean vpnblock(String pno, String cn, String ip) throws Exception {
        boolean result = false;
        String _cn = cn;
//        getMethod.setURI(new URI("http://192.168.2.206:8000/mc/IPlatManager?action=DoTermStatus&opername=yw&command=block&cn="+cn));
        getMethod.setURI(new URI(Str_urlHttp + host + blockurl));
//        cn = new String(cn.getBytes("GBK"),"ISO-8859-1");
        cn = new String(Base64.encodeBase64(cn.getBytes("GBK")));
        NameValuePair[] names = new NameValuePair[4];
        names[0] = new NameValuePair("opername", "yw");
        names[1] = new NameValuePair("command", "block");
        names[2] = new NameValuePair("cn", cn);
        names[3] = new NameValuePair("policeno", cn + " " + pno);
        getMethod.addRequestHeader("cn", cn);
        getMethod.setQueryString(names);
        logger.info("block url: "+getMethod.getURI());
        int code = client.executeMethod(getMethod);
        if (code == 200) {

            result = true;

            logger.info("vpnOnline request command:block,cn:" + _cn + ",ip:"
                    + ip + "response code:" + code + " response body:"
                    + getMethod.getResponseBodyAsString());

        } else {
            logger.info("vpnOnline request command:block,cn:" + _cn + ",ip:"
                    + ip + "response body code:" + code + " response body:"
                    + getMethod.getResponseBodyAsString());
        }
        return result;
    }

    /*
      * �ն���ϻָ�
      */
    public boolean vpnnoblock(String pno, String cn, String ip) throws Exception {
        boolean result = false;
        String _cn = cn;
        getMethod.setURI(new URI(Str_urlHttp + host + noblockurl));
        cn = new String(Base64.encodeBase64(cn.getBytes("GBK")));
//        cn = new String(cn.getBytes("GBK"),"ISO-8859-1");
        // getMethod.setRequestHeader("policeno", cn + " " + pno);
        NameValuePair[] names = new NameValuePair[4];
        names[0] = new NameValuePair("opername", "yw");
        names[1] = new NameValuePair("command", "noblock");
        names[2] = new NameValuePair("cn", cn);
        names[3] = new NameValuePair("policeno", cn + " " + pno);
        getMethod.addRequestHeader("cn", cn);
        getMethod.setQueryString(names);
        getMethod.setHttp11(true);
        int code = client.executeMethod(getMethod);
        logger.info("noblock url: "+getMethod.getURI());
        if (code == 200) {

            result = true;

            logger.info("vpnOnline request command:noblock,cn:" + _cn + ",ip:"
                    + ip + "response body:"
                    + getMethod.getResponseBodyAsString());

        } else {
            logger.info("vpnOnline request command:noblock,cn:" + _cn + ",ip:"
                    + ip + "response body:"
                    + getMethod.getResponseBodyAsString());
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
        getMethod = new GetMethod(Str_urlHttp + host + allUrl);
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

    /* public String vpnAll(String beginno, String endno, int pagesize)
            throws Exception {
        String result = null;
        getMethod = new GetMethod(Str_urlHttp + host + allUrl);
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

    }*/

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

    public void init(String host) {
        HttpClientParams clientparams = new HttpClientParams();
        clientparams.setConnectionManagerTimeout(5 * 60 * 1000);
        //clientparams.setContentCharset("GBK");
        //clientparams.setHttpElementCharset("GBK");
        client = new HttpClient(clientparams);
        getMethod = new GetMethod();
        this.host = host;

    }

    public void close() {
        getMethod.releaseConnection();
    }

    public static void main(String arg[]) throws Exception {
//        String cn = new String(Base64.encodeBase64("黄恒 民 111111111111111111".getBytes()));
//        System.out.println(new String(Base64.decodeBase64(cn.getBytes())));
//        String cn = "User4";
        JbpgHttpclient vpn = new JbpgHttpclient();
        vpn.init("192.168.1.8");
//        System.out.println(vpn.vpnblock("111111111111111111",cn , ""));
        System.out.println(vpn.vpnAll("0", "10", 500));
//        System.out.print(vpn.vpnnoblock("111111111111111111",cn,"192.168.1.8"));
    }
}
