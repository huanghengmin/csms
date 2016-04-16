package com.hzih.ra.web.action.ca;
import com.hzih.ra.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: hhm
 * Date: 12-11-8
 * Time: 下午11:11
 * To change this template use File | Settings | File Templates.
 */
public class CaAddressConfig extends ActionSupport {

    private Logger logger  = Logger.getLogger(CaAddressConfig.class);
    public String saveConfig() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json ="{success:false}";
        String ca_ip = request.getParameter("ca_ip");
        String ca_port = request.getParameter("ca_port");
//        String bs_ip = request.getParameter("bs_ip");
//        String bs_port = request.getParameter("bs_port");
        CaConfigXml.saveConfig(ca_ip,ca_port/*,bs_ip,bs_port*/);
        json ="{success:true}";
        actionBase.actionEnd(response,json,result);
        return null;
    }

    public String findConfig(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        int totalCount =0;
        StringBuilder stringBuilder = new StringBuilder();
        try{
        returnSystemConfigData(stringBuilder);
        }catch (Exception e){
           logger.error(e.getMessage());
        }
        totalCount = totalCount+1;
        StringBuilder json=new StringBuilder("{totalCount:"+totalCount+",root:[");
        json.append(stringBuilder.toString().substring(0,stringBuilder.toString().length()-1));
        json.append("]}");
        try {
            actionBase.actionEnd(response,json.toString(),result);
        } catch (IOException e) {
          logger.error(e.getMessage());
        }
        return null;
    }

    private void returnSystemConfigData(StringBuilder stringBuilder) {
        stringBuilder.append("{");
        stringBuilder.append("ca_ip:'"+ CaConfigXml.getAttribute(CaConfigXml.ca_ip)+"',");
        stringBuilder.append("ca_port:'"+ CaConfigXml.getAttribute(CaConfigXml.ca_port)+"'");
//        stringBuilder.append("bs_ip:'"+CaConfigXml.getAttribute(CaConfigXml.bs_ip)+"',");
//        stringBuilder.append("bs_port:'"+CaConfigXml.getAttribute(CaConfigXml.bs_port)+"'");
        stringBuilder.append("},");
    }
}
