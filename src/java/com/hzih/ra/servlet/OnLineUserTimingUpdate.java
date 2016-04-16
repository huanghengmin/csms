package com.hzih.ra.servlet;
import com.hzih.ra.domain.CaUser;
import com.hzih.ra.web.action.ra.TerminalAction;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: hhm
 * Date: 12-9-17
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
public class OnLineUserTimingUpdate {
    private Logger logger = Logger.getLogger(OnLineUserTimingUpdate.class);
    //更新在线用户
    public void updateOnLine(){
        Set<String> keys = TerminalAction.on_User_Map.keySet();
        for (String key:keys){
            CaUser caUser = TerminalAction.on_User_Map.get(key);
            if(caUser!=null){
             Date onLineTime = caUser.getOnlinetime();
                if(onLineTime!=null){
                    if(new Date().getTime() - caUser.getOnlinetime().getTime()>1*60*1000){
                          TerminalAction.on_User_Map.remove(key);
                          logger.info(caUser.getCn()+"证书序列号"+caUser.getHzihcaserialNumber()+"用户已下线!"+new Date().toString());
                    }
                }else {
                    TerminalAction.on_User_Map.remove(key);
                    logger.info(caUser.getCn()+"证书序列号"+caUser.getHzihcaserialNumber()+"用户已下线!"+new Date().toString());
                }
            }
         }
    }
}
