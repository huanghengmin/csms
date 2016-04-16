package com.hzih.ra.web.action.ra;

import com.hzih.ra.utils.Version;
import com.hzih.ra.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-9-29
 * Time: 下午3:47
 * To change this template use File | Settings | File Templates.
 */
public class VersionAction extends ActionSupport {

    public String execute() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String src = ServletActionContext.getServletContext().getRealPath("/android") + "/tcandroid/version.txt" ;
        File file = new File(src);

        if(file.exists()){
            ByteArrayOutputStream byteArrayInputStream = null;
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = new byte[1024];
            byteArrayInputStream = new ByteArrayOutputStream(1024*1024);
            int length = 0 ;
            while(( length = fileInputStream.read(data)) != -1){
                byteArrayInputStream.write(data,0,length);
            }
            String versionStr = new String(byteArrayInputStream.toByteArray(),"GBK");
            Version version = stringToVersion(versionStr);
            StringBuilder sb = new StringBuilder("{");
            sb.append("total:'1',rows:[{");
            sb.append("name:'").append(version.getName()).append("',");
            sb.append("date:'").append(version.getTeam()).append("',");
            sb.append("version:'").append(version.getNo()).append("',");
            sb.append("team:'").append(version.getTeam()).append("'");
            sb.append("}]}");
            String json = sb.toString();
            actionBase.actionEnd(response, json, result);
        }
        return null;
    }

    private Version stringToVersion(String versionString){
        Version version = new Version();
        String[] versions = versionString.split(",");
        for(int i = 0 ; i<versions.length ; i++){
            String s1 = versions[i].substring(0,versions[i].indexOf(":"));
            String s2 = versions[i].substring(versions[i].indexOf(":")+1,versions[i].length());
            if(s1.equals("name")){
                version.setName(s2);
            }
            else if(s1.equals("no")){
                version.setNo(s2);
            }
            else if(s1.equals("team")){
                version.setTeam(s2);
            }
            else if(s1.equals("time")){
                version.setPublishTime(s2);
            }
        }
        return version;
    }
}
