package com.hzih.ra.web.action.mc.servlet;

import com.hzih.ra.domain.CaUser;
import com.hzih.ra.web.action.ra.TerminalAction;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-7-9
 * Time: 下午2:40
 * To change this template use File | Settings | File Templates.
 */
public class DoTermView  extends HttpServlet {
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
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type","text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String serialnumber = request.getHeader("serialnumber");
        String commands = request.getHeader("command");
        if(commands.equals("jqpgview")){
        PrintWriter writer = response.getWriter();
        String json = "{success:false}";
            if(serialnumber!=null){
                CaUser user = TerminalAction.on_User_Map.get(serialnumber);
                if(user!=null) {
                    if(!user.isViewFlag()){
                        user.setViewFlag(true);
                        TerminalAction.on_User_Map.put(serialnumber,user);
                    }
                    json="{success:true}";
                }
            }
            writer.write(json);
            writer.close();
        }else if(commands.equals("jqpgphoto")){
            if(serialnumber!=null){
                String toSrc =request.getServletContext().getRealPath("/upload/"+serialnumber);
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
                 if(null!=file_file) {
                     if(max!=0){
                         String file_path = request.getServletContext().getRealPath("/upload/"+serialnumber+"/"+max+"."+type);
                         ServletOutputStream out =null;
                         FileInputStream in = null;
                         try {
                             out = response.getOutputStream();
                             in = new FileInputStream(file_path);
                             byte[] content = new byte[1024*1024];
                             int length;
                             while ((length = in.read(content, 0, content.length)) != -1){
                                 out.write(content, 0, length);
                                 out.flush();
                             }
                             in.close();
                             out.flush();
                         } catch (FileNotFoundException e) {
                             e.printStackTrace();
                         } catch (IOException e) {
                             e.printStackTrace();
                         }
                     }
                 }
            }
        }
    }
}
