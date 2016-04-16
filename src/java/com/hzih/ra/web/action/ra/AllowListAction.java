package com.hzih.ra.web.action.ra;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.AllowList;
import com.hzih.ra.service.AllowListService;
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
 * Date: 13-3-21
 * Time: 上午9:31
 * To change this template use File | Settings | File Templates.
 */
public class AllowListAction extends ActionSupport {
    private AllowListService allowListService;
    private Logger logger = Logger.getLogger(AllowListAction.class);
    private LogService logService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

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

    public AllowListService getAllowListService() {
        return allowListService;
    }

    public void setAllowListService(AllowListService allowListService) {
        this.allowListService = allowListService;
    }

    public String findProcess() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String processName = request.getParameter("processName");
        String processId = request.getParameter("processId");
//        String accessStatus = request.getParameter("accessStatus");
        PageResult pageResult = allowListService.findByPages(processName,processId,start,limit);
        if(pageResult!=null){
            List<AllowList> list = pageResult.getResults();
            int count =  pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" + count + ",rows:[";
                Iterator<AllowList> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    AllowList log = raUserIterator.next();
                    if(raUserIterator.hasNext()){
                        json += "{processName:'" + log.getProcessName() + "',processId:'" + log.getProcessId()  +
                                "'},";
                    }else {
                        json += "{processName:'" + log.getProcessName() + "',processId:'" + log.getProcessId() +
                                "'}";
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }

    public String delete() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String processId = request.getParameter("processId");
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        AllowList terminalApp = new AllowList(processId);
        boolean flag = allowListService.delete(terminalApp);
        if (flag) {
            logger.info("取消进程控制成功"+processId);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "StopList", "取消进程控件成功！");
            json = "{success:true}";
        } else {
            logger.info("取消进程控制失败"+processId);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "StopList", "取消进程控件失败！" );
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String add()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String processName = request.getParameter("processName");
        String processId = request.getParameter("processId");
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        AllowList terminalApp = new AllowList(processId,processName);
        boolean flag = allowListService.add(terminalApp);
        if (flag) {
            logger.info("终止进程访问成功"+processId);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "StopList", "终止进程访问成功！");
            json = "{success:true}";
        } else {
            logger.info("取消进程控制失败"+processId);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "StopList", "终止进程访问失败！" );
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }
}
