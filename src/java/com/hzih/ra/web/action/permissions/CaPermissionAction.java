package com.hzih.ra.web.action.permissions;
import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.CaPermission;
import com.hzih.ra.service.CaPermissionService;
import com.hzih.ra.service.CaRolePermissionService;
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
 * Date: 13-4-27
 * Time: 下午2:33
 * To change this template use File | Settings | File Templates.
 */
public class CaPermissionAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(CaPermissionAction.class);
    private LogService logService;
    private int start;
    private int limit;
    private CaPermission caPermission;
    private CaPermissionService caPermissionService;
    private CaRolePermissionService caRolePermissionService;

    public CaRolePermissionService getCaRolePermissionService() {
        return caRolePermissionService;
    }

    public void setCaRolePermissionService(CaRolePermissionService caRolePermissionService) {
        this.caRolePermissionService = caRolePermissionService;
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

    public CaPermission getCaPermission() {
        return caPermission;
    }

    public void setCaPermission(CaPermission caPermission) {
        this.caPermission = caPermission;
    }

    public CaPermissionService getCaPermissionService() {
        return caPermissionService;
    }

    public void setCaPermissionService(CaPermissionService caPermissionService) {
        this.caPermissionService = caPermissionService;
    }

    public String add() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        try{
            boolean flag = caPermissionService.add(caPermission);
            if(flag){
                json= "{success:true}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户新增资源信息成功");
            }  else {
                json = "{success:false,msg:'新增资源失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户新增资源信息失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String modify()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        String id = request.getParameter("id");
        caPermission.setId(Integer.parseInt(id));
        try{
            boolean flag = caPermissionService.modify(caPermission);
            if(flag){
                json= "{success:true}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户更新资源信息成功");
            }  else {
                json = "{success:false,msg:'更新资源失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户更新资源信息失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delete()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String id = request.getParameter("id");
        String json = "{success:false}";
        CaPermission ca_per = new CaPermission();
        ca_per.setId(Integer.parseInt(id));
        try{
            caRolePermissionService.delByPermissionId(Integer.parseInt(id)) ;
           caPermissionService.delete(ca_per);
            json= "{success:true,msg:'删除资源成功'}";
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户删除资源信息成功");
        }catch (Exception e){
            json = "{success:false,msg:'删除资源失败'}";
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户删除资源信息失败");
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    /**
     * 查找
     * @return
     * @throws Exception
     */
    public String findByPages() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String url = request.getParameter("url");
        PageResult pageResult =  caPermissionService.findByPages(url,start,limit);
        if(pageResult!=null){
            List<CaPermission> list = pageResult.getResults();
            int count =  pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" + count + ",rows:[";
                Iterator<CaPermission> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    CaPermission log = raUserIterator.next();
                    if(raUserIterator.hasNext()){
                        json += "{" +
                                "id:'"+log.getId()+
                                "',url:'" + log.getUrl() +"'" +
                                "},";
                    }else {
                        json += "{" +
                                "id:'"+log.getId()+
                                "',url:'" + log.getUrl() + "'" +
                                "}";
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }






    /**
     * 校验URL
     * @return
     * @throws Exception
     */
    public String checkUrl()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String url = request.getParameter("url");
        String json = "{success:true,msg:'true'}";
        try{
        CaPermission ca_permission = caPermissionService.checkUrl(url);
            if(ca_permission!=null){
                json = "{success:true,msg:'false'}";
            }
        }catch (Exception e){
            json = "{success:true,msg:'true'}";
            logger.error(e.getMessage());
        }

        actionBase.actionEnd(response, json, result);
        return null;
    }

}
