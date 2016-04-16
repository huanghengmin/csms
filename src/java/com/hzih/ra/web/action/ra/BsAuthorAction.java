package com.hzih.ra.web.action.ra;

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
 * Date: 13-4-18
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
public class BsAuthorAction extends ActionSupport {

    private CaUserService caUserService;

    public CaUserService getCaUserService() {
        return caUserService;
    }

    public void setCaUserService(CaUserService caUserService) {
        this.caUserService = caUserService;
    }

    private Logger logger = Logger.getLogger(BsAuthorAction.class);

    public String bsAuthor()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json ="{success:false}";
        String hzihcaserialNumber = request.getParameter("hzihcaserialNumber");
        CaUser caUser = caUserService.findBySerialNumber(hzihcaserialNumber);
        if(caUser!=null){
            if(caUser.getStatus()==0){
                json ="success:true";
            }
        }
        writer.write(json);
        return null;
    }
}
