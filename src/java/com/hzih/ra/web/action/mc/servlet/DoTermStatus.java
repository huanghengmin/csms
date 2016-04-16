package com.hzih.ra.web.action.mc.servlet;

import com.hzih.ra.domain.CaUser;
import com.hzih.ra.service.CaUserService;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-30
 * Time: 上午11:54
 * To change this template use File | Settings | File Templates.
 */
public class DoTermStatus extends HttpServlet {
    private Logger logger = Logger.getLogger(DoTermStatus.class);
    private CaUserService caUserService;

    /**
     * <p>
     * 在Servlet中注入对象的步骤:
     * 1.取得ServletContext
     * 2.利用Spring的工具类WebApplicationContextUtils得到WebApplicationContext
     * 3.WebApplicationContext就是一个BeanFactory,其中就有一个getBean方法
     * 4.有了这个方法就可像平常一样为所欲为了,哈哈!
     * </p>
     */
    @Override
    public void init() throws ServletException {
        super.init();

        ServletContext servletContext = this.getServletContext();

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        caUserService = (CaUserService) ctx.getBean("caUserService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type","text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String query_string = request.getQueryString();
        String cn = request.getHeader("cn");
        if (cn != null && cn != "") {
            cn = new String(Base64.decodeBase64(cn.getBytes("GBK")));
        }
        String opername = null;
        String command = null;
        String q_cn = null;
        String policeno = null;
        String[] params = null;
        if (query_string != null) {
            if (query_string.contains("&")) {
                params = query_string.split("&");
                for (String p : params) {
                    if (p.startsWith("opername")) {
                        opername = p.substring(p.indexOf("=") + 1, p.length());
                    } else if (p.startsWith("command")) {
                        command = p.substring(p.indexOf("=") + 1, p.length());
                    } else if (p.startsWith("cn")) {
                        q_cn = p.substring(p.indexOf("=") + 1, p.length());
                    } else if (p.startsWith("policeno")) {
                        policeno = p.substring(p.indexOf("=") + 1, p.length());
                    }
                }
            }
        }
        if(q_cn!=null){
            q_cn =new String(Base64.decodeBase64(q_cn.getBytes("GBK")));
        }
        if(policeno!=null){
            policeno =new String(Base64.decodeBase64(policeno.getBytes("GBK")));
        }

        if(cn.contains(" ")){
            cn = cn.replace(" ","_");
        }

        if (command != null) {
            if (command.equals("block")) {
                CaUser user = null;
                try {
                    user = caUserService.findByCn(cn);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                if (user != null) {
                    user.setStatus(1);
                    boolean flag = false;
                    try {
                        flag = caUserService.sleepRaUser(user);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    if (flag) {
                        response.setStatus(200);
                        logger.info( "阻断用户访问成功!");
                    } else {
                        response.setStatus(404);
                        logger.info( "阻断用户访问失败!");
                    }
                } else {
                    response.setStatus(404);
                    logger.info( "阻断用户访问失败!");
                }
            } else if (command.equals("noblock")) {
                CaUser user = null;
                try {

                    user = caUserService.findByCn(cn);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                if (user != null) {
                    if (user.getStatus() == 1) {
                        user.setStatus(0);
                        boolean flag = false;
                        try {
                            caUserService.reCastRaUser(user);
                        } catch (Exception e) {
                            logger.info(e.getMessage());
                        }
                        if (flag) {
                            response.setStatus(200);
                            logger.info( "恢复用户访问成功!");
                        }
                    }
                } else {
                    response.setStatus(404);
                    logger.info( "恢复用户访问失败!");
                }
            }
        }
    }
}
