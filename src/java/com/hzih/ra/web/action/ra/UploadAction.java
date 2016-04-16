package com.hzih.ra.web.action.ra;

import com.hzih.ra.utils.ExtractZip;
import com.hzih.ra.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-9-25
 * Time: 下午12:22
 * To change this template use File | Settings | File Templates.
 */
public class UploadAction extends ActionSupport {
    private File uploadFile;
    private static final int BUFFER_SIZE = 1* 1024;
    private boolean success;
    private String fileContentType;
    private String msg;
    private String uploadFileFileName;

    public String execute() throws IOException{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        if(!uploadFileFileName.endsWith("zip")){
            String msg = "上传的文件格式不对";
            String json = "{success:true,msg:'" + msg + "'}";
            actionBase.actionEnd(response, json, result);
        }
        String toSrc = ServletActionContext.getServletContext().getRealPath("/android") + "/" +uploadFileFileName;
        File toFile = new File(toSrc);
        if(!toFile.exists()){
            toFile.getParentFile().mkdirs();
        }
        try {
            writeFile(this.uploadFile, toFile);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "上传失败";
            String json = "{success:true,msg:'" + msg + "'}";
            actionBase.actionEnd(response, json, result);
        }
        ExtractZip extractZip = new ExtractZip();
        if(extractZip.Unzip(toSrc)){
            String msg = "上传成功";
            String json = "{success:true,msg:'" + msg + "'}";
            actionBase.actionEnd(response, json, result);
            toFile.delete();
        }
        else {
            String msg = "上传失败";
            String json = "{success:true,msg:'" + msg + "'}";
            actionBase.actionEnd(response, json, result);
        }
        return null;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getFileContentType() {
        return fileContentType;
    }
    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public File getUploadFile(){
        return this.uploadFile;
    }
    public void setUploadFile(File file){
        this.uploadFile = file;
    }

    public String getUploadFileFileName() {
        return uploadFileFileName;
    }

    public void setUploadFileFileName(String uploadFileFileName) {
        this.uploadFileFileName = uploadFileFileName;
    }

    private static void writeFile(File src, File dst) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(src),BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst),BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
            }
            finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
