package com.hzih.ra.web.action.mc.servlet;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.dao.OrgcodeDao;
import com.hzih.ra.domain.CaUser;
import com.hzih.ra.service.CaUserService;
import com.hzih.ra.service.DistrictService;
import com.hzih.ra.web.action.ra.TerminalAction;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-30
 * Time: 上午11:54
 * To change this template use File | Settings | File Templates.
 */
public class DoTermStatusAll extends HttpServlet {
    private Logger logger = Logger.getLogger(DoTermStatusAll.class);
    private CaUserService caUserService;
    private OrgcodeDao orgcodeDao;
    private DistrictService districtService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

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
        orgcodeDao = (OrgcodeDao)ctx.getBean("orgcodeDao");
        districtService = (DistrictService)ctx.getBean("districtService");

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type","text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        StringBuilder json = new StringBuilder();
        String command = request.getHeader("command");
        String beginno = request.getHeader("beginno");  //开始页号
        String endno = request.getHeader("endno");      //结束页号
        String pagesize = request.getHeader("pagesize");   //页面大小
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        int count = 0;
        if (command.equals("allvpn")) {
            try {
                int start_page = Integer.parseInt(beginno);
                int limit_page = Integer.parseInt(endno);
                int pageSize = Integer.parseInt(pagesize);
                int start = start_page * pageSize;
                int limit = limit_page * pageSize;
                PageResult pageResult = null;
                try {
                    pageResult = caUserService.findByPages(null, null, null, null, null, start, limit);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                if (pageResult != null) {
                    List<CaUser> list = pageResult.getResults();
                    count = pageResult.getAllResultsAmount();
                    if (list != null) {
                        Iterator<CaUser> raUserIterator = list.iterator();
                        while (raUserIterator.hasNext()) {
                            CaUser u = raUserIterator.next();
                            json.append("{");
                            if (TerminalAction.on_User_Map != null) {
                                CaUser sql_user = TerminalAction.on_User_Map.get(u.getHzihcaserialNumber());
                                if (sql_user != null) {
                                    json.append("status:'1'"); //在线
                                } else {
                                    json.append("status:'0'"); //在线
                                }
                            } else {
                                json.append("status:'0'"); //不在线
                            }
                            if (u.getHzihcastatus().equals("4"))
                                json.append(",ifcancel:'" + "true" + "'");
                            else
                                json.append(",ifcancel:'" + "false" + "'");
                            json.append(",ip:'" + request.getRemoteAddr() + "'");
                            if (u.getStatus() == 1)
                                json.append(",ifblock:'" + "true" + "'");
                            else
                                json.append(",ifblock:'" + "false" + "'");
                            if(null!=u.getHzihcaserialNumber()){
                                json.append(",serialnumber:'"+u.getHzihcaserialNumber()+"'");
                            }  else {
                                json.append(",serialnumber:''");
                            }
                            json.append(",cardtype:'" + "TF卡" + "'");
                            json.append(",cardmodel:'" + "ZD model" + "'");
                            json.append(",cardver:'" + "3.0" + "'");
                            json.append(",policecate:'" + "1" + "'");
                            json.append(",policeno:'" + u.getHzihjobnumber() + "'");
                            json.append(",policename:'" + u.getCn().replace("_"," ")+ "'");
                            json.append(",idno:'" + u.getHzihid() + "'");
                            if (null != u.getHzihcity())
                                json.append(",org:'" + orgcodeDao.findByOrgCode(u.getHzihprovince()).getOrgname() + "'");

                            json.append(",depart:'" +  orgcodeDao.findByOrgCode(u.getHzihprovince()).getOrgname() + "'");
                            if (null != u.getHzihprovince())
                                json.append(",region:'" +districtService.findById(u.getHzihprovince().substring(0,6)).getDistrictName()+ "'");

                            if (u.getLogindate() != null)
                                json.append(",logindate:'" + dateFormat.format(u.getLogindate()) + "'");

                            if (u.getOnlinetime() != null)
                                json.append(",onlinetime:'" + timeFormat.format(u.getOnlinetime()) + "'");

                            if (u.getCreatedate() != null)
                                json.append(",createdate:'" + dateFormat.format(u.getCreatedate()) + "'");

                            json.append("}");
                            json.append(",");
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        String sb = "[" + json.toString() + "{total:" + count + ",beginno:'" + beginno + "',endno:'" + endno + "',pagesize:" + pagesize + "}]";
        logger.info(sb.toString());
        writer.write(sb);
        writer.close();
    }
}
