package com.hzih.ra.web.action.audit;

import com.hzih.ra.entity.IpPort;
//import com.hzih.ra.servlet.CrlListener;
//import com.hzih.ra.syslog.ReLoadPublicCaSysLog;
import com.hzih.ra.utils.JDomUtil;
import com.hzih.ra.utils.ServiceResponse;
import com.hzih.ra.utils.StringContext;
import com.hzih.ra.web.action.ActionBase;
//import com.hzih.ra.web.action.ca.utils.JDomUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hhm
 * Date: 12-10-26
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */
public class SysLogConfigAction extends ActionSupport {
    private static final String xml = StringContext.systemPath+"/config/sysconfig.xml";
    private Logger logger = Logger.getLogger(SysLogConfigAction.class);
    private IpPort ipPort;

    public IpPort getIpPort() {
        return ipPort;
    }

    public void setIpPort(IpPort ipPort) {
        this.ipPort = ipPort;
    }
    
//    private void restartSendLogService(){
//        if( CrlListener.isRunSysLogService){
//            CrlListener.sysLogSendService.close();
//            CrlListener.isRunSysLogService=false;
//        }
//        CrlListener.sysLogSendService.init();
//        CrlListener.sysLogSendService.start();
//        CrlListener.isRunSysLogService=true;
//        logger.info("ca sysLog 重启成功!");
//    }

    public String add() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = "{success:false}";
        JDomUtil jDomUtils = new JDomUtil();
        String  flag = jDomUtils.add(xml,this.ipPort);
        if(flag.equals("true")){
            json = "{success:true,msg:'保存成功!'}";
//            restartSendLogService();
//            ServiceResponse resp = ReLoadPublicCaSysLog.reloadSysLog();
//            if(resp.getData().equals("true")){
//              logger.info("publicca sysLog 重启成功!");
//            }
        }else {
            json = "{success:false,msg:'"+flag+"'}";
        }
        actionBase.actionEnd(response, json, result);
        return null;

    }
    
    public String delete() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String json = "{success:false}";
        String result =	actionBase.actionBegin(request);
        String port = request.getParameter("port");
        String ip = request.getParameter("ip");
        JDomUtil jDomUtils = new JDomUtil();
        boolean  flag = jDomUtils.delete(xml,new IpPort(ip,Integer.parseInt(port)));
//        logger.info("delete flag ::::::::::"+flag);
        if(flag){
            json = "{success:true}";
//            restartSendLogService();
//            ServiceResponse resp = ReLoadPublicCaSysLog.reloadSysLog();
//            if(resp.getData().equals("true")){
//                logger.info("publicca sysLog 重启成功!");
//            }
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }
   
    public String modify() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String port = request.getParameter("port");
        String ip = request.getParameter("ip");
        String json = "{success:false}";
        JDomUtil jDomUtils = new JDomUtil();
        String  flag = jDomUtils.update(xml,new IpPort(ip,Integer.parseInt(port)),this.ipPort);
        if(flag.equals("true")){
            json = "{success:true,msg:'保存成功!'}";
//            restartSendLogService();
//            ServiceResponse resp = ReLoadPublicCaSysLog.reloadSysLog();
//            if(resp.getData().equals("true")){
//                logger.info("publicca sysLog 重启成功!");
//            }
        }else {
            json = "{success:false,msg:'"+flag+"'}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }
    
    public String ipPort_Json(IpPort ipPort){
        StringBuilder sb= new StringBuilder();
        sb.append("{");
        sb.append("ip:'"+ipPort.getIp()+"',");
        sb.append("port:'"+ipPort.getPort()+"'");
        sb.append("}");
        return sb.toString();
    }
    
    public String list_Json(List<IpPort> ipPorts)throws Exception{
        IpPort itIpPort = null;
        StringBuilder sb = new StringBuilder();
        Iterator<IpPort> ipPortIterator = ipPorts.iterator();
        while (ipPortIterator.hasNext()){
              itIpPort = ipPortIterator.next();
            if(ipPortIterator.hasNext())
              sb.append(ipPort_Json(itIpPort)).append(",");
            else
                sb.append(ipPort_Json(itIpPort));
        }
        StringBuilder json = new StringBuilder();
        json.append("{totalCount:"+ipPorts.size()+",root:["+ sb.toString()+ "]}");
         return json.toString();
    }

    public String getReturnData(Integer first, Integer limitInt, List<IpPort> list) {
        StringBuffer showData=new StringBuffer();
        int end=first+limitInt;
        int index = end>list.size()?list.size():end;
        for(int i=first;i<index;i++){
            showData.append(ipPort_Json(list.get(i)));
            if(i != index-1){
                showData.append(",");
            }
        }
        return showData.toString();
    }

    public String selectSysLogConfig() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String sStart = request.getParameter("start");
        String sLimit = request.getParameter("limit");
        JDomUtil jDomUtils = new JDomUtil();
        List<IpPort> ipPorts = jDomUtils.findAll(xml);
        int start =Integer.parseInt(sStart);
        int limit =  Integer.parseInt(sLimit);
        String json =   getReturnData(start, limit, ipPorts);
//        List<IpPort> resultIpPorts = new ArrayList<IpPort>();
//        int end=start+limit;
//        int index = end>ipPorts.size()?ipPorts.size():end;
//        for(int i=start;i<index;i++){
//           resultIpPorts.add(ipPorts.get(i));
//        }
//        String json = null;
//        try {
//            json = list_Json(resultIpPorts);
//        } catch (Exception e) {
//          logger.info(e.getMessage());
//        }
        json = "{totalCount:"+ipPorts.size()+",root:["+ json.toString()+ "]}";
        actionBase.actionEnd(response, json.toString(), result);
        return null;
    }
    
}
