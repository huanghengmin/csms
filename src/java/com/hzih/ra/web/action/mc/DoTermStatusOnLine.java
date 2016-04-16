package com.hzih.ra.web.action.mc;

import com.hzih.ra.domain.CaUser;
import com.hzih.ra.service.CaUserService;
import com.hzih.ra.service.DistrictService;
import com.hzih.ra.web.action.ra.TerminalAction;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-23
 * Time: 下午1:30
 * To change this template use File | Settings | File Templates.
 */
public class DoTermStatusOnLine extends ActionSupport {
    private Logger logger = Logger.getLogger(DoTermStatusOnLine.class); 

    private CaUserService caUserService;

    private DistrictService districtService;

    public DistrictService getDistrictService() {
        return districtService;
    }

    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }

    public CaUserService getCaUserService() {
        return caUserService;
    }

    public void setCaUserService(CaUserService caUserService) {
        this.caUserService = caUserService;
    }

    public String online()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        StringBuilder json = new StringBuilder();
        String command = request.getHeader("command");
        String beginno = request.getHeader("beginno");
        String endno = request.getHeader("endno");
        String pagesize = request.getHeader("pagesize");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        if(command.equals("onlinevpn")){
            Set<String> key = null;
            if(TerminalAction.on_User_Map!=null){
                key = TerminalAction.on_User_Map.keySet();
                for (Iterator it = key.iterator(); it.hasNext();) {
                    String s = (String) it.next();
                    CaUser value = TerminalAction.on_User_Map.get(s);
                    String serialnumber = value.getHzihcaserialNumber();
                    CaUser u = caUserService.findBySerialNumber(serialnumber);
                    if(u!=null){
                        //以前在线用户数据
                        /*  {status:'1',      //在线状态
                             ifcancel:false,  //是否吊销
                             ip:'171.168.1.1',//终端ip
                             ifblock:false,   //是否阻断
                             cardtype:'TF卡', //卡类型
                             cardmodel:'型号',//卡型号
                             cardver:'3.0',   //卡版本
                             policecate:'1',  //警种
                             policeno:'654321',//警号
                             policename:'李四',//警名
                             idno:'222222222222222222',//身份证
                             org:'静安区公安局',       //组织
                             depart:'交警大队',        //机构
                             region:'上海市',          //省份
                             logindate:'2012-04-12 08:00:21',  //登陆时间
                             onlinetime:'17:33:22',            //在线时间
                             createdate:'2012-04-12 08:00:21'  //构建时间
                             },
                        */
                        json .append("{");
//                        json .append("cn:'" + u.getCn()+"'" ).append(",");
                        json .append("status:'1'"); //在线
                        if(u.getHzihcastatus().equals("4"))
                            json .append(",ifcancel:'" +"true" + "'" );
                        else
                            json .append(",ifcancel:'" +"false" + "'" );
                        json .append(",ip:'" +request.getRemoteAddr()+ "'" );
                        if(u.getStatus()==1)
                            json .append(",ifblock:'" +"true" + "'" );
                        else
                            json .append(",ifblock:'" +"false" + "'" );
                        json .append(",cardtype:'" +"001" + "'" );
                        json .append(",cardmodel:'" +"200" + "'" );
                        json .append(",cardver:'" +"3.0" + "'" );
                        json .append(",policecate:'" +"1" + "'" );
                        json .append(",policeno:'" +u.getHzihjobnumber() + "'" );
                        json .append(",policename:'" +u.getCn() + "'" );
                        json .append(",idno:'" +u.getHzihid() + "'" );
                        if(null!=u.getHzihcity())
                            try{
                                 json .append(",org:'" + districtService.findById(u.getHzihcity()).getDistrictName()+"公安局"+ "'" );
                            }catch (Exception e){
                                logger.error(e.getMessage());
                            }
                        else {
                            json .append(",org:''" );
                        }
                        json .append(",depart:'" +u.getHzihinstitutions()+ "'" );
                        if(null!=u.getHzihprovince())
                            try{
                                 json .append(",region:'" +districtService.findById(u.getHzihprovince()).getDistrictName()+ "'" );
                            }catch (Exception e){
                                logger.error(e.getMessage());
                            }
                        else
                            json .append(",region:''" );
                        if(u.getLogindate()!=null){
                            json .append(",logindate:'" +dateFormat.format(u.getLogindate())+ "'" );
                        }else {
                            json .append(",logindate:''" );
                        }
                        if(u.getOnlinetime()!=null){
                            json .append(",onlinetime:'" +timeFormat.format(u.getOnlinetime())+ "'" );
                        }else {
                            json .append(",onlinetime:''" );
                        }
                        if(u.getCreatedate()!=null){
                            json .append(",createdate:'" +dateFormat.format(u.getCreatedate())+ "'" );
                        }else {
                            json .append(",createdate:''" );
                        }
                        json .append("}");
                        json.append(",");
                    }else {
                        json .append("{");
//                        json .append("cn:'" + value.getCn()+"'" ).append(",");
                        json .append("status:'1'");
                        //以前在线用户数据
                        if(null!=u.getHzihcastatus()&&u.getHzihcastatus().equals("4"))
                            json .append(",ifcancel:'" +"true" + "'" );
                        else
                            json .append(",ifcancel:'" +"false" + "'" );
                        json .append(",ip:'" +request.getRemoteAddr() + "'" );
                        if(value.getStatus()==1){
                            json .append(",ifblock:'" +"true" + "'" );
                        } else {
                            json .append(",ifblock:'" +"false" + "'" );
                        }
                        json .append(",cardtype:'" +"001" + "'" );
                        json .append(",cardmodel:'" +"200" + "'" );
                        json .append(",cardver:'" +"3.0" + "'" );
                        json .append(",policecate:'" +"1" + "'" );
                        json .append(",policeno:'" +value.getHzihjobnumber() + "'" );
                        json .append(",policename:'" +value.getCn() + "'" );
                        json .append(",idno:'" +value.getHzihid() + "'" );
//                        if(null!=u.getHzihcity())
//                            try{
//                                json .append(",org:'" + districtService.findById(Long.parseLong(u.getHzihcity())).getDistrictName()+"公安局"+ "'" );
//                            }catch (Exception e){
//                                logger.error(e.getMessage());
//                            }
//                        else {
//                            json .append(",org:''" );
//                        }
                        json .append(",depart:'" +u.getHzihinstitutions()+ "'" );
//                        if(null!=u.getHzihprovince())
//                            try{
//                                json .append(",region:'" +districtService.findById(Long.parseLong(u.getHzihprovince())).getDistrictName()+ "'" );
//                            }catch (Exception e){
//                                 logger.error(e.getMessage());
//                            }
//                        else
//                            json .append(",region:''" );
                        if(u.getLogindate()!=null){
                            json .append(",logindate:'" +dateFormat.format(u.getLogindate())+ "'" );
                        }else {
                            json .append(",logindate:''" );
                        }
                        if(u.getOnlinetime()!=null){
                            json .append(",onlinetime:'" +timeFormat.format(u.getOnlinetime())+ "'" );
                        }else {
                            json .append(",onlinetime:''" );
                        }
                        if(u.getCreatedate()!=null){
                            json .append(",createdate:'" +dateFormat.format(u.getCreatedate())+ "'" );
                        }else {
                            json .append(",createdate:''" );
                        }
                        json .append("}");
                        json.append(",");
                    }
                }
            }
            String sb = "["+json.toString()+"{total:5,beginno:'"+beginno+"',endno:'"+endno+"',pagesize:"+pagesize+"}]";
            writer.write(sb);
            writer.close();
        }else {
            response.setStatus(404);
        }
        return null;
    }

    //检查数据是否为空
    public String checkValue(Object value){
        if(value==null){
            return "";
        }
        return value.toString();
    }
    
        /* "["
             {status:'1',ifcancel:false,ip:'171.168.1.1',ifblock:false,
             cardtype:'TF卡',cardmodel:'型号',cardver:'3.0',policecate:'1',
             policeno:'654321',policename:'李四',idno:'222222222222222222',
             org:'静安区公安局',depart:'交警大队',region:'上海市',logindate:'2012-04-12 08:00:21',
             onlinetime:'17:33:22',createdate:'2012-04-12 08:00:21'},

             {status:'1',ifcancel:false,ip:'171.168.1.1',ifblock:false,
             cardtype:'ukey',   cardmodel:'a型号',cardver:'3.0',policecate:'1',
             policeno:'123654',policename:'sxl',idno:'123456789123465798',
             org:'徐汇区公安局',depart:'刑警大队',region:'重庆市',logindate:'2012-04-19 03:26:22',
             onlinetime:'22:7:21',createdate:'2012-04-19 03:26:22'} ,

            {status:'0',ifcancel:false,ip:'171.168.1.1',ifblock:false,
            cardtype:'类型',cardmodel:'a型号',cardver:'版本',
            policecate:'1',policeno:'123654',policename:'王五',
            idno:'333333333333333333',org:'静安区公安局',
            depart:'刑警大队',region:'上海市',logindate:'2012-04-12 08:01:38',
            onlinetime:'17:32:5',createdate:'2012-04-12 08:01:38'},

            {status:'0',ifcancel:false,ip:'171.168.1.1',ifblock:false,cardtype:'TF卡',
            cardmodel:'型号',cardver:'版本',policecate:'1',policeno:'12345678',
            policename:'测试1',idno:'123456789012345678',org:'徐汇区公安局',
            depart:'刑警大队',region:'上海市',logindate:'2012-04-11 08:43:16',
            onlinetime:'16:50:27',createdate:'2012-04-11 08:43:16'},

            {status:'0',ifcancel:false,ip:'171.168.1.1',ifblock:false,
            cardtype:'TF卡',cardmodel:'a型号',cardver:'版本',policecate:'1',
            policeno:'123456',policename:'张三',idno:'111111111111111111',
            org:'徐汇区公安局',depart:'刑警大队',region:'上海市',logindate:'2012-04-12 07:57:10',
            onlinetime:'17:36:33',createdate:'2012-04-12 07:57:10'},

            {total:5,beginno:'0',endno:'00',pagesize:500}
        ]";*/
}
