package com.hzih.ra.web.action.ra;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.StopList;
import com.hzih.ra.service.LogService;
import com.hzih.ra.service.StopListService;
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
 * Date: 13-1-31
 * Time: 下午4:13
 * To change this template use File | Settings | File Templates.
 */
public class StopListAction extends ActionSupport {
    private StopListService stopListService;
    private Logger logger = Logger.getLogger(StopListAction.class);
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

    public StopListService getStopListService() {
        return stopListService;
    }

    public void setStopListService(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    public String findProcess() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String processName = request.getParameter("processName");
        String processId = request.getParameter("processId");
//        String accessStatus = request.getParameter("accessStatus");
        PageResult pageResult = stopListService.findByPages(processName,processId,start,limit);
        if(pageResult!=null){
            List<StopList> list = pageResult.getResults();
            int count =  pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" + count + ",rows:[";
                Iterator<StopList> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    StopList log = raUserIterator.next();
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
        StopList terminalApp = new StopList(processId);
        boolean flag = stopListService.delete(terminalApp);
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
        StopList terminalApp = new StopList(processId,processName);
        boolean flag = stopListService.add(terminalApp);
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
