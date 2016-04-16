package com.hzih.ra.web.action.district;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.dao.OrgcodeDao;
import com.hzih.ra.domain.Orgcode;
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

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-14
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */
public class OrgCodeAction extends ActionSupport {
    private Logger logger = Logger.getLogger(OrgCodeAction.class);
    private LogService logService;
    private OrgcodeDao orgcodeDao;
    private int start;
    private int limit;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public OrgcodeDao getOrgcodeDao() {
        return orgcodeDao;
    }

    public void setOrgcodeDao(OrgcodeDao orgcodeDao) {
        this.orgcodeDao = orgcodeDao;
    }

    public String findOrgcodeByDistrict()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String orgcode = request.getParameter("orgcode");
        StringBuilder json = null;
        try {
            PageResult pageResult =  orgcodeDao.findOrgcodeByDistrict(orgcode,start/limit+1,limit);
            if(pageResult!=null){
                List<Orgcode> orgcodes  = pageResult.getResults();
                int count =  pageResult.getAllResultsAmount();
                if(orgcodes!=null){
                    json = orgcodeJson(orgcodes,count);
                }
            }
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "省市级联","获取市信息成功!");
        } catch (Exception e) {
            logger.error("用户管理", e);
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "省市级联","获取市信息失败!");
        }
        actionBase.actionEnd(response,json.toString(),result);
        return null;
    }


    private StringBuilder orgcodeJson(List<Orgcode> orgcodes, int count) {
        StringBuilder json = new StringBuilder("{totalCount:"+count+",root:[");
        Iterator<Orgcode> iterator = orgcodes.iterator();
        while (iterator.hasNext()){
            Orgcode orgcode = iterator.next();
            if(iterator.hasNext()){
                json.append("{");
                json.append("orgcode:'"+orgcode.getOrgcode()+"',");
                json.append("orgname:'"+orgcode.getOrgname()+"'");
                json.append("},");
            }else {
                json.append("{");
                json.append("orgcode:'"+orgcode.getOrgcode()+"',");
                json.append("orgname:'"+orgcode.getOrgname()+"'");
                json.append("}");
            }
        }
        json.append("]}");
        return json;
    }

//    public StringBuilder orgJson(List<Orgcode> list,int count){
//        StringBuilder json = new StringBuilder("{totalCount:"+count+",root:[");
//        Iterator<Orgcode> iterator = list.iterator();
//        while (iterator.hasNext()){
//            Orgcode orgcode = iterator.next();
//            if(iterator.hasNext()){
//                json.append("{");
//                json.append("orgcode:'"+orgcode.getOrgcode()+"',");
//                if(orgcode.getOrgname().equals("公安部")){
//                    json.append("orgname:'"+orgcode.getOrgname().substring(0,3)+"'");
//                }else if(orgcode.getOrgname().equals("新疆公安厅")){
//                    json.append("orgname:'"+orgcode.getOrgname().substring(0,5)+"'");
//                } else {
//                    json.append("orgname:'"+orgcode.getOrgname().substring(0,2)+"'");
//                }
//                json.append("},");
//            }else {
//                json.append("{");
//                json.append("orgcode:'"+orgcode.getOrgcode()+"',");
//                if(orgcode.getOrgname().equals("公安部")){
//                    json.append("orgname:'"+orgcode.getOrgname().substring(0,3)+"'");
//                }else if(orgcode.getOrgname().equals("新疆公安厅")){
//                    json.append("orgname:'"+orgcode.getOrgname().substring(0,5)+"'");
//                }else {
//                    json.append("orgname:'"+orgcode.getOrgname().substring(0,2)+"'");
//                }
//                json.append("}");
//            }
//        }
//        json.append("]}");
//        return json;
//    }
//
//    public String findCityByProvinceCode()throws Exception{
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        ActionBase actionBase = new ActionBase();
//        String result =	actionBase.actionBegin(request);
//        String orgcode = request.getParameter("orgcode");
//        StringBuilder json = null;
//        try {
//            PageResult pageResult =  orgcodeDao.findCityByProvinceCode(orgcode,start/limit+1,limit);
//            if(pageResult!=null){
//                List<Orgcode> orgcodes  = pageResult.getResults();
//                int count =  pageResult.getAllResultsAmount();
//                if(orgcodes!=null){
//                    json = orgcodeJson(orgcodes,count);
//                }
//            }
//            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "省市级联","获取市信息成功!");
//        } catch (Exception e) {
//            logger.error("用户管理", e);
//            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "省市级联","获取市信息失败!");
//        }
//        actionBase.actionEnd(response,json.toString(),result);
//        return null;
//    }
//
//    private StringBuilder orgcodeJson(List<Orgcode> orgcodes, int count) {
//        StringBuilder json = new StringBuilder("{totalCount:"+count+",root:[");
//        Iterator<Orgcode> iterator = orgcodes.iterator();
//        while (iterator.hasNext()){
//            Orgcode orgcode = iterator.next();
//            if(iterator.hasNext()){
//                json.append("{");
//                json.append("orgcode:'"+orgcode.getOrgcode()+"',");
//                json.append("orgname:'"+orgcode.getOrgname()+"'");
//                json.append("},");
//            }else {
//                json.append("{");
//                json.append("orgcode:'"+orgcode.getOrgcode()+"',");
//                json.append("orgname:'"+orgcode.getOrgname()+"'");
//                json.append("}");
//            }
//        }
//        json.append("]}");
//        return json;
//    }
//
//
//    private StringBuilder orgDistrictJson(List<Orgcode> orgcodes, int count) {
//        StringBuilder json = new StringBuilder("{totalCount:"+count+",root:[");
//        Iterator<Orgcode> iterator = orgcodes.iterator();
//        while (iterator.hasNext()){
//            Orgcode orgcode = iterator.next();
//            if(iterator.hasNext()){
//                json.append("{");
//                json.append("id:'"+orgcode.getOrgcode()+"',");
//                json.append("districtName:'"+orgcode.getOrgname()+"'");
//                json.append("},");
//            }else {
//                json.append("{");
//                json.append("id:'"+orgcode.getOrgcode()+"',");
//                json.append("districtName:'"+orgcode.getOrgname()+"'");
//                json.append("}");
//            }
//        }
//        json.append("]}");
//        return json;
//    }
//
//    public String findProvinceCode()throws Exception{
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        ActionBase actionBase = new ActionBase();     //改变请求时间
//        String result =	actionBase.actionBegin(request);
//        StringBuilder json = null;
//        try {
//            PageResult pageResult =  orgcodeDao.findProvinceCode(start/limit+1,limit);
//            if(pageResult!=null){
//                List<Orgcode> orgcodes  = pageResult.getResults();
//                int count =  pageResult.getAllResultsAmount();
//                if(orgcodes!=null){
//                    json = orgJson(orgcodes,count);
//                }
//            }
//            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "省市级联","获取省信息成功!");
//        } catch (Exception e) {
//            logger.error("用户管理", e);
//            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "省市级联","获取省信息失败!");
//        }
//        actionBase.actionEnd(response,json.toString(),result);
//        return null;
//    }
//    
//    public String findDataByDistrict()throws Exception{
//        HttpServletRequest request = ServletActionContext.getRequest();
//        HttpServletResponse response = ServletActionContext.getResponse();
//        ActionBase actionBase = new ActionBase();
//        String result =	actionBase.actionBegin(request);
//        String orgcode = request.getParameter("orgcode");
//        StringBuilder json = null;
//        try {
//            PageResult pageResult =  orgcodeDao.findDataByDistrict(orgcode,start/limit+1,limit);
//            if(pageResult!=null){
//                List<Orgcode> orgcodes  = pageResult.getResults();
//                int count =  pageResult.getAllResultsAmount();
//                if(orgcodes!=null){
//                    json = orgDistrictJson(orgcodes,count);
//                }
//            }
//            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "省市级联","获取区县信息成功!");
//        } catch (Exception e) {
//            logger.error("用户管理", e);
//            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "省市级联","获取区县信息失败!");
//        }
//        actionBase.actionEnd(response,json.toString(),result);
//        return null;
//    }
}
