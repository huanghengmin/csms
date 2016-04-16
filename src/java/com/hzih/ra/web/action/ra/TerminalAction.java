package com.hzih.ra.web.action.ra;

import com.hzih.ra.domain.*;
import com.hzih.ra.entity.AccessUrl;
import com.hzih.ra.entity.BlueTooth;
import com.hzih.ra.entity.Wifi;
import com.hzih.ra.json.JSONUtils;
import com.hzih.ra.service.*;
import com.hzih.ra.syslog.SysLogSend;
import com.hzih.ra.utils.CodeUtil;
import com.hzih.ra.utils.FileUtil;
import com.hzih.ra.utils.StringContext;
import com.hzih.ra.web.action.mc.AbnormalJson;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-2
 * Time: 上午11:12
 * To change this template use File | Settings | File Templates.
 */
public class TerminalAction extends ActionSupport {
    private Logger logger = Logger.getLogger(TerminalAction.class);
    private StopListService  stopListService;
    private AllowListService allowListService;
    private WhiteListService whiteListService;
    private BlackListService blackListService;
    public static Map<String,CaUser> on_User_Map = new LinkedHashMap<String,CaUser>();

    public CaUserService getCaUserService() {
        return caUserService;
    }

    public void setCaUserService(CaUserService caUserService) {
        this.caUserService = caUserService;
    }

    private CaUserService caUserService;

    public WhiteListService getWhiteListService() {
        return whiteListService;
    }

    public void setWhiteListService(WhiteListService whiteListService) {
        this.whiteListService = whiteListService;
    }

    public BlackListService getBlackListService() {
        return blackListService;
    }

    public void setBlackListService(BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    public AllowListService getAllowListService() {
        return allowListService;
    }

    public void setAllowListService(AllowListService allowListService) {
        this.allowListService = allowListService;
    }

    public StopListService getStopListService() {
        return stopListService;
    }

    public void setStopListService(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    //编码转换
    public String if_encode(String params) {
        if(params!=null){
            try {
                return java.net.URLDecoder.decode(params,"utf-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return null;
    }
    //终端连接
    public String connect()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        String code = request.getHeader("code");
        String ca_user = request.getHeader("user");
        String serialnumber = null;
        if(ca_user!=null&&!ca_user.equals("")){
            JSONObject ca_user_data =  JSONUtils.toJSONObject(if_encode(ca_user));
            serialnumber = ca_user_data.get("serialnumber").toString();
            if(serialnumber!=null&&!serialnumber.equals("")){
//                CaUser import_ca_user = new CaUser();
//                import_ca_user.setHzihphone(checkValue(ca_user_data.get("phone").toString()));
//                import_ca_user.setPhonenetid(checkValue(ca_user_data.get("phonenetid").toString()));
//                import_ca_user.setHzihcaserialNumber(serialnumber);
//                import_ca_user.setHzihprovince(checkValue(ca_user_data.get("province").toString()));
//                import_ca_user.setHzihcity(checkValue(ca_user_data.get("city").toString()));
//                import_ca_user.setHzihorganization(checkValue(ca_user_data.get("organization").toString()));
//                import_ca_user.setHzihinstitutions(checkValue(ca_user_data.get("institutions").toString()));
//                import_ca_user.setHzihid(checkValue(ca_user_data.get("userid").toString()));
//                import_ca_user.setCn(checkValue(ca_user_data.get("username").toString()));
//                import_ca_user.setTerminalid(checkValue(ca_user_data.get("deviceid").toString()));
//                import_ca_user.setOnlinetime(new Date());
                if(on_User_Map!=null){
                    CaUser online_user= on_User_Map.get(serialnumber);
                    if(online_user!=null){
//                        online_user.setHzihphone(checkValue(ca_user_data.get("phone").toString()));
//                        online_user.setPhonenetid(checkValue(ca_user_data.get("phonenetid").toString()));
//                        online_user.setHzihcaserialNumber(serialnumber);
//                        online_user.setHzihprovince(checkValue(ca_user_data.get("province").toString()));
//                        online_user.setHzihcity(checkValue(ca_user_data.get("city").toString()));
//                        online_user.setHzihorganization(checkValue(ca_user_data.get("organization").toString()));
//                        online_user.setHzihinstitutions(checkValue(ca_user_data.get("institutions").toString()));
//                        online_user.setHzihid(checkValue(ca_user_data.get("userid").toString()));
//                        online_user.setCn(checkValue(ca_user_data.get("username").toString()));
//                        online_user.setTerminalid(checkValue(ca_user_data.get("deviceid").toString()));
//                        online_user.setOnlinetime(new Date());
                        CaUser sql_user =caUserService.findBySerialNumber(serialnumber);
                        if(sql_user!=null){
                            sql_user.setOnlinetime(new Date());
                            caUserService.modify(sql_user);
                            on_User_Map.put(serialnumber,sql_user);
                            //                        上线率日志发送
//                            sendOnLineRate(sql_user);
                            //机卡分离and BS访问
//                            checkOutCard_Bs(sql_user);
                        }
                    }  else {
                        CaUser sql_user =caUserService.findBySerialNumber(serialnumber);
                        if(sql_user!=null){
                            sql_user.setLogindate(new Date());
                            sql_user.setOnlinetime(new Date());
                            caUserService.modify(sql_user);

                            on_User_Map.put(serialnumber,sql_user);
                            //                        上线率日志发送
                            sendOnLineRate(sql_user);
                            //机卡分离and BS访问
                            checkOutCard_Bs(sql_user);
                        }
                    }
                }else {
                    on_User_Map = new LinkedHashMap<String,CaUser>();
                    CaUser sql_user =caUserService.findBySerialNumber(serialnumber);
                    if(sql_user!=null){
                        sql_user.setLogindate(new Date());
                        sql_user.setOnlinetime(new Date());
                        caUserService.modify(sql_user);
                        on_User_Map.put(serialnumber,sql_user);
                        //上线率日志发送
                        sendOnLineRate(sql_user);
                        //机卡分离and BS访问
                        checkOutCard_Bs(sql_user);
                    }
                }
            }
        }
        if(code!=null){
        String json=null;
        switch (code){
            case   CodeUtil.GET_VERSION:
                String toSrc = ServletActionContext.getServletContext().getRealPath("/android") + "/tcandroid/" +"version.txt";
                File file = new File(toSrc);
                StringBuilder stringBuilder =null;
                if(file.exists()){
                    InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                    BufferedReader bufferedReader = new BufferedReader(read);
                    stringBuilder = new StringBuilder();
                    String line =null;
                    while ((line= bufferedReader.readLine()) != null) {
                       stringBuilder.append(line);
                   }
                read.close();
                }else{
                    logger.info("android version 文件不存在!");
                }
                if(stringBuilder!=null){
                    json = CodeUtil.SUCCESS+",{"+stringBuilder.toString()+"}";
                }
                break;

            case   CodeUtil.DOWNLOAD_APK:
                String apk = ServletActionContext.getServletContext().getRealPath("/android") + "/tcandroid/" +"tcandroid.apk";
                File apk_file = new File(apk);
                if(apk_file.exists()){
                    response = FileUtil.copy(apk_file, response);
                    logger.info("下载文件成功!");
                }
                break;

            case  CodeUtil.CONNECT:
                json = CodeUtil.SUCCESS;
                break;


            case CodeUtil.CONFIG:
                int totalCount = 0;
                String s = getData();
                totalCount = totalCount+1;
                StringBuilder sb = new StringBuilder("{totalCount:"+totalCount+",root:[");
                sb.append(s.toString().substring(0,s.length()-1));
                sb.append("]}");
                json = CodeUtil.SUCCESS +"," + sb.toString();
                break;


            //获取违规记录
            case CodeUtil.VIOLATION:
                StringBuilder builder = new StringBuilder();
                if(on_User_Map!=null){
                    if(serialnumber!=null){
                        CaUser user1 = on_User_Map.get(serialnumber);
                        if(null!=user1.getAbnormalFlag()&&!user1.getAbnormalFlag().equals("")){
                            if(user1.getAbnormalFlag().equals("true")){
                                builder.append("{");
                                builder.append("abnormalFlag:'true',");
                                builder.append("message:'"+user1.getAbnormalMessage()+"'");
                                builder.append("}");
                            } else {
                                builder.append("{");
                                builder.append("abnormalFlag:'false',");
                                builder.append("message:'此用户暂时没有违规!'");
                                builder.append("}");
                            }
                        } else {
                            builder.append("{");
                            builder.append("abnormalFlag:'false',");
                            builder.append("message:'此用户暂时没有违规!'");
                            builder.append("}");
                        }
                    }
                }
               json = CodeUtil.SUCCESS +","+builder.toString();
               break;


            //获取是否要截屏
            case CodeUtil.VIEW:
                if(serialnumber!=null){
                    if(on_User_Map!=null){
                        if(serialnumber!=null){
                            CaUser user1 = on_User_Map.get(serialnumber);
                            if(user1.isViewFlag()){
                                json = CodeUtil.VIEW_TRUE;
                            }else {
                                json = CodeUtil.VIEW_FALSE;
                            }
                        }
                    }
                }
                break;

            case CodeUtil.UPDATE_VIEW:
                InputStream in = request.getInputStream();
                String type =request.getHeader("type");
                if(serialnumber!=null){
                    if(on_User_Map!=null){
                        if(serialnumber!=null){
                            CaUser user1 = on_User_Map.get(serialnumber);
                            if(user1.isViewFlag()){
                                user1.setViewFlag(false);
                                on_User_Map.put(serialnumber,user1);
                            } else {
                                user1.setViewFlag(false);
                                on_User_Map.put(serialnumber,user1);
                            }
                        }
                    }
                }
                //图片写到指定地址
                if(in!=null){
                    writeInputStreamToFile(in,serialnumber,type);
                }
                break;
            //获取是否上传地理位置
            case CodeUtil.LOCATION:
                if(serialnumber!=null){
                    if(on_User_Map!=null){
                        if(serialnumber!=null){
                            CaUser user1 = on_User_Map.get(serialnumber);
                            if(user1.isLocation()){
                                json = CodeUtil.LOCATION_TRUE;
                            }else {
                                json = CodeUtil.LOCATION_FALSE;
                            }
                        }
                    }
                }
                break;


            case CodeUtil.UPDATE_LOCATION:
                String l_data = request.getHeader("data");
                String decrypt_data =if_encode(l_data);
                if(serialnumber!=null){
                    if(on_User_Map!=null){
                        if(serialnumber!=null){
                            CaUser user1 = on_User_Map.get(serialnumber);
                            if(user1.isLocation()){
                                user1.setLocationstr(decrypt_data.substring(1,decrypt_data.length()-1));
                                on_User_Map.put(serialnumber,user1);
                            } else {
                                user1.setLocationstr(decrypt_data.substring(1,decrypt_data.length()-1));
                                on_User_Map.put(serialnumber,user1);
                            }
                        }
                    }
                }
                break;
            //获取白名单进程
            case CodeUtil.ALLOW_PROCESS:
                StringBuilder buer = new StringBuilder();
                List<AllowList> terminalApps = allowListService.getStopProcess();
                JSONArray jsonArray = null;
                if(terminalApps!=null&&terminalApps.size()>0){
                    jsonArray = JSONArray.fromObject(terminalApps);
                }
                if(jsonArray!=null){
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject jo = (JSONObject) jsonArray.get(i);
                        buer.append("{processId:'"+jo.get("processId")+"'}");
                        if(i!=jsonArray.size()-1){
                            buer.append(",");
                        }
                    }
                }
                String ss= "["+buer.toString()+"]";
                json = CodeUtil.SUCCESS+","+URLEncoder.encode(ss,"utf-8");
                break;


            //获取黑名单进程
            case  CodeUtil.STOP_PROCESS:
                StringBuilder buer_one = new StringBuilder("");
                List<StopList> terminalApps_one = stopListService.getStopProcess();
                JSONArray jsonArray_one = null;
                if(terminalApps_one!=null&&terminalApps_one.size()>0){
                    jsonArray_one = JSONArray.fromObject(terminalApps_one);
                }
                if(jsonArray_one!=null){
                    for (int i = 0; i < jsonArray_one.size(); i++) {
                        JSONObject jo = (JSONObject) jsonArray_one.get(i);
                        buer_one.append("{processId:'"+jo.get("processId")+"'}");
                        if(i!=jsonArray_one.size()-1){
                            buer_one.append(",");
                        }
                    }
                }
                String ss_one= "["+buer_one.toString()+"]";
                json = CodeUtil.SUCCESS+","+URLEncoder.encode(ss_one,"utf-8");
                break;


            //获取黑白名单进程
            case CodeUtil.A_S_PROCESS:
                StringBuilder buer_two = new StringBuilder();
                List<StopList> terminalApps_two = stopListService.getStopProcess();
                List<AllowList> allowApps_two  = allowListService.getStopProcess();
                JSONArray jsonArray_two = null;
                if(terminalApps_two!=null&&terminalApps_two.size()>0){
                    jsonArray_two = JSONArray.fromObject(terminalApps_two);
                }
                if(jsonArray_two!=null){
                    for (int i = 0; i < jsonArray_two.size(); i++) {
                        JSONObject jo = (JSONObject) jsonArray_two.get(i);
                        buer_two.append("{processId:'"+jo.get("processId")+"'}");
                        if(i!=jsonArray_two.size()-1){
                            buer_two.append(",");
                        }
                    }
                }
                if(allowApps_two!=null&&allowApps_two.size()>0){
                    JSONArray allow_two = null;
                    if(allowApps_two!=null&&allowApps_two.size()>0){
                        allow_two = JSONArray.fromObject(allowApps_two);
                        buer_two.append(",");
                    }
                    for (int i = 0; i < allow_two.size(); i++) {
                        JSONObject jo = (JSONObject) allow_two.get(i);
                        buer_two.append("{processId:'"+jo.get("processId")+"'}");
                        if(i!=jsonArray_two.size()-1){
                            buer_two.append(",");
                        }
                    }
                }
                String ss_two= "["+buer_two.toString()+"]";
                json = CodeUtil.SUCCESS+","+URLEncoder.encode(ss_two,"utf-8");
                break;

            case  CodeUtil.SET_PWD:
                if(serialnumber!=null){
                    String data = request.getHeader("data");
                    CaUser user1 =  caUserService.findBySerialNumber(serialnumber);
                    if(user1!=null){
                        user1.setTerminal_pwd(data);
                        try{
                            caUserService.modify(user1);
                            json = CodeUtil.AUDIT_PWD_SUCCESS+",{"+"设置密码完成!}";
                        }catch (Exception e){
                            logger.info(e.getMessage());
                        }
                    }else {
                        json = CodeUtil.AUDIT_PWD_FAIL+",{"+"服务器找不到此用户}";
                    }
                }
            break;
            
            //重置密码
            case CodeUtil.GET_PWD:
                if(serialnumber!=null){
                    CaUser user1 =  caUserService.findBySerialNumber(serialnumber);
                    if(user1!=null){
                        String pwd_audit = user1.getTerminal_pwd_audit();
                        if(pwd_audit.equals("0")){
                             user1.setTerminal_pwd_audit("1");
                            try{
                             caUserService.modify(user1);
                            }catch (Exception e){
                                logger.info("更新状态失败!");
                            }
                            json = CodeUtil.SUCCESS;
                        } else if(pwd_audit.equals("1")){
                            json = CodeUtil.AUDIT_PWD_ING+",{重置密码审批中!}";
                        } else if(pwd_audit.equals("2")) {
                            json = CodeUtil.AUDIT_PWD_SUCCESS+",{"+user1.getTerminal_pwd()+"}";
                        } else {
                            json = CodeUtil.AUDIT_PWD_FAIL+",{重置密码出错!}";
                        }
                    }else {
                        json = CodeUtil.AUDIT_PWD_FAIL+",{服务器找不到此用户!}";
                    }
                }else {
                    json = CodeUtil.AUDIT_PWD_FAIL+",{服务器找不到此用户!}";
                }
            break;

            case CodeUtil.GET_PWD_END:
                if(serialnumber!=null){
                    CaUser user1 =  caUserService.findBySerialNumber(serialnumber);
                    if(user1!=null){
                        String pwd_audit = user1.getTerminal_pwd_audit();
                        if(pwd_audit.equals("2")){
                            user1.setTerminal_pwd_audit("0");
                            try{
                                caUserService.modify(user1);
                            }catch (Exception e){
                                logger.info("更新状态失败!");
                            }
                            json = CodeUtil.SUCCESS;
                        }
                   }else {
                        json = CodeUtil.AUDIT_PWD_FAIL+",{服务器找不到此用户!}";
                    }
                }else {
                    json = CodeUtil.AUDIT_PWD_FAIL+",{服务器找不到此用户!}";
                }
                break;


            //上报访问记录
            case CodeUtil.REPORT_URL:
                String data = request.getHeader("data");
                String access_url =if_encode(data);
                //url访问列表
                List<AccessUrl> accessUrls = null;
                if(access_url!=null&&!access_url.equals("")){
                    accessUrls  = JSONUtils.toList(access_url, AccessUrl.class);
                }
                if(accessUrls!=null){
                    if(on_User_Map!=null){
                        if(serialnumber!=null){
                            CaUser user1 = on_User_Map.get(serialnumber);
                            user1.setAccessUrls(accessUrls);
                            //非法外连
                            checkOutIllegal(user1);
                            on_User_Map.put(serialnumber,user1);
                        }
                    }
                }
                json = CodeUtil.SUCCESS;
                break;


            //上报wifi违规
            case CodeUtil.REPORT_WIFI:
                //wifi 打开详细
                String wifi = request.getHeader("data");
                //wifi操作列表
                List<Wifi> wifis = null;
                if(wifi!=null&&!wifi.equals("")){
                    wifis  = JSONUtils.toList(wifi, Wifi.class);
                }
                if(wifis!=null){
                        if(on_User_Map!=null){
                            if(serialnumber!=null){
                                CaUser user1 = on_User_Map.get(serialnumber);
                                user1.setWifis(wifis);
                                on_User_Map.put(serialnumber,user1);
                                user1.setLogFlag("WIFILG");
                                user1.setWifi_msg("用户"+user1.getCn()+"无线尝试连接次数超限!");
                                user1.setMsg("用户"+user1.getCn()+"无线尝试连接次数超限!");
                                String m_json = AbnormalJson.WIFILG_json(user1);
                                logger.info("发送无线尝试连接次数超限日志"+m_json);
                                SysLogSend.sysLog(m_json);
                            }
                        }
                }
                json = CodeUtil.SUCCESS;
                break;


            //上报蓝牙违规
            case CodeUtil.REPORT_BLUE_TOOTH:
                String blueTooth = request.getHeader("data");
                //蓝牙操作列表
                List<BlueTooth> blueTooths = null;
                if(blueTooth!=null&&!blueTooth.equals("")){
                    blueTooths  = JSONUtils.toList(blueTooth, BlueTooth.class);
                }
                if(blueTooths!=null){
                    if(on_User_Map!=null){
                        if(serialnumber!=null){
                            CaUser user1 = on_User_Map.get(serialnumber);
                            user1.setBlueTooths(blueTooths);
                            on_User_Map.put(serialnumber,user1);
                            //蓝牙
                            user1.setLogFlag("TOOTHLG");
                            user1.setBooth_msg("用户"+user1.getCn()+"蓝牙尝试连接次数超限!");
                            user1.setMsg("用户"+user1.getCn()+"蓝牙尝试连接次数超限!");
                            String m_json = AbnormalJson.TOOTHLG_json(user1);
                            logger.info("发送蓝牙尝试连接次数超限日志"+m_json);
                            SysLogSend.sysLog(m_json);
                        }
                    }
                }
                json = CodeUtil.SUCCESS;
                break;


            //上报终端运行进程
            case CodeUtil.REPORT_PROCESS:
                String data_one = request.getHeader("data");     //在线终端进程
                String process =if_encode(data_one);
                //进程
                List<StopList> processes = null;
                if(process!=null&&!process.equals("")){
                    processes = JSONUtils.toList(process, StopList.class);
                }
                if(processes!=null){
                    if(on_User_Map!=null){
                        if(serialnumber!=null){
                            CaUser user1 = on_User_Map.get(serialnumber);
                            user1.setRunProcesses(processes);
                            //非法进程访问
                            blackProcessIllegal(user1);
                            on_User_Map.put(serialnumber,user1);
                        }
                    }
                }
                json = CodeUtil.SUCCESS;
                break;
            }
        if(json!=null){
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.close();
        }
    }
        return null;
    }
    //更新重置密码状态
    public String updateAuditPwdStatus() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        String json = "{success:false}";
        PrintWriter writer = response.getWriter();
        String serialnumber = request.getParameter("serialnumber");
        if(serialnumber!=null){
            CaUser user1 = caUserService.findBySerialNumber(serialnumber);
            if(user1!=null){
                user1.setTerminal_pwd_audit("2");
                caUserService.modify(user1);
                json = "{success:true}";
            }
        }
        writer.write(json);
        return null;
    }
    //得到终端上报配置信息
    private String getData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("wifi_num:'"+ getAttribute("wifi_num")+"',");
        stringBuilder.append("booth_num:'"+getAttribute("booth_num")+"',");
        stringBuilder.append("up_time:'"+getAttribute("up_time")+"',");
        stringBuilder.append("clear_time:'"+getAttribute("clear_time")+"',");
        stringBuilder.append("disable_wifi:'"+getAttribute("disable_wifi")+"',");
        stringBuilder.append("disable_booth:'"+getAttribute("disable_booth")+"',");
        stringBuilder.append("allow_process:'"+getAttribute("allow_process")+"',");
        stringBuilder.append("stop_process:'"+getAttribute("stop_process")+"'");
        stringBuilder.append("},");
        return stringBuilder.toString();
    }

    private String getAttribute(String elementName){
        String xml = StringContext.systemPath+ "/config/android_config.xml";
        Document document = buildFromFile(xml);
        Element root =  document.getRootElement();
        Element child = root.getChild(elementName);
        return child.getText();
    }

    private Document buildFromFile(String filePath) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new File(filePath));
            return anotherDocument;
        } catch (JDOMException e) {
            logger.error(e.getMessage());
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    //接收郭达望发送过来的违规超过上限信息
    public String abnormal()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String message = request.getParameter("message");
        String cn = request.getParameter("cn");
        CaUser caUser = caUserService.findByCn(cn);
        if(null!=caUser){
            String serialnumber = caUser.getHzihcaserialNumber();
            if(on_User_Map!=null){
                if(serialnumber!=null){
                    CaUser user1 = on_User_Map.get(serialnumber);
                    user1.setAbnormalFlag("true");
                    user1.setAbnormalMessage(message);
                    on_User_Map.put(serialnumber,user1);
                }
            }
        }
        writer.write("{success:true}");
        return null;
    }

    //图片写到指定地址
    public void writeInputStreamToFile(InputStream in,String serialnumber, String type) throws FileNotFoundException {
        String toSrc = ServletActionContext.getServletContext().getRealPath("/upload/"+serialnumber);
        File folder = new File(toSrc);
        if(!folder.exists()){
            folder.mkdirs();
        }
        Date date = new Date();
        logger.info(toSrc + "/" + date.getTime() + type);
        File file = new File(toSrc + "/" + date.getTime() + type);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return;
            }
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(in);
        int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        try {
            while ((size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                fos.close();
                in.close();
            } catch (IOException e) {
                return;
            }
        }
    }

    //查看图片
    public String viewPhoto()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String serialnumber = request.getParameter("serialnumber");
        String json = "{success:false}";
        if(serialnumber!=null){
            String toSrc = ServletActionContext.getServletContext().getRealPath("/upload/"+serialnumber);
            File dir = new File(toSrc);
            File file_file=null;
            long max = 0;
            String type = "jpg";
            File file[] = dir.listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()){
                    String name = file[i].getName();
                    if(name.contains(".")){
                        String num = name.substring(0,name.lastIndexOf("."));
                        max = Long.parseLong(num);
                        file_file = file[i];
                    }
                }
            }
            if(null!=file_file){
                for (int i = 0; i < file.length; i++) {
                    if (file[i]!=file_file){
                           file[i].delete();
                    }
                }
            }
            if(max!=0){
                json = "{success:true,url:'"+"/upload/"+serialnumber+"/"+max+"."+type+"'}";
            }
        }
        writer.write(json);
        writer.close();
        return null;
    }

    //返回在线用户列表
    public String onLineUser()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        StringBuilder json = new StringBuilder();
        Set<String> key = null;
        int count = 0;
         if(on_User_Map!=null){
            key = on_User_Map.keySet();
            for (Iterator it = key.iterator(); it.hasNext();) {
                String s = (String) it.next();
                CaUser value = on_User_Map.get(s);
                String serialnumber = value.getHzihcaserialNumber();
                CaUser u = caUserService.findBySerialNumber(serialnumber);
                if(u!=null){
                    json .append("{");
//                    json .append("id:'"+u.getId()+"'");
                    json .append("cn:'" + checkValue(u.getCn())+"'" );
                    json .append(",hzihpassword:'" + checkValue(u.getHzihpassword())+"'");
                    json .append(",hzihid:'" + checkValue(u.getHzihid())+"'" );
                    json .append(",hzihphone:'" + checkValue(u.getHzihphone())+"'");
                    json .append(",hzihaddress:'" + checkValue(u.getHzihaddress())+"'" );
                    json .append(",hzihemail:'" + checkValue(u.getHzihemail())+"'" );
                    json .append(",hzihjobnumber:'" + checkValue(u.getHzihjobnumber())+"'" );
                    json .append(",phonenetid:'" + checkValue(u.getPhonenetid())+"'");
                    json .append(",hzihcaserialNumber:'" + checkValue(u.getHzihcaserialNumber())+"'" );
                    json .append(",terminalid:'" + checkValue(u.getTerminalid())+"'" );
                    json .append(",terminal_pwd:'" + checkValue(u.getTerminal_pwd())+"'" );
                    json .append(",terminal_pwd_audit:'" + checkValue(u.getTerminal_pwd_audit())+"'");
                    json .append(",hzihdn:'" + checkValue(u.getHzihdn())+"'" );
                    json .append(",hzihprovince:'" + checkValue(u.getHzihprovince())+"'" );
                    json .append(",hzihcity:'" + checkValue(u.getHzihcity())+"'");
                    json .append(",hzihorganization:'" + checkValue(u.getHzihorganization())+"'" );
                    json .append(",hzihinstitutions:'" + checkValue(u.getHzihinstitutions())+"'");
                    json .append(",hzihcastatus:'" + checkValue(u.getHzihcastatus())+"'" );
                    json .append(",hzihparentca:'" + checkValue(u.getHzihparentca())+"'" );
                    json .append(",status:'" + checkValue(u.getStatus()) +"'");
                    json .append(",location:'" + checkValue(u.getLocationstr()) +"'");
                    json .append(",hzihcertificatetype:'" + checkValue(u.getHzihcertificatetype()) + "'" );
                    json .append("}");
                    if(it.hasNext()){
                        json.append(",");
                    }
                }else {
                    json .append("{");
//                    json .append("id:'"+checkValue(value.getId())+"'");
                    json .append("cn:'" + checkValue(value.getCn())+"'" );
                    json .append(",hzihpassword:'" + checkValue(value.getHzihpassword())+"'" );
                    json .append(",hzihid:'" + checkValue(value.getHzihid())+"'" );
                    json .append(",hzihphone:'" + checkValue(value.getHzihphone())+"'" );
                    json .append(",hzihaddress:'" + checkValue(value.getHzihaddress())+"'" );
                    json .append(",hzihemail:'" + checkValue(value.getHzihemail())+"'" );
                    json .append(",hzihjobnumber:'" + checkValue(value.getHzihjobnumber())+"'" );
                    json .append(",phonenetid:'" + checkValue(value.getPhonenetid())+"'");
                    json .append(",hzihcaserialNumber:'" + checkValue(value.getHzihcaserialNumber())+"'" );
                    json .append(",terminalid:'" + checkValue(value.getTerminalid())+"'" );
                    json .append(",terminal_pwd:'" +checkValue( value.getTerminal_pwd())+"'" );
                    json .append(",terminal_pwd_audit:'" + checkValue(value.getTerminal_pwd_audit())+"'");
                    json .append(",hzihdn:'" + checkValue(value.getHzihdn())+"'" );
                    json .append(",hzihprovince:'" + checkValue(value.getHzihprovince())+"'" );
                    json .append(",hzihcity:'" +checkValue( value.getHzihcity())+"'");
                    json .append(",hzihorganization:'" + checkValue(value.getHzihorganization())+"'" );
                    json .append(",hzihinstitutions:'" + checkValue(value.getHzihinstitutions())+"'");
                    json .append(",hzihcastatus:'" + checkValue(value.getHzihcastatus())+"'" );
                    json .append(",hzihparentca:'" + checkValue(value.getHzihparentca())+"'" );
                    json .append(",status:'" + checkValue(value.getStatus())+"'" );
                    json .append(",location:'" + checkValue(value.getLocationstr()) +"'");
                    json .append(",hzihcertificatetype:'" + checkValue(value.getHzihcertificatetype()) + "'" );
                    json .append("}");
                    if(it.hasNext()){
                        json.append(",");
                    }
                }
            }
         }
        if(key!=null){
            count = key.size();
        }
        String sb = "{success:true,total:" + count + ",rows:["+json.toString()+"]}";
        writer.write(sb);
        writer.close();
        return null;
    }

    //检查数据是否为空
    public String checkValue(Object value){
        if(value==null){
            return "";
        }
        return value.toString();
    }
    
    //android更新截屏
    public String updateView()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String serialnumber = request.getParameter("serialnumber");
        String json = "{success:false}";
        if(serialnumber!=null){
            CaUser user = on_User_Map.get(serialnumber);
            if(user!=null) {
                if(!user.isViewFlag()){
                    user.setViewFlag(true);
                    on_User_Map.put(serialnumber,user);
                }
                json="{success:true}";
            }
        }
        writer.write(json);
        writer.close();
        return null;
    }

    public String location()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String serialnumber = request.getParameter("serialnumber");
        String json = "{success:false}";
        if(serialnumber!=null){
            CaUser user = on_User_Map.get(serialnumber);
            if(user!=null) {
                if(!user.isLocation()){
                    user.setLocation(true);
                    on_User_Map.put(serialnumber,user);
                }
                json="{success:true}";
            }
        }
        writer.write(json);
        writer.close();
        return null;
    }

    public String closeLocation()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String serialnumber = request.getParameter("serialnumber");
        String json = "{success:false}";
        if(serialnumber!=null){
            CaUser user = on_User_Map.get(serialnumber);
            if(user!=null) {
                if(user.isLocation()){
                    user.setLocation(false);
                    on_User_Map.put(serialnumber,user);
                }
                json="{success:true}";
            }
        }
        writer.write(json);
        writer.close();
        return null;
    }

    //得到终端运行进程
    public String getProcessList()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String serialnumber = request.getParameter("serialnumber");
        String start = request.getParameter("start");
        String limit = request.getParameter("limit");
        List<StopList> apps  = null;
        StringBuilder json = new StringBuilder();
        if(serialnumber!=null){
            CaUser user = on_User_Map.get(serialnumber);
            if(user!=null) {
                apps = user.getRunProcesses();
                if(apps!=null){
                    json.append(getProcessJson(Integer.parseInt(start), Integer.parseInt(limit), apps));
                }
            }
        }
        String sb = "{success:true,total:" + apps.size() + ",rows:["+json.toString()+"]}";
        logger.info(sb);
        writer.write(sb);
        writer.close();
        return null;
    }

    //得到转换后终端运行进程的json
    public StringBuffer getProcessJson(int first, int limitInt, List<StopList> list) {
        StringBuffer showData=new StringBuffer();
        int end=first+limitInt;
        int index = end>list.size()?list.size():end;
        for(int i=first;i<index;i++){
            StopList app = list.get(i);
            showData.append("{");
            showData.append("processId:'"+app.getProcessId() + "'").append(",");
            showData.append("processName:'"+app.getProcessName()+"'");
            showData.append("}");
            if(i != index-1){
                showData.append(",");
            }
        }
        return showData;
    }

    //白名单网址过滤
    public boolean whiteFilter(String accessAddress){
        boolean flag = false;
        List<WhiteList> whiteListList = whiteListService.findAll();
        Iterator<WhiteList> whiteListIterator = whiteListList.iterator();
        while (whiteListIterator.hasNext()){
            WhiteList whiteList = whiteListIterator.next();
            String whiteUrl =whiteList.getUrl();
            if(whiteUrl.equals("*")){
                flag = true;
            }else if(whiteUrl.startsWith("*")){
                String white = whiteUrl.replace("*","");
                if(accessAddress.toLowerCase().endsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(whiteUrl)){
                    flag = true;
                }
            } else if(whiteUrl.endsWith("*")){
                String white = whiteUrl.replace("*","");
                if(accessAddress.toLowerCase().startsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(whiteUrl)){
                    flag = true;
                }
            } else if(whiteUrl.contains("*")){
                String white = whiteUrl.replace("*","");
                String[] split = whiteUrl.split("\\*");
                if(accessAddress.toLowerCase().startsWith(split[0])&&accessAddress.toLowerCase().endsWith(split[1])||accessAddress.equalsIgnoreCase(white)){
                    flag = true;
                }
            }else{
                if (whiteUrl.equalsIgnoreCase(accessAddress)){
                    flag = true;
                }
            }
        }
        return flag;
    }
    //黑名单网址过滤
    public boolean blackFilter(String accessAddress){
        boolean flag = false;
        List<BlackList> blackListList = blackListService.findAll();
        Iterator<BlackList> blackListIterator = blackListList.iterator();
        while (blackListIterator.hasNext()){
            BlackList blackList = blackListIterator.next();
            String blackUrl = blackList.getUrl();
            if(blackUrl.equals("*")){
                flag = true;
            }else if(blackUrl.startsWith("*")){
                String white = blackUrl.replace("*","");
                if(accessAddress.toLowerCase().endsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(blackUrl)){
                    flag = true;
                }
            } else if(blackUrl.endsWith("*")){
                String white = blackUrl.replace("*","");
                if(accessAddress.toLowerCase().startsWith(white.toLowerCase())||accessAddress.equalsIgnoreCase(blackUrl)){
                    flag = true;
                }
            } else if(blackUrl.contains("*")){
                String black = blackUrl.replace("*","");
                String[] split = blackUrl.split("\\*");
                if(accessAddress.toLowerCase().startsWith(split[0])&&accessAddress.toLowerCase().endsWith(split[1])||accessAddress.equalsIgnoreCase(black)){
                    flag = true;
                }
            }else{
                if (blackUrl.equalsIgnoreCase(accessAddress)){
                    flag = true;
                }
            }
        }
        return flag;
    }

    //校验非法外连
    public boolean checkAccessAddress(String uri, int s,CaUser caUser){
        String black_flag = AndroidConfigAction.getAttribute("stop_url") ;        //黑名单
        String white_flag = AndroidConfigAction.getAttribute("allow_url");        //白名单
        if(black_flag.equals("on")&&white_flag.equals("on")){
            if(!blackFilter(uri.trim())){
                if(whiteFilter(uri.trim())){
                    return true;
                } else {
                    caUser.setAbnormalUrl("用户"+caUser.getCn()+"非法外连访问url:"+uri+" 次数:"+s);
                   return false;
                }
            }else {
                caUser.setAbnormalUrl("用户"+caUser.getCn()+"非法外连访问url:"+uri+" 次数:"+s);
                return false;
            }
        } else if(black_flag.equals("on")) {
            if(blackFilter(uri.trim())){
                caUser.setAbnormalUrl("用户"+caUser.getCn()+"非法外连访问url:"+uri+" 次数:"+s);
                return false;
            }else {
                return true;
            }
        }else {
            if(whiteFilter(uri.trim())){
                return true;
            }else {
                caUser.setAbnormalUrl("用户"+caUser.getCn()+"非法外连访问url:"+uri+" 次数:"+s);
                return false;
            }
        }
    }
    
    //校验黑名单进程进程
    private void blackProcessIllegal(CaUser user) {
        List<StopList> runList = user.getRunProcesses();
        if(runList!=null){
            for (StopList stopList:runList){
                String processId = stopList.getProcessId();
                if(blackProcess(processId)){
                    //表示进程违规日志
                    user.setLogFlag("BLPLG");
                    user.setMsg("用户 "+user.getCn()+" 进程违规"+stopList.getProcessName());
                    user.setAbnormalProcess(stopList.getProcessName());
                    String json = AbnormalJson.BLPLG_json(user);
                    logger.info("发送黑名单违规日志"+json);
                    SysLogSend.sysLog(json);
                }
            }
        }
    }

    //黑名单进程过滤
    public boolean blackProcess(String processId){
        StopList stopList = null;
        try {
            stopList = stopListService.findById(processId);
        } catch (Exception e) {
            return false;
        }
        if(stopList!=null){
            return true;
        }
        return false;
    }
    
    //发送日志上线率
    public void sendOnLineRate(CaUser user){
        user.setLogFlag("OLRLG");       //表示上线率日志
        user.setMsg("用户 "+user.getCn()+" 上线时间"+user.getDate());
        String json = AbnormalJson.OLRLG_json(user);
        logger.info("发送上线率日志"+json);
        SysLogSend.sysLog(json);
    }
    
    //校验非法外连
    public void checkOutIllegal(CaUser user){
        List<AccessUrl> accessUrls = user.getAccessUrls();
        if(accessUrls!=null){
            String msg = new String();
            for (AccessUrl s:accessUrls){
                if(s.getUrl()!=null&&s.getNum()>0){
                    if(checkAccessAddress(s.getUrl(),s.getNum(),user)){
                        msg ="用户"+user.getCn()+"正常访问,访问地址:"+s.getUrl()+",次数:"+s.getNum()+"!";
                        logger.info(msg);
                    } else {
                        //非法外连违规日志
                        user.setLogFlag("OEILG");
                        String json = AbnormalJson.OEILG_json(user);
                        logger.info("发送非法外连"+json);
                        SysLogSend.sysLog(json);
                    }
                }
            }
        }
    }
    
    //校验机卡分离与用户bs访问权限
    public void checkOutCard_Bs(CaUser user)throws Exception{
        String msg = null;
        CaUser raUser = null;
        try {
            raUser = caUserService.findBySerialNumber(user.getHzihcaserialNumber());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        if(raUser!=null){
            if(null!=raUser.getTerminalid()&&null!=user.getTerminalid()&&!raUser.getTerminalid().equals(user.getTerminalid())){
                msg = "机卡分离,用户"+user.getCn()+"手机与证书序列号不匹配!";
                user.setLogFlag("MCSLG"); //机上分离违规日志
                user.setMsg(msg);
                String json = AbnormalJson.MCSLG_json(user);
                logger.info("发送机卡分离"+json);
                SysLogSend.sysLog(json);
            }else {
                if(null!=raUser.getPhonenetid()&&null!=user.getPhonenetid()&&!raUser.getPhonenetid().equals(user.getPhonenetid())){
                    msg = "机卡分离,用户"+user.getCn()+"手机入网id与(手机号,证书序列号)不匹配!";
                    user.setLogFlag("MCSLG");   //机上分离违规日志
                    user.setMsg(msg);
                    String json = AbnormalJson.MCSLG_json(user);
                    logger.info("发送机卡分离"+json);
                    SysLogSend.sysLog(json);
                } else {
                    msg = "机卡分离,用户"+user.getCn()+"手机入网id与(手机号,证书序列号)不匹配!终端未传入设备信息或服务器未录入设备信息!";
                    user.setLogFlag("MCSLG");   //机上分离违规日志
                    user.setMsg(msg);
                    String json = AbnormalJson.MCSLG_json(user);
                    logger.info("发送机卡分离"+json);
                    SysLogSend.sysLog(json);
                }
            }
        } else {
            msg = "机卡分离,服务器不存此用户证书"+user.getCn()+"!";
            user.setLogFlag("MCSLG");    //机卡分离违规日志
            user.setMsg(msg);
            String json = AbnormalJson.MCSLG_json(user);
            logger.info("发送机卡分离"+json);
            SysLogSend.sysLog(json);
        }
    }
}
