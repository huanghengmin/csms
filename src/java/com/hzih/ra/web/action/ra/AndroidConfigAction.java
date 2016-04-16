package com.hzih.ra.web.action.ra;

import com.hzih.ra.service.LogService;
import com.hzih.ra.utils.StringContext;
import com.hzih.ra.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-18
 * Time: 上午9:21
 * To change this template use File | Settings | File Templates.
 */
public class AndroidConfigAction extends ActionSupport {
    private static final String xml = StringContext.systemPath+"/config/android_config.xml";
//    private static final String xml = "E:/fartec/ichange/ra/config/android_config.xml";
    private Logger logger = Logger.getLogger(AndroidConfigAction.class);
    private LogService logService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public String findConfig()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        int totalCount =0;
        StringBuilder stringBuilder = new StringBuilder();
        returnSystemConfigData(stringBuilder);
        totalCount = totalCount+1;
        StringBuilder json=new StringBuilder("{totalCount:"+totalCount+",root:[");
        json.append(stringBuilder.toString().substring(0,stringBuilder.toString().length()-1));
        json.append("]}");
        try {
            actionBase.actionEnd(response,json.toString(),result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAttribute(String elementName){
        Document document = buildFromFile(xml);
        Element root =  document.getRootElement();
        Element child = root.getChild(elementName);
        return child.getText();
    }
    
//    public static void main(String args[])throws Exception{
//        String ss =   getAttribute("allow_process") ;
//    }


    private void returnSystemConfigData(StringBuilder stringBuilder) {
        stringBuilder.append("{");
        stringBuilder.append("wifi_num:'"+ getAttribute("wifi_num")+"',");
        stringBuilder.append("booth_num:'"+getAttribute("booth_num")+"',");
        stringBuilder.append("up_time:'"+getAttribute("up_time")+"',");
        stringBuilder.append("clear_time:'"+getAttribute("clear_time")+"',");
        stringBuilder.append("disable_wifi:'"+getAttribute("disable_wifi")+"',");
        stringBuilder.append("disable_booth:'"+getAttribute("disable_booth")+"',");
        stringBuilder.append("stop_process:'"+getAttribute("stop_process")+"',");
        stringBuilder.append("allow_process:'"+getAttribute("allow_process")+"',");
        stringBuilder.append("stop_url:'"+getAttribute("stop_url")+"',");
        stringBuilder.append("allow_url:'"+getAttribute("allow_url")+"'");
        stringBuilder.append("},");
    }

    public String  config()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String wifi_num = request.getParameter("wifi_num");
        String booth_num = request.getParameter("booth_num");
        String up_time = request.getParameter("up_time");
        String clear_time = request.getParameter("clear_time");
      /*  String disable_wifi = request.getParameter("disable_wifi");
        String disable_booth = request.getParameter("disable_booth");
        if(disable_wifi==null||!disable_wifi.equals("on")){
            disable_wifi = "off";
        }
        if(disable_booth==null||!disable_booth.equals("on")){
            disable_booth = "off";
        }*/
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        Document document = buildFromFile(xml);
        if(document!=null){
            Element element = document.getRootElement();
            element.getChild("wifi_num").setText(wifi_num);
            element.getChild("booth_num").setText(booth_num);
            element.getChild("up_time").setText(up_time);
            element.getChild("clear_time").setText(clear_time);
           // element.getChild("disable_wifi").setText(disable_wifi);
           // element.getChild("disable_booth").setText(disable_booth);
            XMLOutputter outputter = new XMLOutputter();
            Format fm = Format.getRawFormat();
            fm.setExpandEmptyElements(true);
            fm.setEncoding("utf-8");
            outputter.setFormat(fm);
            FileWriter writer = new FileWriter(xml);
            outputter.output(document, writer);
            writer.close();
            json = "{success:true}";
        }else{
            Element root=new Element("root");
            Element wifi = new Element("wifi_num");
            wifi.setText(wifi_num);
            Element booth = new Element("booth_num");
            booth.setText(booth_num);
            Element up = new Element("up_time");
            up.setText(up_time);
            Element clear = new Element("clear_time");
            clear.setText(clear_time);
           // Element disable_wi = new Element("disable_wifi");
           // disable_wi.setText(disable_wifi);
         //   Element disable_boo = new Element("disable_booth");
          //  disable_boo.setText(disable_booth);
            root.addContent(wifi);
            root.addContent(booth);
            root.addContent(up);
            root.addContent(clear);
           // root.addContent(disable_wi);
           // root.addContent(disable_boo);
            Document doc=new Document(root);
            XMLOutputter outputter = new XMLOutputter();
            Format fm = Format.getRawFormat();
            fm.setExpandEmptyElements(true);
            fm.setEncoding("utf-8");
            outputter.setFormat(fm);
            FileWriter writer = new FileWriter(xml);
            outputter.output(doc, writer);
            writer.close();
            json = "{success:true}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String  strategy()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String allow_process = request.getParameter("allow_process") ;
        String stop_process = request.getParameter("stop_process");
        String disable_wifi = request.getParameter("disable_wifi");
        String disable_booth = request.getParameter("disable_booth");
        String stop_url = request.getParameter("stop_url");
        String allow_url = request.getParameter("allow_url");

        if(disable_wifi==null||!disable_wifi.equals("on")){
            disable_wifi = "off";
        }
        if(disable_booth==null||!disable_booth.equals("on")){
            disable_booth = "off";
        }
        if(allow_process==null||!allow_process.equals("on")){
            allow_process = "off";
        }
        if(stop_process==null||!stop_process.equals("on")){
            stop_process = "off";
        }
        if(stop_url==null||!stop_url.equals("on")){
            stop_url = "off";
        }
        if(allow_url==null||!allow_url.equals("on")){
            allow_url = "off";
        }
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        Document document = buildFromFile(xml);
        if(document!=null){
            Element element = document.getRootElement();
            element.getChild("allow_process").setText(allow_process);
            element.getChild("stop_process").setText(stop_process);
            element.getChild("disable_wifi").setText(disable_wifi);
            element.getChild("disable_booth").setText(disable_booth);
            element.getChild("stop_url").setText(stop_url);
            element.getChild("allow_url").setText(allow_url);
            XMLOutputter outputter = new XMLOutputter();
            Format fm = Format.getRawFormat();
            fm.setExpandEmptyElements(true);
            fm.setEncoding("utf-8");
            outputter.setFormat(fm);
            FileWriter writer = new FileWriter(xml);
            outputter.output(document, writer);
            writer.close();
            json = "{success:true}";
        }else{
            Element root=new Element("root");
            Element _allow_process = new Element("allow_process");
            _allow_process.setText(allow_process);
            Element _stop_process = new Element("stop_process");
            _stop_process.setText(stop_process);
            Element disable_wi = new Element("disable_wifi");
            disable_wi.setText(disable_wifi);
            Element disable_boo = new Element("disable_booth");
            disable_wi.setText(disable_wifi);
            Element _stop_url = new Element("stop_url");
            _stop_url.setText(stop_url);
            Element _allow_url = new Element("allow_url");
            _allow_url.setText(allow_url);
            root.addContent(_allow_process);
            root.addContent(_stop_process);
            root.addContent(disable_wi);
            root.addContent(disable_boo);
            root.addContent(_allow_url);
            root.addContent(_stop_url);
            Document doc=new Document(root);
            XMLOutputter outputter = new XMLOutputter();
            Format fm = Format.getRawFormat();
            fm.setExpandEmptyElements(true);
            fm.setEncoding("utf-8");
            outputter.setFormat(fm);
            FileWriter writer = new FileWriter(xml);
            outputter.output(doc, writer);
            writer.close();
            json = "{success:true}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public static  Document buildFromFile(String filePath) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new File(filePath));
            return anotherDocument;
        } catch (JDOMException e) {
//            logger.error(e.getMessage());
        } catch (NullPointerException e) {
//            logger.error(e.getMessage());
        } catch (IOException e) {
//            logger.error(e.getMessage());
        }
        return null;
    }
}
