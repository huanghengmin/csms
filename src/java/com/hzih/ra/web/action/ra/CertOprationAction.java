package com.hzih.ra.web.action.ra;

import com.hzih.ra.entity.HzihUser;
import com.hzih.ra.utils.UserCaCalls;
import com.hzih.ra.utils.ServiceResponse;
import com.hzih.ra.web.action.ca.CaConfigXml;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: hhm
 * Date: 12-7-30
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
public class CertOprationAction extends ActionSupport {
    private Logger logger = Logger.getLogger(CertOprationAction.class);
    private HzihUser hzihUser;
    private String status = "1";

    public HzihUser getHzihUser() {
        return hzihUser;
    }

    public void setHzihUser(HzihUser hzihUser) {
        this.hzihUser = hzihUser;
    }

    public String addPublicUser()throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json = "{success:false}";
        String[][] params = new String[][] {
                {"cn",hzihUser.getCn()},
                {"hzihpassword",hzihUser.getHzihpassword()},
                {"hzihid",hzihUser.getHzihid()},
                {"hzihphone",hzihUser.getHzihphone()},
                {"hzihaddress", hzihUser.getHzihaddress()},
                {"hzihemail",hzihUser.getHzihemail()},
                {"hzihjobnumber",hzihUser.getHzihjobnumber()},
                {"hzihcertificatetype",hzihUser.getHzihcertificatetype()},
                {"hzihcavalidity",hzihUser.getHzihcavalidity()}
        };
        ServiceResponse serviceResponse = UserCaCalls.callExistAndAddService(params, CaConfigXml.getAttribute(CaConfigXml.ca_ip),CaConfigXml.getAttribute(CaConfigXml.ca_port),false);
        String serviceResponseData = serviceResponse.getData();
            if(serviceResponseData.equals("{success:true}")){
                logger.info("申请用户证书成功!");
                json = "{success:true}";
            }   else {
                logger.info("申请用户证书失败!");
            }
        writer.write(json);
        writer.close();
        return null;
    }

    public String existUserName()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String cn = request.getParameter("cn");
        String json ="{success:false}";
        String[][] params = new String[][] {
                {"cn",cn}
        };
        ServiceResponse serviceResponse = UserCaCalls.callExistAndAddService(params,CaConfigXml.getAttribute(CaConfigXml.ca_ip),CaConfigXml.getAttribute(CaConfigXml.ca_port),true);
        String serviceResponseData = serviceResponse.getData();
        if(serviceResponseData.equals("{success:true,msg:'false'}")){
            logger.info("已存在用户名!");
            json="{success:true,msg:'false'}";
        }   else {
            json="{success:true,msg:'true'}";
        }
        writer.write(json);
        writer.close();
        return null;
    }


    public static String getParentCaValidate() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String selectDate = request.getParameter("selectDate");
        String json ="{success:true,msg:'false'}";
        String[][] params = new String[][] {
                {"selectDate",selectDate}
        };
        ServiceResponse serviceResponse = UserCaCalls.callParentCaValidateService(params,CaConfigXml.getAttribute(CaConfigXml.ca_ip),CaConfigXml.getAttribute(CaConfigXml.ca_port));
        String serviceResponseData = serviceResponse.getData();
        if(serviceResponseData.startsWith("{success:true,msg:'false',validate:")){
            json = serviceResponseData;
        }   else {
            json = serviceResponseData;
        }
        writer.write(json);
        writer.close();
        return null;
    }
    
}
