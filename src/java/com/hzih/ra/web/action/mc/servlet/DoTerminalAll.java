package com.hzih.ra.web.action.mc.servlet;

import cn.collin.commons.domain.PageResult;
import com.hzih.ra.domain.CaUser;
import com.hzih.ra.service.CaUserService;
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
 * Date: 13-7-16
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
public class DoTerminalAll extends HttpServlet {
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

//        [{
//            terminalName:'test',
//            terminalType:'001',
//            termianlOutlink:'001',
//            termianlos:'001',
//            termianlBand:'001',
//            cardType:'001',
//            cardname:'aa',
//            card_version:'bb',
//            userId:'330781198904050021',
//            userName:'cc',
//            userDepart:'01000',
//            userZone:'110000',
//            policeNumber:'dd',
//            regTime:'2013-06-06 09:52:34',
//            ifcancel:'0',
//            flag:'1'}]






        response.setHeader("content-type","text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        StringBuilder json = new StringBuilder();
        String command = request.getHeader("command");
//        String beginno = request.getHeader("beginno");  //开始页号
//        String endno = request.getHeader("endno");      //结束页号
//        String pagesize = request.getHeader("pagesize");   //页面大小
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        int count = 0;
        if (command.equals("remoteadd")) {
            try {
//                int start_page = Integer.parseInt(beginno);
//                int limit_page = Integer.parseInt(endno);
//                int pageSize = Integer.parseInt(pagesize);
//                int start = start_page * pageSize;
//                int limit = limit_page * pageSize;
                PageResult pageResult = null;
                try {
                    pageResult = caUserService.findByPages(null, null, null, null, null, 0, 10000);
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
//                            if (TerminalAction.on_User_Map != null) {
//                                CaUser sql_user = TerminalAction.on_User_Map.get(u.getHzihcaserialNumber());
//                                if (sql_user != null) {
//                                    json.append("status:'1'"); //在线
//                                } else {
//                                    json.append("status:'0'"); //在线
//                                }
//                            } else {
//                                json.append("status:'0'"); //不在线
//                            }
                            if (u.getHzihcastatus().equals("4"))
                                json.append("ifcancel:'" + "true" + "'");
                            else
                                json.append("ifcancel:'" + "false" + "'");
//                            json.append(",ip:'" + request.getRemoteAddr() + "'");
//                            if (u.getStatus() == 1)
//                                json.append(",ifblock:'" + "true" + "'");
//                            else
//                                json.append(",ifblock:'" + "false" + "'");
//                            if(null!=u.getHzihcaserialNumber()){
//                                json.append(",serialnumber:'"+u.getHzihcaserialNumber()+"'");
//                            }  else {
//                                json.append(",serialnumber:''");
//                            }
                            json.append(",cardType:'" + "TF卡" + "'");
                            json.append(",cardname:'" + "ZD model" + "'");
                            json.append(",card_version:'" + "3.0" + "'");

                            json.append(",userId:'" + u.getHzihid() + "'");
                            json.append(",userName:'" + u.getCn().replace("_"," ")+ "'");
                            json.append(",policeNumber:'" + u.getHzihjobnumber() + "'");

                            json.append(",userDepart:'" + u.getHzihprovince().substring(6,12) + "'");
                            json.append(",userZone:'" + u.getHzihprovince().substring(0,6) + "'");

                            json.append(",terminalName:'test'");
                            json.append(",terminalType:'001'");
                            json.append(",termianlOutlink:'001'");
                            json.append(",termianlos:'001'");
                            json.append(",termianlBand:'001'");

                            json.append(",flag:'1'");

//                            if (null != u.getHzihcity())
//                                json.append(",org:'" + orgcodeDao.findByOrgCode(u.getHzihprovince()).getOrgname() + "'");

//                            json.append(",depart:'" +  orgcodeDao.findByOrgCode(u.getHzihprovince()).getOrgname() + "'");
//                            if (null != u.getHzihprovince())
//                                json.append(",region:'" +districtService.findById(u.getHzihprovince().substring(0,6)).getDistrictName()+ "'");

//                            if (u.getLogindate() != null)
//                                json.append(",logindate:'" + dateFormat.format(u.getLogindate()) + "'");

//                            if (u.getOnlinetime() != null)
//                                json.append(",onlinetime:'" + timeFormat.format(u.getOnlinetime()) + "'");

                            if (u.getCreatedate() != null)
                                json.append(",regTime:'" + dateFormat.format(u.getCreatedate()) + "'");

                            json.append("}");
                            json.append(",");
                        }
                    }
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
        String sb = "[" + json.toString() + "{total:" + count + /*",beginno:'" + beginno + "',endno:'" + endno + "',pagesize:" + pagesize +*/ "}]";
        logger.info(sb.toString());
        writer.write(sb);
        writer.close();
    }
}

