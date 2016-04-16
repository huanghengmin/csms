import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-20
 * Time: 上午9:53
 * To change this template use File | Settings | File Templates.
 */
public class ConvertBsProxy {
    //生成Bs管理配置文件
    public void convertBsMs(String xml_path,String outputFilePath) throws JDOMException, IOException {
        StringBuilder sb = new StringBuilder();
        SAXBuilder sx=new SAXBuilder();
        Document doc=sx.build(xml_path);               //构造文档对象
        Element root=doc.getRootElement();             //获取根元素
        Element access = root.getChild("access");    //取名字为access的所有元素
//        System.out.print("BS访问标识:"+access.getText());
//        System.out.println();
//        System.out.println("-------------------------------");
        if(access.getValue().equals("true")){
        List managers = root.getChildren("manager");  //得到所有https结点

        //遍历所有https结点
        for(int i=0;i<managers.size();i++){
            Element manager=(Element)managers.get(i);
            String ip=manager.getAttribute("ip").getValue();
            String port=manager.getAttribute("port").getValue();
            String http=manager.getAttribute("protocol").getValue();
            Element proxy=manager.getChild("proxy");
            //加入语句
            sb.append("server {").append("\n");
            sb.append("listen       "+ip+":"+port+";").append("\n");
            sb.append("charset  utf-8;").append("\n");
            sb.append("access_log  logs/host.access.log;").append("\n");

            if(http.equals("https")){
                sb.append("ssl on;\n" +
                        "         ssl_certificate /usr/local/nginx/ssl/User.crt;\n" +
                        "\n" +
                        "         ssl_certificate_key /usr/local/nginx/ssl/User.pem;\n" +
                        "\n" +
                        "         #ssl_client_certificate /usr/local/nginx/ssl/ROOT.crt;              \n" +
                        "\t\n" +
                        "         #ssl_verify_client on;   \n" +
                        "\t\t\t\t\n" +
                        "         #ssl_prefer_server_ciphers on;                      \n" +
                        "      \n" +
                        "         keepalive_timeout    60; ").append("\n");
            }

//            System.out.print("BS管理ip:"+ip+",");
//            System.out.print("BS管理port:"+port+",");
//            System.out.print("BS管理协议:"+http+",");
//            System.out.println();
//            System.out.println("-----------------------------------");

            String proxy_ip=proxy.getAttribute("ip").getValue();
            String proxy_port=proxy.getAttribute("port").getValue();
            String proxy_http=proxy.getAttribute("protocol").getValue();

//            System.out.print("BS代理ip:"+proxy_ip+",");
//            System.out.print("BS代理port:"+proxy_port+",");
//            System.out.print("BS代理协议:"+proxy_http+",");
//            System.out.println();
//            System.out.println("-----------------------------------");

            List url_list = proxy.getChildren("url");
            //遍历所有代理url结点
            for(int j=0;j<url_list.size();j++){
                Element url= (Element)url_list.get(j);
                String name=url.getAttribute("name").getValue();
                String req_url=url.getText();

                sb.append("  location ^~/"+name+" {\n" +
                        "          proxy_pass  "+proxy_http+"://"+proxy_ip+":"+proxy_port+"/"+name+";\n" +
                        "          proxy_redirect    default ;\n" +
                        "          proxy_set_header   Host             $host:"+port+";\n" +
                        "          proxy_set_header X-Real-IP $remote_addr;\n" +
                        "          proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\n" +
                        "      }").append("\n");
//                System.out.print("http:"+http);
//                System.out.print("请求标识 = "+name+",");
//                System.out.print("访问目标url:"+req_url+",");
//                System.out.println();
//                System.out.println("-----------------------------------");
            }

            sb.append("}").append("\n");
        }
            File file = new File(outputFilePath) ;
            FileOutputStream out = new FileOutputStream(file);
            out.write(sb.toString().getBytes());
            out.flush();
            out.close();
        }
    }
    //生成Bs代理配置文件
    public void convertBsPx(String xml_path,String outPutFilePath) throws JDOMException, IOException {
        StringBuilder sb = new StringBuilder();
        SAXBuilder sX=new SAXBuilder();
        Document doc=sX.build(xml_path);        //构造文档对象
        Element root=doc.getRootElement();             //获取根元素
        Element access = root.getChild("access");    //取名字为access的所有元素
        if(access.getValue().equals("true")){
            List managers = root.getChildren("manager");  //得到所有https结点
            //遍历所有https结点
            for(int i=0;i<managers.size();i++){
                Element manager=(Element)managers.get(i);
                String ip=manager.getAttribute("ip").getValue();
                String port=manager.getAttribute("port").getValue();
                String http=manager.getAttribute("protocol").getValue();
                Element proxy=manager.getChild("proxy");
//              System.out.print("BS管理ip:"+ip+",");
//              System.out.print("BS管理port:"+port+",");
//              System.out.print("BS管理协议:"+http+",");
//              System.out.println();
//              System.out.println("-----------------------------------");
                String proxy_ip=proxy.getAttribute("ip").getValue();
                String proxy_port=proxy.getAttribute("port").getValue();
                String proxy_http=proxy.getAttribute("protocol").getValue();
                //加入语句
                sb.append("server {").append("\n");
                sb.append("listen       "+proxy_ip+":"+proxy_port+";").append("\n");
                sb.append("charset  utf-8;").append("\n");
                sb.append("access_log  logs/host.access.log;").append("\n");


                if(http.equals("https")){
                    sb.append("ssl on;\n" +
                            "         ssl_certificate /usr/local/nginx/ssl/User.crt;\n" +
                            "\n" +
                            "         ssl_certificate_key /usr/local/nginx/ssl/User.pem;\n" +
                            "\n" +
                            "         #ssl_client_certificate /usr/local/nginx/ssl/ROOT.crt;              \n" +
                            "\t\n" +
                            "         #ssl_verify_client on;   \n" +
                            "\t\t\t\t\n" +
                            "         #ssl_prefer_server_ciphers on;                      \n" +
                            "      \n" +
                            "         keepalive_timeout    60; ").append("\n");
                }
//            System.out.print("BS代理ip:"+proxy_ip+",");
//            System.out.print("BS代理port:"+proxy_port+",");
//            System.out.print("BS代理协议:"+proxy_http+",");
//            System.out.println();
//            System.out.println("-----------------------------------");
                List url_list = proxy.getChildren("url");
                //遍历所有代理url结点
                for(int j=0;j<url_list.size();j++){
                    Element url= (Element)url_list.get(j);
                    String name=url.getAttribute("name").getValue();
                    String req_url=url.getText();

                    sb.append("  location ^~/"+name+" {\n" +
                            "          proxy_pass  "+req_url+";\n" +
                            "          proxy_redirect    default ;\n" +
                            "          proxy_set_header   Host             $host:"+proxy_port+";\n" +
                            "          proxy_set_header X-Real-IP $remote_addr;\n" +
                            "          proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\n" +
                            "      }").append("\n");
//                System.out.print("http:"+http);
//                System.out.print("请求标识 = "+name+",");
//                System.out.print("访问目标url:"+req_url+",");
//                System.out.println();
//                System.out.println("-----------------------------------");
                }
                sb.append("}").append("\n");
            }
            File file = new File(outPutFilePath) ;
            FileOutputStream out = new FileOutputStream(file);
            out.write(sb.toString().getBytes());
            out.flush();
            out.close();
        }
    }
//    @Test//测试生成配置文件
//    public void testConvert()throws Exception{
//            String xml_path ="E:\\fartec\\ichange\\ra\\bsproxy.xml"; //原始配置文件目录
//            convertBsMs(xml_path,"E:/bsMs.conf");                        //bs管理配置文件生成
//            convertBsPx(xml_path,"E:/bsPx.conf");                        //bs代理配置文件生成
//    }
}
