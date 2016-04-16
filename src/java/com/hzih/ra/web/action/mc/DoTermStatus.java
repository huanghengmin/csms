package com.hzih.ra.web.action.mc;

import com.hzih.ra.domain.CaUser;
import com.hzih.ra.service.CaUserService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-23
 * Time: 下午1:28
 * To change this template use File | Settings | File Templates.
 */
public class DoTermStatus extends ActionSupport {
    private Logger logger = Logger.getLogger(DoTermStatus.class);
    public CaUserService getCaUserService() {
        return caUserService;
    }

    public void setCaUserService(CaUserService caUserService) {
        this.caUserService = caUserService;
    }

    private  CaUserService caUserService;
    //阻断与恢复
    public String block()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String  json = null;
        String opername = request.getHeader("opername");
        String command = request.getHeader("command");
        String cn = request.getHeader("cn");
        String policeno = request.getHeader("policeno");
        if(command.equals("block")){
             CaUser user =null;
            try{
               user = caUserService.findByCn(cn);
            }catch (Exception e){
               logger.error(e.getMessage());
            }
            if(user!=null){
                user.setStatus(1);
               boolean flag = caUserService.sleepRaUser(user);
                if(flag){
                    response.setStatus(200);
                    json = "阻断用户访问成功!";
                }else {
                    response.setStatus(404);
                    json = "阻断用户访问失败!";
                }
            }else {
                response.setStatus(404);
                json = "阻断用户访问失败!";
            }
        } else if(command.equals("noblock")){
            CaUser user = caUserService.findByCn(cn);
            if(user!=null){
                if(user.getStatus()==1){
                    user.setStatus(0);
                    boolean flag = false;
                    try{
                        caUserService.reCastRaUser(user);
                    }catch (Exception e){
                        logger.info(e.getMessage());
                    }
                    if(flag){
                        response.setStatus(200);
                        json = "恢复用户访问成功!";
                    }
                }
            }else {
                response.setStatus(404);
                json = "恢复用户访问失败!";
            }
        }

        return null;
    }
}
