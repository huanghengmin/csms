package com.hzih.ra.web.action.ca;

import com.hzih.ra.utils.StringContext;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;

public class CaConfigXml {
    private static Logger logger=Logger.getLogger(CaConfigXml.class);
    public  static String ca_config = "ca_config";
    public  static String ca_ip = "ca_ip";
    public  static String ca_port = "ca_port";
//    public  static String bs_ip = "bs_ip";
//    public  static String bs_port = "bs_port";
    private static String path = StringContext.systemPath+"/config/ca_config.xml";

    public static String getAttribute(String attributeName){
        SAXReader saxReader = new SAXReader();
        Document doc=null;
        try {
            doc =saxReader.read(new File(path));
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }
        Element root = doc.getRootElement();
        String result = root.attributeValue(attributeName);
        return result;
    }

    public static void saveConfig(String ca_ip,String ca_port/*,String bs_ip,String bs_port*/){
        Document doc=DocumentHelper.createDocument();
        Element root=doc.addElement(CaConfigXml.ca_config);
        root.addAttribute(CaConfigXml.ca_ip,ca_ip);
        root.addAttribute(CaConfigXml.ca_port,ca_port);
//        root.addAttribute(CaConfigXml.bs_ip,bs_ip);
//        root.addAttribute(CaConfigXml.bs_port,bs_port);
        OutputFormat outputFormat=new OutputFormat("",true);
        try {
            XMLWriter xmlWriter=new XMLWriter(new FileOutputStream(new File(path)),outputFormat);
            try {
                xmlWriter.write(doc);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage());
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        }
    }
}
