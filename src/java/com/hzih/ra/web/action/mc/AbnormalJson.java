package com.hzih.ra.web.action.mc;

import com.hzih.ra.domain.CaUser;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-24
 * Time: 上午10:51
 * To change this template use File | Settings | File Templates.
 */

public class AbnormalJson {

    public static String MCSLG_json(CaUser caUser){        //机上分离违规日志
        if(caUser!=null){
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("cn:'"+caUser.getCn()+"',");
            json.append("hzihid:'"+caUser.getHzihid()+"',");
            json.append("hzihphone:'"+caUser.getHzihphone()+"',");
            json.append("hzihaddress:'"+caUser.getHzihaddress()+"',");
            json.append("hzihemail:'"+caUser.getHzihemail()+"',");
            json.append("hzihjobnumber:'"+caUser.getHzihjobnumber()+"',");
            json.append("phonenetid:'"+caUser.getPhonenetid()+"',");
            json.append("hzihcaserialNumber:'"+caUser.getHzihcaserialNumber()+"',");
            json.append("terminalid:'"+caUser.getTerminalid()+"',");
            json.append("hzihprovince:'"+caUser.getHzihprovince()+"',");
            json.append("hzihcity:'"+caUser.getHzihcity()+"',");
            json.append("hzihorganization:'"+caUser.getHzihorganization()+"',");
            json.append("hzihinstitutions:'"+caUser.getHzihinstitutions()+"',");
            json.append("abnormalFlag:'"+caUser.getAbnormalFlag()+"',");
            json.append("abnormalMessage:'"+caUser.getAbnormalMessage()+"',");
            json.append("logFlag:'"+caUser.getLogFlag()+"',");
            json.append("date:'"+caUser.getDate()+"',");
            json.append("msg:'"+caUser.getMsg()+"'");
            json.append("}");
            return json.toString();
        }
        return null;
    }

    public static  String OEILG_json(CaUser caUser){    //非法外连违规日志
        if(caUser!=null){
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("cn:'"+caUser.getCn()+"',");
            json.append("hzihid:'"+caUser.getHzihid()+"',");
            json.append("hzihphone:'"+caUser.getHzihphone()+"',");
            json.append("hzihaddress:'"+caUser.getHzihaddress()+"',");
            json.append("hzihemail:'"+caUser.getHzihemail()+"',");
            json.append("hzihjobnumber:'"+caUser.getHzihjobnumber()+"',");
            json.append("phonenetid:'"+caUser.getPhonenetid()+"',");
            json.append("hzihcaserialNumber:'"+caUser.getHzihcaserialNumber()+"',");
            json.append("terminalid:'"+caUser.getTerminalid()+"',");
            json.append("hzihprovince:'"+caUser.getHzihprovince()+"',");
            json.append("hzihcity:'"+caUser.getHzihcity()+"',");
            json.append("hzihorganization:'"+caUser.getHzihorganization()+"',");
            json.append("hzihinstitutions:'"+caUser.getHzihinstitutions()+"',");
            json.append("abnormalFlag:'"+caUser.getAbnormalFlag()+"',");
            json.append("abnormalMessage:'"+caUser.getAbnormalMessage()+"',");
            json.append("logFlag:'"+caUser.getLogFlag()+"',");
            json.append("date:'"+caUser.getDate()+"',");
            json.append("msg:'"+caUser.getMsg()+"'");
            json.append("}");
            return json.toString();
        }
        return null;
    }


    public static  String OLRLG_json(CaUser caUser){   //表示上线率日志
        if(caUser!=null){
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("cn:'"+caUser.getCn()+"',");
            json.append("hzihid:'"+caUser.getHzihid()+"',");
            json.append("hzihphone:'"+caUser.getHzihphone()+"',");
            json.append("hzihaddress:'"+caUser.getHzihaddress()+"',");
            json.append("hzihemail:'"+caUser.getHzihemail()+"',");
            json.append("hzihjobnumber:'"+caUser.getHzihjobnumber()+"',");
            json.append("phonenetid:'"+caUser.getPhonenetid()+"',");
            json.append("hzihcaserialNumber:'"+caUser.getHzihcaserialNumber()+"',");
            json.append("terminalid:'"+caUser.getTerminalid()+"',");
            json.append("hzihprovince:'"+caUser.getHzihprovince()+"',");
            json.append("hzihcity:'"+caUser.getHzihcity()+"',");
            json.append("hzihorganization:'"+caUser.getHzihorganization()+"',");
            json.append("hzihinstitutions:'"+caUser.getHzihinstitutions()+"',");
            json.append("abnormalFlag:'"+caUser.getAbnormalFlag()+"',");
            json.append("abnormalMessage:'"+caUser.getAbnormalMessage()+"',");
            json.append("logFlag:'"+caUser.getLogFlag()+"',");
            json.append("date:'"+caUser.getDate()+"',");
            json.append("msg:'"+caUser.getMsg()+"'");
            json.append("}");
            return json.toString();
        }
        return null;
    }

    public static String BLPLG_json(CaUser caUser){      //表示进程违规日志
        if(caUser!=null){
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("cn:'"+caUser.getCn()+"',");
            json.append("hzihid:'"+caUser.getHzihid()+"',");
            json.append("hzihphone:'"+caUser.getHzihphone()+"',");
            json.append("hzihaddress:'"+caUser.getHzihaddress()+"',");
            json.append("hzihemail:'"+caUser.getHzihemail()+"',");
            json.append("hzihjobnumber:'"+caUser.getHzihjobnumber()+"',");
            json.append("phonenetid:'"+caUser.getPhonenetid()+"',");
            json.append("hzihcaserialNumber:'"+caUser.getHzihcaserialNumber()+"',");
            json.append("terminalid:'"+caUser.getTerminalid()+"',");
            json.append("hzihprovince:'"+caUser.getHzihprovince()+"',");
            json.append("hzihcity:'"+caUser.getHzihcity()+"',");
            json.append("hzihorganization:'"+caUser.getHzihorganization()+"',");
            json.append("hzihinstitutions:'"+caUser.getHzihinstitutions()+"',");
            json.append("abnormalFlag:'"+caUser.getAbnormalFlag()+"',");
            json.append("abnormalMessage:'"+caUser.getAbnormalMessage()+"',");
            json.append("abnormalProcess:'"+caUser.getAbnormalProcess()+"',");
            json.append("logFlag:'"+caUser.getLogFlag()+"',");
            json.append("date:'"+caUser.getDate()+"',");
            json.append("msg:'"+caUser.getMsg()+"'");
            json.append("}");
            return json.toString();
        }
        return null;
    }


    public static String WIFILG_json(CaUser caUser){      //表示wifi违规日志
        if(caUser!=null){
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("cn:'"+caUser.getCn()+"',");
            json.append("hzihid:'"+caUser.getHzihid()+"',");
            json.append("hzihphone:'"+caUser.getHzihphone()+"',");
            json.append("hzihaddress:'"+caUser.getHzihaddress()+"',");
            json.append("hzihemail:'"+caUser.getHzihemail()+"',");
            json.append("hzihjobnumber:'"+caUser.getHzihjobnumber()+"',");
            json.append("phonenetid:'"+caUser.getPhonenetid()+"',");
            json.append("hzihcaserialNumber:'"+caUser.getHzihcaserialNumber()+"',");
            json.append("terminalid:'"+caUser.getTerminalid()+"',");
            json.append("hzihprovince:'"+caUser.getHzihprovince()+"',");
            json.append("hzihcity:'"+caUser.getHzihcity()+"',");
            json.append("hzihorganization:'"+caUser.getHzihorganization()+"',");
            json.append("hzihinstitutions:'"+caUser.getHzihinstitutions()+"',");
            json.append("abnormalFlag:'"+caUser.getAbnormalFlag()+"',");
            json.append("abnormalMessage:'"+caUser.getAbnormalMessage()+"',");
            json.append("wifi_msg:'"+caUser.getWifi_msg()+"',");
            json.append("logFlag:'"+caUser.getLogFlag()+"',");
            json.append("date:'"+caUser.getDate()+"',");
            json.append("msg:'"+caUser.getMsg()+"'");
            json.append("}");
            return json.toString();
        }
        return null;
    }


    public static String TOOTHLG_json(CaUser caUser){      //表示TOOTH违规日志
        if(caUser!=null){
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("cn:'"+caUser.getCn()+"',");
            json.append("hzihid:'"+caUser.getHzihid()+"',");
            json.append("hzihphone:'"+caUser.getHzihphone()+"',");
            json.append("hzihaddress:'"+caUser.getHzihaddress()+"',");
            json.append("hzihemail:'"+caUser.getHzihemail()+"',");
            json.append("hzihjobnumber:'"+caUser.getHzihjobnumber()+"',");
            json.append("phonenetid:'"+caUser.getPhonenetid()+"',");
            json.append("hzihcaserialNumber:'"+caUser.getHzihcaserialNumber()+"',");
            json.append("terminalid:'"+caUser.getTerminalid()+"',");
            json.append("hzihprovince:'"+caUser.getHzihprovince()+"',");
            json.append("hzihcity:'"+caUser.getHzihcity()+"',");
            json.append("hzihorganization:'"+caUser.getHzihorganization()+"',");
            json.append("hzihinstitutions:'"+caUser.getHzihinstitutions()+"',");
            json.append("abnormalFlag:'"+caUser.getAbnormalFlag()+"',");
            json.append("abnormalMessage:'"+caUser.getAbnormalMessage()+"',");
            json.append("booth_msg:'"+caUser.getBooth_msg()+"',");
            json.append("logFlag:'"+caUser.getLogFlag()+"',");
            json.append("date:'"+caUser.getDate()+"',");
            json.append("msg:'"+caUser.getMsg()+"'");
            json.append("}");
            return json.toString();
        }
        return null;
    }
}
