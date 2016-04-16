package com.hzih.ra.web.action.district;
import com.hzih.ra.domain.District;
import com.hzih.ra.service.DistrictService;
import com.hzih.ra.service.LogService;
import com.hzih.ra.web.SessionUtils;
import com.hzih.ra.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

public class DistrictAction extends ActionSupport {
    private static Logger logger = Logger.getLogger(DistrictAction.class);
    private DistrictService districtService;
    private LogService logService;

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public StringBuilder resultJson(List<District> list){
       StringBuilder json = new StringBuilder("{totalCount:"+list.size()+",root:[");
        Iterator<District> iterator = list.iterator();
        while (iterator.hasNext()){
            District district = iterator.next();
            if(iterator.hasNext()){
                json.append("{");
                json.append("id:'"+district.getId()+"',");
                json.append("districtName:'"+district.getDistrictName()+"'");
                json.append("},");
            }else {
                json.append("{");
                json.append("id:'"+district.getId()+"',");
                json.append("districtName:'"+district.getDistrictName()+"'");
                json.append("}");
            }
        }
        json.append("]}");
        return json;
    }

    //所有的省
   /* public String comboParent() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        StringBuilder json = null;
        try {
            List<District> list = districtService.findParents();
            json = resultJson(list);
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "省市级联","获取省级信息成功!");
        } catch (Exception e) {
            logger.error("用户管理", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "省市级联","获取省级信息失败!");
        }
        actionBase.actionEnd(response,json.toString(),result);
        return null;
    }
    //省下市
    public String  comboChild()throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        long parentId = Long.parseLong(request.getParameter("parentId"));
        ActionBase actionBase = new ActionBase();     //改变请求时间
        String result =	actionBase.actionBegin(request);
        StringBuilder json = null;
        try {
            List<District> list = districtService.findChildByParent(parentId);
            json = resultJson(list);
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "省市级联","获取市级信息成功!");
        } catch (Exception e) {
            logger.error("用户管理", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "省市级联","获取市级信息失败!");
        }
        actionBase.actionEnd(response,json.toString(),result);
        return null;
    }

    public String comboChildByParentShow() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        long parentId = Long.parseLong(request.getParameter("parentId"));
        ActionBase actionBase = new ActionBase();     //改变请求时间
        String result =	actionBase.actionBegin(request);
        StringBuilder json = null;
        try {
            List<District> list = districtService.findChildByParent(parentId/10000*10000);
            json = resultJson(list);
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "省市级联","获取信息成功!!");
        } catch (Exception e) {
            logger.error("用户管理", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "省市级联","获取信息失败!!");
        }
        actionBase.actionEnd(response,json.toString(),result);
        return null;
    }*/

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        DistrictAction.logger = logger;
    }

    public DistrictService getDistrictService() {
        return districtService;
    }

    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

}
