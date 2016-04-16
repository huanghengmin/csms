package com.hzih.ra.web.action.ra;
import com.hzih.ra.domain.*;
import com.hzih.ra.entity.AccessUrl;
import com.hzih.ra.entity.BlueTooth;
import com.hzih.ra.entity.Wifi;
import com.hzih.ra.json.JSONUtils;
import com.hzih.ra.service.BlackListService;
import com.hzih.ra.service.CaUserService;
import com.hzih.ra.service.StopListService;
import com.hzih.ra.service.WhiteListService;
import com.hzih.ra.syslog.SysLogSend;
import com.hzih.ra.utils.StringContext;
import com.hzih.ra.web.action.ActionBase;
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
 * Date: 13-1-2
 * Time: 上午9:51
 * To change this template use File | Settings | File Templates.
 */
public class CheckAuthorAction extends ActionSupport {
    private Logger logger =  Logger.getLogger(CheckAuthorAction.class);
    private WhiteListService whiteListService;
    private BlackListService blackListService;
    private StopListService stopListService;
    public static  Map<String,CaUser> raUserMap = new LinkedHashMap<String,CaUser>();

    public StopListService getStopListService() {
        return stopListService;
    }

    public void setStopListService(StopListService stopListService) {
        this.stopListService = stopListService;
    }

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

    private CaUserService caUserService;

    public CaUserService getCaUserService() {
        return caUserService;
    }

    public void setCaUserService(CaUserService caUserService) {
        this.caUserService = caUserService;
    }
    //android获取截屏状态
    public String getView()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json = "false";
        String serialnumber = request.getParameter("serialnumber");
        if(serialnumber!=null){
            CaUser raUser = raUserMap.get(serialnumber);
            json = String.valueOf(raUser.isViewFlag());
        }
        writer.write(json);
        writer.close();
        return null;
    }
    //android更新截屏
    public String updateView()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String serialnumber = request.getHeader("serialnumber");
        InputStream in = request.getInputStream();
        String type =request.getHeader("type");
        String json = "{success:false}";
        if(serialnumber!=null){
            CaUser raUser = raUserMap.get(serialnumber);
            if(raUser!=null&&!raUser.equals("")) {
                if(raUser.isViewFlag()){
                    raUser.setViewFlag(false);
                    raUserMap.remove(serialnumber);
                    raUserMap.put(serialnumber,raUser);
                } else {
                    raUser.setViewFlag(true);
                    raUserMap.remove(serialnumber);
                    raUserMap.put(serialnumber,raUser);
                }
                json="{success:true}";
            }
        }
        //图片写到指定地址
        if(in!=null){
            writeInputStreamToFile(in,serialnumber,type);
        }
        writer.write(json);
        writer.close();
        return null;
    }
    //截屏
    public String view()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String serialnumber = request.getParameter("serialnumber");
        String json = "{success:false}";
        if(serialnumber!=null){
            CaUser raUser = raUserMap.get(serialnumber);
            if(raUser!=null&&!raUser.equals("")) {
                if(!raUser.isViewFlag()){
                    raUser.setViewFlag(true);
                    raUserMap.remove(serialnumber);
                    raUserMap.put(serialnumber,raUser);
                }
                json="{success:true}";
            }
        }
        writer.write(json);
        writer.close();
        return null;
    }
   //得到转换后的json
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
            long max = 0;
            String type = "jpg";
            File file[] = dir.listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()){
                    String name = file[i].getName();
                    if(name.contains(".")){
                        String num = name.substring(0,name.lastIndexOf("."));
                        max = Long.parseLong(num);
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
            CaUser raUser = raUserMap.get(serialnumber);
            if(raUser!=null&&!raUser.equals("")) {
                apps = raUser.getRunProcesses();
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
            } catch (IOException e) {
                return;
            }
        }
    }
    //返回在线用户列表
    public String onLineUser()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        StringBuilder json = new StringBuilder();
        Set<String> key = raUserMap.keySet();
        for (Iterator it = key.iterator(); it.hasNext();) {
            String s = (String) it.next();
            CaUser value = raUserMap.get(s);
            JSONObject jsonObject = JSONObject.fromObject(value);
            json.append(jsonObject.toString());
            if(it.hasNext()){
                json.append(",");
            }
        }
        String sb = "{success:true,total:" + key.size() + ",rows:["+json.toString()+"]}";
        writer.write(sb);
        writer.close();
        return null;
    }
    //白名单过滤
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
    //黑名单过滤
    public boolean blackFilter(String accessAddress){
        boolean flag = true;
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
    //校验非法外连
    public boolean checkAccessAddress(String accessAddress, int s){
        boolean flag = true;
        if(blackFilter(accessAddress)){
            if(!whiteFilter(accessAddress)){
                flag = false;
                logger.info("白名单未包含访问网址:"+accessAddress+"访问:"+s+"次");
            }
        }else {
            flag = false;
            logger.info("访问黑名单网址:"+accessAddress+",访问次数:"+s);
        }
        return flag;
    }
    //终端终止进程 action
    public String stopProcess()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        StringBuilder json = new StringBuilder();
        List<StopList> terminalApps = stopListService.getStopProcess();
        JSONArray jsonArray = null;
        if(terminalApps!=null&&terminalApps.size()>0){
           jsonArray = JSONArray.fromObject(terminalApps);
        }
        if(jsonArray!=null){
            for (int i = 0; i < jsonArray.size(); i++) {
                 JSONObject jo = (JSONObject) jsonArray.get(i);
                 json.append("{processId:'"+jo.get("processId")+"'}");
                if(i!=jsonArray.size()-1){
                   json.append(",");
                }
            }
        }
        String ss= "["+json.toString()+"]";
        writer.write(URLEncoder.encode(ss,"utf-8"));
        writer.close();
       return null;
    }  
    //encode转换
    public String if_encode(String param_name,HttpServletRequest request) {
        String e_param = request.getHeader(param_name);
        if(e_param!=null){
            try {
                return java.net.URLDecoder.decode(e_param,"utf-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return null;
    }
    //上线数据导入
    public String checkTerminalAuthor()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        String msg = null;
        //在线终端进程
        String process =if_encode("process",request);
        //用户名
        String user =if_encode("username",request);
        //身份证号
        String userid =if_encode("userid",request);
        //省
        String province =if_encode("province",request);
        //市
        String city =if_encode("city",request);
        //组织
        String organization =if_encode("organization",request);
        //机构
        String institutions =if_encode("institutions",request);
        //日期
        String date =if_encode("date",request);
        //手机号
        String phone =if_encode("phone",request);
        //入网id
        String phonenetid =if_encode("phonenetid",request);
        //证书序列号
        String serialnumber =if_encode("serialnumber",request);
        //访问url数据，次数
        String access_url =if_encode("data",request);
        logger.info(access_url);
        //蓝牙 打开详细
        String blueTooth = request.getHeader("bluetooth");
        //wifi 打开详细
        String wifi = request.getHeader("wifi");
        //wifi操作列表
        List<Wifi> wifis = null;
        if(wifi!=null&&!wifi.equals("")){
            wifis  = JSONUtils.toList(wifi, Wifi.class);
        }
        //蓝牙操作列表
        List<BlueTooth> blueTooths = null;
        if(blueTooth!=null&&!blueTooth.equals("")){
            blueTooths  = JSONUtils.toList(blueTooth, BlueTooth.class);
        }
        //url访问列表
        List<AccessUrl> accessUrls = null;
        if(access_url!=null&&!access_url.equals("")){
          accessUrls  = JSONUtils.toList(access_url, AccessUrl.class);
        }
        //进程
        List<StopList> processes = null;
        if(process!=null&&!process.equals("")){
            processes = JSONUtils.toList(process, StopList.class);
        }
        CaUser raUser = new CaUser();
        raUser.setCn(user);
        raUser.setHzihid(userid);
        raUser.setHzihprovince(province);
        raUser.setHzihcity(city);
        raUser.setHzihorganization(organization);
        raUser.setHzihinstitutions(institutions);
        raUser.setDate(date);
        raUser.setHzihphone(phone);
        raUser.setPhonenetid(phonenetid);
        raUser.setHzihcaserialNumber(serialnumber);
        if(accessUrls!=null){
            raUser.setAccessUrls(accessUrls);
        }
        if(wifis!=null){
            raUser.setWifis(wifis);
        }
        if(blueTooths!=null){
            raUser.setBlueTooths(blueTooths);
        }
        if(processes!=null){
            raUser.setRunProcesses(processes);
        }
        //添加进map
        if(raUserMap.get(serialnumber)!=null){
            raUser.setViewFlag(raUserMap.get(serialnumber).isViewFlag());
            raUserMap.remove(serialnumber);
            raUserMap.put(serialnumber,raUser);
        }else {
            raUserMap.put(serialnumber,raUser);
        }
        //上线率日志发送
        sendOnLineRate(raUser);
        //非法进程访问
        blackProcessIllegal(raUser);
        //非法外连
        checkOutIllegal(raUser);
        //机卡分离and BS访问
        checkOutCard_Bs(raUser);
        return null;
    }
    //校验黑名单进程
    private void blackProcessIllegal(CaUser raUser) {
        List<StopList> runList = raUser.getRunProcesses();
        if(runList!=null){
            for (StopList stopList:runList){
                String processId = stopList.getProcessId();
                if(blackProcess(processId)){
                    //表示进程违规日志
                    raUser.setLogFlag("BLPLG");
                    JSONObject jsonObject = JSONObject.fromObject(raUser);
                    logger.info("发送黑名单违规日志"+jsonObject.toString());
                    SysLogSend.sysLog(jsonObject.toString());
                }
            }
        }
    }
    //发送日志上线率
    public void sendOnLineRate(CaUser raUser){
        //表示上线率日志
        raUser.setLogFlag("OLRLG");
        JSONObject jsonObject = JSONObject.fromObject(raUser);
        logger.info("发送上线率日志"+jsonObject.toString());
        SysLogSend.sysLog(jsonObject.toString());
    }
    //校验非法外连
    public void checkOutIllegal(CaUser raUser){
        List<AccessUrl> accessUrls = raUser.getAccessUrls();
        if(accessUrls!=null){
            String msg = new String();
            for (AccessUrl s:accessUrls){
                if(s.getUrl()!=null&&s.getNum()>0){
                    if(checkAccessAddress(s.getUrl(),s.getNum())){
                        msg ="用户正常访问,访问地址:"+s.getUrl()+",次数:"+s.getNum()+"!";
                        logger.info(msg);
                        SysLogSend.sysLog(msg);
                    } else {
                        msg = "用户非法外连,外连地址"+s.getUrl()+"!外连次数:"+s.getNum();
                        //非法外连违规日志
                        raUser.setLogFlag("OEILG");
                        raUser.setMsg(msg);
                        JSONObject jsonObject = JSONObject.fromObject(raUser);
                        logger.info("发送非法外连日志"+jsonObject.toString());
                        SysLogSend.sysLog(jsonObject.toString());
                    }
                }
            }
        }
    }
    //校验机卡分离与用户bs访问权限
    public void checkOutCard_Bs(CaUser ra_user) throws Exception {
       String msg = null;
       CaUser raUser = caUserService.findBySerialNumber(ra_user.getHzihcaserialNumber());
       if(raUser!=null){
//           if(!raUser.getPhone().equals(ra_user.getPhone())){
               if(!raUser.getTerminalid().equals(ra_user.getTerminalid())){
               msg = "机卡分离,用户手机与证书序列号不匹配!";
               //机上分离违规日志
               ra_user.setLogFlag("MCSLG");
               ra_user.setMsg(msg);
               JSONObject jsonObject = JSONObject.fromObject(ra_user);
               logger.info("发送机卡分离日志"+jsonObject.toString());
               SysLogSend.sysLog(jsonObject.toString());
           }else {
               if(!raUser.getPhonenetid().equals(ra_user.getPhonenetid())){
                   msg = "机卡分离,用户手机入网id与(手机号,证书序列号)不匹配!";
                   //机上分离违规日志
                   ra_user.setLogFlag("MCSLG");
                   ra_user.setMsg(msg);
                   JSONObject jsonObject = JSONObject.fromObject(ra_user);
                   logger.info("发送机卡分离日志"+jsonObject.toString());
                   SysLogSend.sysLog(jsonObject.toString());
               } /*else {

//                   if(raUser.getStatus()==0){
//                       msg = "用户校验通过,允许访问!";
//                       ra_user.setAccessFlag(true);
//                       //bs是否允许访问日志
//                       ra_user.setLogFlag("BSSLG");
//                       ra_user.setMsg(msg);
//                       JSONObject jsonObject = JSONObject.fromObject(ra_user);
//                       logger.info("发送BS日志"+jsonObject.toString());
//                       SysLogSend.sysLog(jsonObject.toString());
//                   } else if(raUser.getStatus()==1){
//                       msg = "用户校验完成,已暂停用户访问!!";
//                       ra_user.setAccessFlag(false);
//                       //bs是否允许访问日志
//                       ra_user.setLogFlag("BSSLG");
//                       ra_user.setMsg(msg);
//                       JSONObject jsonObject = JSONObject.fromObject(ra_user);
//                       logger.info("发送BS日志"+jsonObject.toString());
//                       SysLogSend.sysLog(jsonObject.toString());
//                   } else {
//                       msg = "用户校验完成,已停止用户访问!!";
//                       ra_user.setAccessFlag(false);
//                       //bs是否允许访问日志
//                       ra_user.setLogFlag("BSSLG");
//                       ra_user.setMsg(msg);
//                       JSONObject jsonObject = JSONObject.fromObject(ra_user);
//                       logger.info("发送BS日志"+jsonObject.toString());
//                       SysLogSend.sysLog(jsonObject.toString());
//                   }
               }*/
           }
       } else {
           msg = "机卡分离,服务器不存在拥有此证书用户!";
           //机卡分离违规日志
           ra_user.setLogFlag("MCSLG");
           ra_user.setMsg(msg);
           JSONObject jsonObject = JSONObject.fromObject(ra_user);
           logger.info("发送机卡分离日志"+jsonObject.toString());
           SysLogSend.sysLog(jsonObject.toString());
       }
   }
    //得到终端配置
    public String config()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        int totalCount =0;
        StringBuilder stringBuilder = new StringBuilder();
        returnSystemConfigData(stringBuilder);
        totalCount = totalCount+1;
        StringBuilder json=new StringBuilder("{totalCount:"+totalCount+",root:[");
        json.append(stringBuilder.toString().substring(0,stringBuilder.toString().length()-1));
        json.append("]}");
        try {
            actionBase.actionEnd(response,json.toString(),result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAttribute(String elementName){
        String xml = StringContext.systemPath+"/config/android_config.xml";
        Document document = buildFromFile(xml);
        Element root =  document.getRootElement();
        Element child = root.getChild(elementName);
        return child.getText();
    }

    private void returnSystemConfigData(StringBuilder stringBuilder) {
        stringBuilder.append("{");
        stringBuilder.append("wifi_num:'"+ getAttribute("wifi_num")+"',");
        stringBuilder.append("booth_num:'"+getAttribute("booth_num")+"',");
        stringBuilder.append("up_time:'"+getAttribute("up_time")+"',");
        stringBuilder.append("clear_time:'"+getAttribute("clear_time")+"',");
        stringBuilder.append("disable_wifi:'"+getAttribute("disable_wifi")+"',");
        stringBuilder.append("disable_booth:'"+getAttribute("disable_booth")+"'");
        stringBuilder.append("},");
    }

    public  Document buildFromFile(String filePath) {
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
    //得到链接状态
    public String connect(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = "{success:true}";
        try {
            actionBase.actionEnd(response,json.toString(),result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //违规超过上限
    public String mcControls()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
//        PrintWriter writer = response.getWriter();
        String message = request.getParameter("message");
        String serialnumber = request.getParameter("serialnumber");
        CaUser raUser = raUserMap.get(serialnumber);
        if(raUser!=null){
            raUser.setAbnormalFlag("true");
            raUser.setAbnormalMessage(message);
            raUserMap.remove(serialnumber);
            raUserMap.put(serialnumber,raUser);
        }
        return null;
    }
    //终端获取违规记录
    public String getAbnormal()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        StringBuilder json =new StringBuilder();
        PrintWriter writer = response.getWriter();
        String serialnumber = request.getParameter("serialnumber");
        CaUser raUser = raUserMap.get(serialnumber);
        if(raUser!=null){
           if(raUser.getAbnormalFlag().equals("true")){
             json.append("{");
               json.append("abnormalFlag:'true',");
               json.append("message:'"+raUser.getAbnormalMessage()+"'");
               json.append("}");
           }
        }
        writer.write(json.toString());
        writer.close();
        return null;
    }

}
