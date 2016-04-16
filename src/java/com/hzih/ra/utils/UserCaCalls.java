package com.hzih.ra.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-16
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public class UserCaCalls {
    private static final Logger logger = Logger.getLogger(UserCaCalls.class);

    public static ServiceResponse callParentCaValidateService(String[][] params,String ip,String port) {
        String requestUrl = "http://"+ip+":"+port+"/publicca/Ca_getParentCaValidate.action";
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

    public static ServiceResponse callExistAndAddService(String[][] params,String ip,String port,boolean  exist) {
        String requestUrl=null;
        if(exist){
            requestUrl  = "http://"+ip+":"+port+"/publicca/EndUser_existUserName.action";
        } else {
            requestUrl  = "http://"+ip+":"+port+"/publicca/EndUser_addUser.action";
        }
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
            logger.error("操作用户证书失败!", e);
        }
        return response;
    }

    public static ServiceResponse calRevokeUserService(String[][] params,String ip,String port) {
        String requestUrl  = "http://"+ip+":"+port+"/ca/CmsCommandAction_revokeUserCa.action";
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
            logger.error("操作用户证书失败!", e);
        }
        return response;
    }

    public static ServiceResponse calReleaseUserService(String[][] params,String ip,String port) {
        String requestUrl  = "http://"+ip+":"+port+"/ca/CmsCommandAction_releaseUserCa.action";
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
            logger.error("操作用户证书失败!", e);
        }
        return response;
    }
}
