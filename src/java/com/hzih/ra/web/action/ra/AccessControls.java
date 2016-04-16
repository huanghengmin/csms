package com.hzih.ra.web.action.ra;

import com.hzih.ra.domain.*;
import com.hzih.ra.service.*;
import com.hzih.ra.syslog.SysLogSend;
import com.hzih.ra.utils.ServiceResponse;
import com.hzih.ra.web.action.ca.CaConfigXml;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-19
 * Time: 上午11:14
 * To change this template use File | Settings | File Templates.
 */
public class AccessControls extends ActionSupport {
    private Logger logger = Logger.getLogger(AccessControls.class);

    public CaUserService getCaUserService() {
        return caUserService;
    }
    private WhiteListService whiteListService;
    private BlackListService blackListService;
    private CaUserService caUserService;

    public WhiteListService getWhiteListService() {
        return whiteListService;
    }
    public void setWhiteListService(WhiteListService whiteListService) {
        this.whiteListService = whiteListService;
    }
    public BlackListService getBlackListService() {
        return blackListService;
    }
    public void setBlackListService(BlackListService blackListService) {
        this.blackListService = blackListService;
    }
    public void setCaUserService(CaUserService caUserService) {
        this.caUserService = caUserService;
    }

    //白名单网址过滤
    public boolean whiteFilter(String accessAddress){
        boolean flag = false;
        List<WhiteList> whiteListList = whiteListService.findAll();
        Iterator<WhiteList> whiteListIterator = whiteListList.iterator();
        while (whiteListIterator.hasNext()){
            WhiteList whiteList = whiteListIterator.next();
            String whiteUrl =whiteList.getUrl();
            if(whiteUrl.equals("*")){
                flag = true;
            }else if(whiteUrl.startsWith("*")){
                String white = whiteUrl.replace("*","");
                if(accessAddress.toLowerCase().endsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            } else if(whiteUrl.endsWith("*")){
                String white = whiteUrl.replace("*","");
                if(accessAddress.toLowerCase().startsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            } else if(whiteUrl.contains("*")){
                String white = whiteUrl.replace("*","");
                String[] split = whiteUrl.split("\\*");
                if(accessAddress.toLowerCase().startsWith(split[0])&&accessAddress.toLowerCase().endsWith(split[1])||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            }else{
                if (whiteUrl.equalsIgnoreCase(accessAddress)){
                    flag = true;
                }
            }
        }
        return flag;
    }


    //白名单网址过滤
    public boolean whiteTcpFilter(String accessAddress){
        boolean flag = false;
        List<WhiteList> whiteListList = whiteListService.findAll();
        Iterator<WhiteList> whiteListIterator = whiteListList.iterator();
        while (whiteListIterator.hasNext()){
            WhiteList whiteList = whiteListIterator.next();
            String whiteUrl =whiteList.getUrl();
            if(whiteUrl.equals("*")){
                flag = true;
            }else if(whiteUrl.startsWith("*")){
                String white = whiteUrl.replace("*","");
                if(accessAddress.toLowerCase().endsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            } else if(whiteUrl.endsWith("*")){
                String white = whiteUrl.replace("*","");
                if(accessAddress.toLowerCase().startsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            } else if(whiteUrl.contains("*")){
                String white = whiteUrl.replace("*","");
                String[] split = whiteUrl.split("\\*");
                if(accessAddress.toLowerCase().startsWith(split[0])&&accessAddress.toLowerCase().endsWith(split[1])||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            }else{
                if (whiteUrl.contains(accessAddress)){
                    flag = true;
                }
            }
        }
        return flag;
    }


    //黑名单网址过滤
    public boolean blackFilter(String accessAddress){
        boolean flag = false;
        List<BlackList> blackListList = blackListService.findAll();
        Iterator<BlackList> blackListIterator = blackListList.iterator();
        while (blackListIterator.hasNext()){
            BlackList blackList = blackListIterator.next();
            String blackUrl = blackList.getUrl();
            if(blackUrl.equals("*")){
                flag = true;
            }else if(blackUrl.startsWith("*")){
                String white = blackUrl.replace("*","");
                if(accessAddress.toLowerCase().endsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            } else if(blackUrl.endsWith("*")){
                String white = blackUrl.replace("*","");
                if(accessAddress.toLowerCase().startsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            } else if(blackUrl.contains("*")){
                String black = blackUrl.replace("*","");
                String[] split = blackUrl.split("\\*");
                if(accessAddress.toLowerCase().startsWith(split[0])&&accessAddress.toLowerCase().endsWith(split[1])||accessAddress.equalsIgnoreCase(black)){
                    flag = true;
                }
            }else{
                if (blackUrl.equalsIgnoreCase(accessAddress)){
                    flag = true;
                }
            }
        }
        return flag;
    }


    //黑名单网址过滤
    public boolean blackTcpFilter(String accessAddress){
        boolean flag = false;
        List<BlackList> blackListList = blackListService.findAll();
        Iterator<BlackList> blackListIterator = blackListList.iterator();
        while (blackListIterator.hasNext()){
            BlackList blackList = blackListIterator.next();
            String blackUrl = blackList.getUrl();
            if(blackUrl.equals("*")){
                flag = true;
            }else if(blackUrl.startsWith("*")){
                String white = blackUrl.replace("*","");
                if(accessAddress.toLowerCase().endsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            } else if(blackUrl.endsWith("*")){
                String white = blackUrl.replace("*","");
                if(accessAddress.toLowerCase().startsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            } else if(blackUrl.contains("*")){
                String black = blackUrl.replace("*","");
                String[] split = blackUrl.split("\\*");
                if(accessAddress.toLowerCase().startsWith(split[0])&&accessAddress.toLowerCase().endsWith(split[1])||accessAddress.equalsIgnoreCase(black)){
                    flag = true;
                }
            }else{
                if (blackUrl.contains(accessAddress)){
                    flag = true;
                }
            }
        }
        return flag;
    }


    //ocsp校验
    public boolean ocsp(String CERT_HEX_SN,String username){
        String json = null;
        String[][] params = new String[][] {
                {"CERT_HEX_SN",CERT_HEX_SN},
                {"username",username}
        };
        ServiceResponse serviceResponse = callOcspService(params, CaConfigXml.getAttribute(CaConfigXml.ca_ip),CaConfigXml.getAttribute(CaConfigXml.ca_port));
        String serviceResponseData = serviceResponse.getData();
        if(serviceResponseData!=null){
            json = serviceResponseData;
        }
        if(json!=null){
            if(json.startsWith("true")){
                return true;
            }
        }
        return false;
    }

    public ServiceResponse callOcspService(String[][] params,String ip,String port) {
        String requestUrl = "http://"+ip+":"+port+"/publicca/OcspAction_ocsp.action";
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5*60 * 1000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5*60 * 1000);
        PostMethod post = new PostMethod(requestUrl);
        post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5*60 * 1000);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        for (String[] param : params) {
            post.addParameter(param[0], param[1]);
        }
        ServiceResponse response = new ServiceResponse();
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(post);
            response.setCode(statusCode);
            logger.info("返回状态码"+statusCode);
            if (statusCode == 200) {
                String data = post.getResponseBodyAsString();
                response.setData(data);
            }
        } catch (Exception e) {
            logger.error("获取证书有效期失败!", e);
        }
        return response;
    }

    //bs 校验
    public  ServiceResponse callBsmsService(String[][] params,String ip,String port) {
        String requestUrl = "http://"+ip+":"+port+"/bsms/BsAuthorAction_access.action";
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5 * 1000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5 * 1000);
        PostMethod post = new PostMethod(requestUrl);
        post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5 * 1000);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        for (String[] param : params) {
            post.addParameter(param[0], param[1]);
        }
        ServiceResponse response = new ServiceResponse();
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(post);
            response.setCode(statusCode);
            logger.info("返回状态码"+statusCode);
            if (statusCode == 200) {
                String data = post.getResponseBodyAsString();
                response.setData(data);
            }
        } catch (Exception e) {
            logger.error("获取证书有效期失败!", e);
        }
        return response;
    }

    //鉴权问bs
/*    public String access()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String CERT_HEX_SN=null; //证书序列号
        String CERT_CN =null;   //证书Cn
        String granularity = null;
        String uri = null;
        String s_user = request.getHeader("pu");
        String g_uri = request.getHeader("pr");
        String msg = "";
        String[] s_user_values = null ;
        String[]  g_uri_values = null;
        String json = "false";
        
        if(s_user.contains(";")&&s_user!=null){
            s_user_values = s_user.split(";");
        }
        if(s_user_values!=null){
            for (String value:s_user_values){
                if(value.startsWith("CERT_HEX_SN=")){
                    CERT_HEX_SN = value.substring(value.indexOf("=")+1,value.length());
                }else if(value.startsWith("CERT_CN=")){
                    CERT_CN = value.substring(value.indexOf("=")+1,value.length());
                }
            }
        }
        if(g_uri.contains(";")&&g_uri!=null){
            g_uri_values = g_uri.split(";");
        }
        if(g_uri_values!=null){
            for (String value:g_uri_values){
                if(value.startsWith("granularity=")){
                    granularity = value.substring(value.indexOf("=")+1,value.length());
                }else if(value.startsWith(" uri =")){
                    uri = value.substring(value.indexOf("=")+1,value.length());
                }
            }
        }
        String username = null;
        String user_id = null;
        if(CERT_CN!=null){
            if(CERT_CN.contains(" ")){
                username = CERT_CN.substring(0,CERT_CN.lastIndexOf(" "));
                user_id = CERT_CN.substring(CERT_CN.lastIndexOf(" ")+1,CERT_CN.length());
            }else{
                username = CERT_CN;
            }
        }
//          Integer.toHexString(int);    // 十进制转化为十六进制。

        String serialNumber = CERT_HEX_SN.toUpperCase();    // 十六进制转化为十进制。
        while (serialNumber.startsWith("0")){
            serialNumber = serialNumber.substring(1,serialNumber.length());
        }
        CaUser caUser = caUserService.findBySerialNumber(String.valueOf(serialNumber));
        if(caUser!=null){
           if(caUser.getStatus()==0){
                if(caUser.getHzihcastatus().equals("3")){
                   if(ocsp(serialNumber,username)){
                        if(null!=uri&&!uri.equals("")){
                            String black_flag = AndroidConfigAction.getAttribute("stop_url") ;        //黑名单
                            String white_flag = AndroidConfigAction.getAttribute("allow_url");        //白名单
                            if(black_flag.equals("on")&&white_flag.equals("on")){
                                 if(!blackFilter(uri.trim())){
                                    if(whiteFilter(uri.trim())){
                                        String[][] params = new String[][] {
                                                {"CERT_HEX_SN",CERT_HEX_SN},
                                                {"username",username},
                                                {"uri",uri.trim()}
                                        };
                                        ServiceResponse serviceResponse = callBsmsService(params,CaConfigXml.getAttribute(CaConfigXml.bs_ip),CaConfigXml.getAttribute(CaConfigXml.bs_port));
                                        String serviceResponseData = serviceResponse.getData();
                                        if(serviceResponseData.equals("true")){
                                            response.setStatus(200);
                                            msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！";
                                            json ="true,"+msg;
                                        }
                                    }else {
                                        response.setStatus(403);
                                        msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！";
                                        json ="false,"+msg;
                                    }
                                 } else {
                                     response.setStatus(403);
                                     msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！";
                                     json ="false,"+msg;
                                 }
                            } else if(black_flag.equals("on")) {
                                 if(blackFilter(uri.trim())){
                                     response.setStatus(403);
                                     msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！";
                                     json ="false,"+msg;
                                 }else {
                                     String[][] params = new String[][] {
                                             {"CERT_HEX_SN",CERT_HEX_SN},
                                             {"username",username},
                                             {"uri",uri.trim()}
                                     };
                                     ServiceResponse serviceResponse = callBsmsService(params,CaConfigXml.getAttribute(CaConfigXml.bs_ip),CaConfigXml.getAttribute(CaConfigXml.bs_port));
                                     String serviceResponseData = serviceResponse.getData();
                                     if(serviceResponseData.equals("true")){
                                         response.setStatus(200);
                                         msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！";
                                         json ="true,"+msg;
                                     }
                                 }
                            }else if(white_flag.equals("on")){
                               if(whiteFilter(uri.trim())){
                                   String[][] params = new String[][] {
                                           {"CERT_HEX_SN",CERT_HEX_SN},
                                           {"username",username},
                                           {"uri",uri}
                                   };
                                   ServiceResponse serviceResponse = callBsmsService(params,CaConfigXml.getAttribute(CaConfigXml.bs_ip),CaConfigXml.getAttribute(CaConfigXml.bs_port));
                                   String serviceResponseData = serviceResponse.getData();
                                   if(serviceResponseData.equals("true")){
                                       response.setStatus(200);
                                       msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！";
                                       json ="true,"+msg;
                                   }
                               }else {
                                   response.setStatus(403);
                                   msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！";
                                   json ="false,"+msg;
                               }
                            } else {
                                response.setStatus(200);
                                msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！";
                                json ="true,"+msg;
                            }
                        }else {
                            response.setStatus(403);
                            msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！";
                            json ="false,"+msg;
                        }
                   }else {
                       response.setStatus(403);
                       msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！";
                       json ="false,"+msg;
                   }
                }else {
                   response.setStatus(403);  //不允许访问
                    msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！";
                    json ="false,"+msg;
                }
           }else {
               response.setStatus(403);    //不允许访问
               msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！";
               json ="false,"+msg;
           }
        } else {
            response.setStatus(403);     //不允许访问
            msg ="CERT_HEX_SN:"+CERT_HEX_SN+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！";
            json ="false,"+msg;
        }
        writer.write(json);
        writer.close();
        return null;
    }*/

    //bs问鉴权
    public String author()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json = "false";
        try{
        String serialNumber = request.getParameter("CERT_HEX_SN");
        String CERT_CN= request.getParameter("CERT_CN");
        if(CERT_CN.contains(" ")){
            CERT_CN = CERT_CN.replace(" ","_");
        }
        String uri = request.getParameter("uri");
        String granularity = request.getParameter("granularity");
//        String user_id = request.getParameter("user_id");
        String msg = "";
        CaUser caUser = caUserService.findBySerialNumber(String.valueOf(serialNumber));
        if(caUser!=null){
            caUser.setOnlinetime(new Date());
            caUserService.modify(caUser);
            String ca_user_serialNumber = caUser.getHzihcaserialNumber();
            if(ca_user_serialNumber!=null){
                if(TerminalAction.on_User_Map.get(ca_user_serialNumber)==null){
                    TerminalAction.on_User_Map.put(ca_user_serialNumber,caUser);
                    logger.info(caUser.getCn()+"证书序列号"+caUser.getHzihcaserialNumber()+"用户已上线!上线时间"+new Date().toString());
                }
            }
            if(caUser.getStatus()==0){
                if(caUser.getHzihcastatus().equals("3")){
//                    if(ocsp(serialNumber,username)){
                        if(null!=uri&&!uri.equals("")){
                            if(uri.startsWith("tcp://")){
                                uri = uri.replace("tcp://","");
                                String black_flag = AndroidConfigAction.getAttribute("stop_url") ;        //黑名单
                                String white_flag = AndroidConfigAction.getAttribute("allow_url");        //白名单
                                if(black_flag.equals("on")&&white_flag.equals("on")){
                                    if(!blackTcpFilter(uri.trim())){
                                        if(whiteTcpFilter(uri.trim())){
                                            if(checkResourcePowerTcp(CERT_CN,uri.trim())){
                                                msg = "CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用黑白名单策略";
                                                json ="true"/*+msg*/;
                                                logger.info(msg);
                                            }else {
                                                msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                                json = "false"/*+msg*/;
                                                logger.info(msg);
                                            }
                                        }else {
                                            msg = "CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑白名单策略";
                                            json = "false"/*+msg*/;
                                            logger.info(msg);
                                        }
                                    } else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑白名单策略";
                                        json = "false"/*+msg*/;
                                        logger.info(msg);
                                    }
                                } else if(black_flag.equals("on")) {
                                    if(blackTcpFilter(uri.trim())){
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑名单策略";
                                        json = "false"/*+msg*/;
                                        logger.info(msg);
                                    }else {
                                        if(checkResourcePowerTcp(CERT_CN,uri.trim())){
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用黑名单策略";
                                            json ="true"/*+msg*/;
                                            logger.info(msg);
                                        }else {
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                            json = "false"/*+msg*/;
                                            logger.info(msg);
                                        }
                                    }
                                }else if(white_flag.equals("on")){
                                    if(whiteTcpFilter(uri.trim())){
                                        if(checkResourcePowerTcp(CERT_CN,uri.trim())){
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用白名单策略";
                                            json ="true"/*+msg*/;
                                            logger.info(msg);
                                        }else {
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                            json = "false"/*+msg*/;
                                            logger.info(msg);
                                        }
                                    }else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用白名单策略";
                                        json = "false"/*+msg*/;
                                        logger.info(msg);
                                    }
                                } else {
                                    if(checkResourcePowerTcp(CERT_CN,uri.trim())){
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权未启用黑白名单策略";
                                        json ="true"/*+msg*/;
                                        logger.info(msg);

                                    }else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                        json = "false"/*+msg*/;
                                        logger.info(msg);
                                    }
                                }
                            }else {
                                String black_flag = AndroidConfigAction.getAttribute("stop_url") ;        //黑名单
                                String white_flag = AndroidConfigAction.getAttribute("allow_url");        //白名单
                                if(black_flag.equals("on")&&white_flag.equals("on")){
                                    if(!blackFilter(uri.trim())){
                                        if(whiteFilter(uri.trim())){
                                            if(checkResourcePower(CERT_CN,uri.trim())){
                                                msg = "CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用黑白名单策略";
                                                json ="true"/*+msg*/;
                                                logger.info(msg);
                                            }else {
                                                msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                                json = "false"/*+msg*/;
                                                logger.info(msg);
                                            }
                                        }else {
                                            msg = "CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑白名单策略";
                                            json = "false"/*+msg*/;
                                            logger.info(msg);
                                        }
                                    } else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑白名单策略";
                                        json = "false"/*+msg*/;
                                        logger.info(msg);
                                    }
                                } else if(black_flag.equals("on")) {
                                    if(blackFilter(uri.trim())){
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑名单策略";
                                        json = "false"/*+msg*/;
                                        logger.info(msg);
                                    }else {
                                        if(checkResourcePower(CERT_CN,uri.trim())){
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用黑名单策略";
                                            json ="true"/*+msg*/;
                                            logger.info(msg);
                                        }else {
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                            json = "false"/*+msg*/;
                                            logger.info(msg);
                                        }
                                    }
                                }else if(white_flag.equals("on")){
                                    if(whiteFilter(uri.trim())){
                                        if(checkResourcePower(CERT_CN,uri.trim())){
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用白名单策略";
                                            json ="true"/*+msg*/;
                                            logger.info(msg);
                                        }else {
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                            json = "false"/*+msg*/;
                                            logger.info(msg);
                                        }
                                    }else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用白名单策略";
                                        json = "false"/*+msg*/;
                                        logger.info(msg);
                                    }
                                } else {
                                    if(checkResourcePower(CERT_CN,uri.trim())){
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权未启用黑白名单策略";
                                        json ="true"/*+msg*/;
                                        logger.info(msg);

                                    }else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                        json = "false"/*+msg*/;
                                        logger.info(msg);
                                    }
                                }
                            }
                        }else {
                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！未获取访问地址";
                            json = "false"/*+msg*/;
                            logger.info(msg);
                        }
                    }else {
                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！ocsp校验失败";
                        json = "false"/*+msg*/;
                        logger.info(msg);
                    }
                }else {
                    msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权CA状态异常";
                    json = "false"/*+msg*/;
                    logger.info(msg);
                }
            }else {
                msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！用户已阻断访问";
                json = "false"/*+msg*/;
                logger.info(msg);
            }
       /* } else {
            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+username+"访问地址:"+uri+"鉴权否认！用户未找到,访问禁止";
            json = "false"*//*+msg*//*;
            logger.info(msg);
        }*/
        }catch (Exception e){
            logger.error("没有正确的用户请求,访问被拒绝!"+e.getMessage());
            json="false";
        }
        writer.write(json);
        writer.close();
        return null;
    }

    //直接访问鉴权校验
    public String JqpgAuthor()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json = "false";
        try{
            String CERT_HEX_SN=null; //证书序列号
            String CERT_CN =null;   //证书Cn
            String granularity = null;
            String uri = null;
            String s_user = request.getHeader("pu");
            String g_uri = request.getHeader("pr");
            String[] s_user_values = null ;
            String[]  g_uri_values = null;

            if(s_user!=null&&s_user.contains(";")&&s_user!=null){
                s_user_values = s_user.split(";");
            }
            if(s_user_values!=null){
                for (String value:s_user_values){
                    if(value.startsWith("CERT_HEX_SN=")){
                        CERT_HEX_SN = value.substring(value.indexOf("=")+1,value.length());
                    }else if(value.startsWith("CERT_CN=")){
                        CERT_CN = value.substring(value.indexOf("=")+1,value.length());
                    }
                }
            }
            if(g_uri.contains(";")&&g_uri!=null){
                g_uri_values = g_uri.split(";");
            }
            if(g_uri_values!=null){
                for (String value:g_uri_values){
                    if(value.startsWith("granularity=")){
                        granularity = value.substring(value.indexOf("=")+1,value.length());
                    }else if(value.startsWith(" uri =")){
                        uri = value.substring(value.indexOf("=")+1,value.length());
                    }else if(value.startsWith(" uri=tcp")){
                        uri = value.substring(value.indexOf("=")+1,value.length());
                    }
                }
            }
            String username = null;
            String user_id = null;
            if(CERT_CN!=null){
                if(CERT_CN.contains(" ")){
                    username = CERT_CN.substring(0,CERT_CN.lastIndexOf(" "));
                    user_id = CERT_CN.substring(CERT_CN.lastIndexOf(" ")+1,CERT_CN.length());
                }else{
                    username = CERT_CN;
                }
            }
            String serialNumber = CERT_HEX_SN.toUpperCase();    // 十六进制转化为十进制。
            while (serialNumber.startsWith("0")){
                serialNumber = serialNumber.substring(1,serialNumber.length());
            }


            String msg = "";
            CaUser caUser = caUserService.findBySerialNumber(String.valueOf(serialNumber));
            if(caUser!=null){
                caUser.setOnlinetime(new Date());
                caUserService.modify(caUser);
                String ca_user_serialNumber = caUser.getHzihcaserialNumber();
                if(ca_user_serialNumber!=null){
                    if(TerminalAction.on_User_Map.get(ca_user_serialNumber)==null){
                        TerminalAction.on_User_Map.put(ca_user_serialNumber,caUser);
                        logger.info(caUser.getCn()+"证书序列号"+caUser.getHzihcaserialNumber()+"用户已上线!上线时间"+new Date().toString());
                    }
                }
                if(caUser.getStatus()==0){
                    if(caUser.getHzihcastatus().equals("3")){
//                    if(ocsp(serialNumber,username)){
                        if(null!=uri&&!uri.equals("")){
                            if(uri.startsWith("tcp://")){
                                uri = uri.replace("tcp://","");
                                String black_flag = AndroidConfigAction.getAttribute("stop_url") ;        //黑名单
                                String white_flag = AndroidConfigAction.getAttribute("allow_url");        //白名单
                                if(black_flag.equals("on")&&white_flag.equals("on")){
                                    if(!blackTcpFilter(uri.trim())){
                                        if(whiteTcpFilter(uri.trim())){
                                            if(checkResourcePowerTcp(CERT_CN,uri.trim())){
                                                msg = "CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用黑白名单策略";
                                                json ="true"+msg;
                                                logger.info(msg);
                                                response.setStatus(200);
                                            }else {
                                                msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                                json = "false"+msg;
                                                logger.info(msg);
                                                response.setStatus(403);
                                            }
                                        }else {
                                            msg = "CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑白名单策略";
                                            json = "false"+msg;
                                            logger.info(msg);
                                            response.setStatus(403);
                                        }
                                    } else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑白名单策略";
                                        json = "false"+msg;
                                        logger.info(msg);
                                        response.setStatus(403);
                                    }
                                } else if(black_flag.equals("on")) {
                                    if(blackTcpFilter(uri.trim())){
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑名单策略";
                                        json = "false"+msg;
                                        logger.info(msg);
                                        response.setStatus(403);
                                    }else {
                                        if(checkResourcePowerTcp(CERT_CN,uri.trim())){
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用黑名单策略";
                                            json ="true"+msg;
                                            logger.info(msg);
                                            response.setStatus(403);
                                        }else {
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                            json = "false"+msg;
                                            logger.info(msg);
                                            response.setStatus(403);
                                        }
                                    }
                                }else if(white_flag.equals("on")){
                                    if(whiteTcpFilter(uri.trim())){
                                        if(checkResourcePowerTcp(CERT_CN,uri.trim())){
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用白名单策略";
                                            json ="true"+msg;
                                            logger.info(msg);
                                            response.setStatus(403);
                                        }else {
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                            json = "false"+msg;
                                            logger.info(msg);
                                            response.setStatus(403);
                                        }
                                    }else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用白名单策略";
                                        json = "false"+msg;
                                        logger.info(msg);
                                        response.setStatus(403);
                                    }
                                } else {
                                    if(checkResourcePowerTcp(CERT_CN,uri.trim())){
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权未启用黑白名单策略";
                                        json ="true"+msg;
                                        logger.info(msg);
                                        response.setStatus(403);

                                    }else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                        json = "false"+msg;
                                        logger.info(msg);
                                        response.setStatus(403);
                                    }
                                }
                            }else {
                                String black_flag = AndroidConfigAction.getAttribute("stop_url") ;        //黑名单
                                String white_flag = AndroidConfigAction.getAttribute("allow_url");        //白名单
                                if(black_flag.equals("on")&&white_flag.equals("on")){
                                    if(!blackFilter(uri.trim())){
                                        if(whiteFilter(uri.trim())){
                                            if(checkResourcePower(CERT_CN,uri.trim())){
                                                msg = "CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用黑白名单策略";
                                                json ="true"+msg;
                                                logger.info(msg);
                                                response.setStatus(200);
                                            }else {
                                                msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                                json = "false"+msg;
                                                logger.info(msg);
                                                response.setStatus(403);
                                            }
                                        }else {
                                            msg = "CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑白名单策略";
                                            json = "false"+msg;
                                            logger.info(msg);
                                            response.setStatus(403);
                                        }
                                    } else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑白名单策略";
                                        json = "false"+msg;
                                        logger.info(msg);
                                        response.setStatus(403);
                                    }
                                } else if(black_flag.equals("on")) {
                                    if(blackFilter(uri.trim())){
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用黑名单策略";
                                        json = "false"+msg;
                                        logger.info(msg);
                                        response.setStatus(403);
                                    }else {
                                        if(checkResourcePower(CERT_CN,uri.trim())){
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用黑名单策略";
                                            json ="true"+msg;
                                            logger.info(msg);
                                            response.setStatus(200);
                                        }else {
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                            json = "false"+msg;
                                            logger.info(msg);
                                            response.setStatus(403);
                                        }
                                    }
                                }else if(white_flag.equals("on")){
                                    if(whiteFilter(uri.trim())){
                                        if(checkResourcePower(CERT_CN,uri.trim())){
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权启用白名单策略";
                                            json ="true"+msg;
                                            logger.info(msg);
                                            response.setStatus(200);
                                        }else {
                                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                            json = "false"+msg;
                                            logger.info(msg);
                                            response.setStatus(403);
                                        }
                                    }else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权启用白名单策略";
                                        json = "false"+msg;
                                        logger.info(msg);
                                        response.setStatus(403);
                                    }
                                } else {
                                    if(checkResourcePower(CERT_CN,uri.trim())){
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"通过鉴权！鉴权未启用黑白名单策略";
                                        json ="true"+msg;
                                        logger.info(msg);
                                        response.setStatus(200);

                                    }else {
                                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！没有可访问资源";
                                        json = "false"+msg;
                                        logger.info(msg);
                                        response.setStatus(403);
                                    }
                                }
                            }
                        }else {
                            msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！未获取访问地址";
                            json = "false"+msg;
                            logger.info(msg);
                            response.setStatus(403);
                        }
                    }else {
                        msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！ocsp校验失败";
                        json = "false"+msg;
                        logger.info(msg);
                        response.setStatus(403);
                    }
                }else {
                    msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！鉴权CA状态异常";
                    json = "false"+msg;
                    logger.info(msg);
                    response.setStatus(403);
                }
            }else {
                msg ="CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权否认！用户已阻断访问";
                json = "false"+msg;
                logger.info(msg);
                response.setStatus(403);
            }
        }catch (Exception e){
            logger.error("没有正确的用户请求,访问被拒绝!"+e.getMessage());
            json="false";
            response.setStatus(403);
        }
        writer.write(json);
        writer.close();
        return null;
    }

    /**
     * 校验资源权限
     * @param CERT_CN
     * @param uri
     * @return
     */
    public boolean checkResourcePower(String CERT_CN,String uri){
        boolean flag = false;
        CaUser caUser = null;
        try {
            caUser = caUserService.findByCn(CERT_CN);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if(caUser!=null){
            try {
                Set<CaRole> caRoles =caUser.getCaRoles();
                if(caRoles!=null){
                      for (CaRole caRole:caRoles){
                            if(caRole!=null&&caRole.getStatus()!=1){
                                 Set<CaPermission> set = caRole.getCaPermissions();
                                if(set!=null){
                                    for (CaPermission caPermission:set){
                                        if(caPermission_filter(uri,caPermission.getUrl())){
                                            flag = true;
                                        }
                                    }
                                }
                            }
                      }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            return false;
        }
          return flag;
    }
    /**
     * 校验资源权限
     * @param CERT_CN
     * @param uri
     * @return
     */
    public boolean checkResourcePowerTcp(String CERT_CN,String uri){
        boolean flag = false;
        CaUser caUser = null;
        try {
            caUser = caUserService.findByCn(CERT_CN);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if(caUser!=null){
            try {
                Set<CaRole> caRoles =caUser.getCaRoles();
                if(caRoles!=null){
                    for (CaRole caRole:caRoles){
                        if(caRole!=null&&caRole.getStatus()!=1){
                            Set<CaPermission> set = caRole.getCaPermissions();
                            if(set!=null){
                                for (CaPermission caPermission:set){
                                    if(caPermission_Tcpfilter(uri,caPermission.getUrl())){
                                        flag = true;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else {
            return false;
        }
        return flag;
    }


    //白名单网址过滤
    public boolean caPermission_filter(String uri,String permission_url){
        boolean flag = false;
            if(permission_url.equals("*")){
                flag = true;
            }else if(permission_url.startsWith("*")){
                String white = permission_url.replace("*","");
                if(uri.toLowerCase().endsWith(white.toLowerCase())||uri.equalsIgnoreCase(white)){
                    flag = true;
                }
            } else if(permission_url.endsWith("*")){
                String white = permission_url.replace("*","");
                if(uri.toLowerCase().startsWith(white.toLowerCase())||uri.equalsIgnoreCase(white)){
                    flag = true;
                }
            } else if(permission_url.contains("*")){
                String white = permission_url.replace("*","");
                String[] split = permission_url.split("\\*");
                if(uri.toLowerCase().startsWith(split[0])&&uri.toLowerCase().endsWith(split[1])||uri.equalsIgnoreCase(white)){
                    flag = true;
                }
            }else{
                if (permission_url.equalsIgnoreCase(uri)){
                    flag = true;
                }
            }
        return flag;
    }


    //白名单网址过滤
    public boolean caPermission_Tcpfilter(String uri,String permission_url){
        boolean flag = false;
        if(permission_url.equals("*")){
            flag = true;
        }else if(permission_url.startsWith("*")){
            String white = permission_url.replace("*","");
            if(uri.toLowerCase().endsWith(white.toLowerCase())||uri.equalsIgnoreCase(white)){
                flag = true;
            }
        } else if(permission_url.endsWith("*")){
            String white = permission_url.replace("*","");
            if(uri.toLowerCase().startsWith(white.toLowerCase())||uri.equalsIgnoreCase(white)){
                flag = true;
            }
        } else if(permission_url.contains("*")){
            String white = permission_url.replace("*","");
            String[] split = permission_url.split("\\*");
            if(uri.toLowerCase().startsWith(split[0])&&uri.toLowerCase().endsWith(split[1])||uri.equalsIgnoreCase(white)){
                flag = true;
            }
        }else{
            if (permission_url.contains(uri)){
                flag = true;
            }
        }
        return flag;
    }

}

