package com.hzih.ra.web.action.ra;

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

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-16
 * Time: 下午5:32
 * To change this template use File | Settings | File Templates.
 */
public class OcspAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(OcspAction.class);

    public static ServiceResponse callService(String[][] params,String ip,String port) {
        String requestUrl = "http://"+ip+":"+port+"/publicca/OcspUtils_ocsp.action";
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

    public static ServiceResponse callOcspService(String[][] params,String ip,String port) {
        String requestUrl = "http://"+ip+":"+port+"/publicca/OcspAction_ocsp.action";
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

    public String ocsp()throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json = "{success:false}";
        String subjectX500Principal = request.getParameter("subjectX500Principal");
        String notBefore = request.getParameter("notBefore");
        String notAfter = request.getParameter("notAfter");
        String serialNumber = request.getParameter("serialNumber");
        String issuerX500Principal = request.getParameter("issuerX500Principal");
        String sigAlgName = request.getParameter("sigAlgName");
        String sigAlgOID = request.getParameter("sigAlgOID");
        String base64cert = request.getParameter("base64cert");
        String publicKey = request.getParameter("publicKey");
        String[][] params = new String[][] {
                {"subjectX500Principal",subjectX500Principal},
                {"notBefore",notBefore} ,
                {"notAfter",notAfter}  ,
                {"serialNumber",serialNumber}   ,
                {"issuerX500Principal",issuerX500Principal}    ,
                {"sigAlgName",sigAlgName}     ,
                {"sigAlgOID",sigAlgOID}     ,
                {"base64cert",base64cert}     ,
                {"publicKey",publicKey}
        };
        ServiceResponse serviceResponse = callService(params, CaConfigXml.getAttribute(CaConfigXml.ca_ip),CaConfigXml.getAttribute(CaConfigXml.ca_port));
        String serviceResponseData = serviceResponse.getData();
        if(serviceResponseData!=null){
//            String data = new String(serviceResponseData.getBytes("ISO-8859-1"),"utf-8");
            json = serviceResponseData;
        }
        writer.write(json);
        writer.close();
        return null;
    }

    /*public String ocsp()throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json = "{success:false}";
        String CERT_HEX_SN = request.getParameter("CERT_HEX_SN");
        String username = request.getParameter("username");
        String[][] params = new String[][] {
                {"CERT_HEX_SN",CERT_HEX_SN},
                {"username",username}
        };
        ServiceResponse serviceResponse = callOcspService(params,CaConfigXml.getAttribute(CaConfigXml.ca_ip),CaConfigXml.getAttribute(CaConfigXml.ca_port));
        String serviceResponseData = serviceResponse.getData();
        if(serviceResponseData!=null){
            json = serviceResponseData;
        }
        writer.write(json);
        writer.close();
        return null;
    }*/
    
}
