package com.hzih.ra.web.action.ra;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.AccessAddress;
import com.hzih.ra.service.AccessAddressService;
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
 * Date: 12-12-27
 * Time: 下午12:05
 * To change this template use File | Settings | File Templates.
 */
public class AccessAddressAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(AccessAddressAction.class);
    private LogService logService;
    private AccessAddressService accessAddressService;
    private int start;
    private int limit;
    private AccessAddress accessAddress;

    public AccessAddress getAccessAddress() {
        return accessAddress;
    }

    public void setAccessAddress(AccessAddress accessAddress) {
        this.accessAddress = accessAddress;
    }

    public AccessAddressService getAccessAddressService() {
        return accessAddressService;
    }

    public void setAccessAddressService(AccessAddressService accessAddressService) {
        this.accessAddressService = accessAddressService;
    }


    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }


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

    public String joinBlackList() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String url = request.getParameter("url");
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
       AccessAddress joinAccessAddress = new AccessAddress(url);
        joinAccessAddress.setStatus(1);
        boolean flag = accessAddressService.joinBlackList(joinAccessAddress);
        if (flag) {
            logger.info("加入黑名单成功");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "加入黑名单成功！");
            json = "{success:true}";
        } else {
            logger.info("加入黑名单失败");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "加入黑名单失败！");
        }

        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String joinWhiteList() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String url = request.getParameter("url");
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        AccessAddress joinAccessAddress = new AccessAddress(url);
        joinAccessAddress.setStatus(0);
        boolean flag = accessAddressService.joinWhiteList(joinAccessAddress);
        if (flag) {
            logger.info("重新加入白名单成功");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "重新加入白名单成功！");
            json = "{success:true}";
        } else {
            logger.info("重新加入白名单失败");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "重新加入白名单失败！");
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String add() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        boolean flag = accessAddressService.add(accessAddress);
        if (flag) {
            logger.info("新增记录成功");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "新增记录成功！");
            json = "{success:true}";
        } else {
            logger.info("新增记录失败");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "新增记录失败！");
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String modify() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        String oldUrl = request.getParameter("oldUrl");
        AccessAddress oldAccessAddress = new AccessAddress(oldUrl);
        accessAddressService.delete(oldAccessAddress);
        boolean flag = accessAddressService.modify(accessAddress);
        if (flag) {
            logger.info("更新记录成功");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "更新记录成功！" );
            json = "{success:true}";
        } else {
            logger.info("更新记录失败");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "更新记录失败！" );
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delete() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String url = request.getParameter("url");
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        AccessAddress delAccessAddress = new AccessAddress(url);
        boolean flag = accessAddressService.delete(delAccessAddress);
        if (flag) {
            logger.info("删除记录成功");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "删除记录成功！");
            json = "{success:true}";
        } else {
            logger.info("删除记录失败");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "删除记录失败！" );
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String checkUrl() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String url = request.getParameter("url");
        String json = "{success:true,msg:'true'}";
        AccessAddress checkUrlAddress = accessAddressService.checkUrl(url);
        if(checkUrlAddress!=null){
            json = "{success:true,msg:'false'}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String findByPages() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String url = request.getParameter("url");
        String status = request.getParameter("status");
        PageResult pageResult = accessAddressService.findByPages(url,status,start,limit);
        if(pageResult!=null){
            List<AccessAddress> list = pageResult.getResults();
            int count = pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" +count + ",rows:[";
                Iterator<AccessAddress> accessAddressIterator = list.iterator();
                while (accessAddressIterator.hasNext()){
                    AccessAddress log = accessAddressIterator.next();
                    if(accessAddressIterator.hasNext()){
                        json += "{url:'" + log.getUrl() + "',status:'" + log.getStatus() + "'},";
                    }else {
                        json += "{url:'" + log.getUrl() + "',status:'" + log.getStatus() + "'}";
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }
}
