package com.hzih.ra.utils;

import com.inetec.common.security.License;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LicenseUtils {
    private static final Logger logger = Logger.getLogger(LicenseUtils.class);
    /**
     *  权限控制
     * @param isExistLicense    是否存在 usb-key
     * @return
     */
	public List<String> getNeedsLicenses(boolean isExistLicense) {
		String qxManager = "TOP_QXGL:SECOND_YHGL:SECOND_JSGL:SECOND_AQCL:";                                             //权限管理
		String wlManager = "TOP_WLGL:SECOND_JKGL:SECOND_LTCS:SECOND_LYGL:SECOND_PZGL:";                               //网络管理
		String xtManager = "TOP_XTGL:SECOND_PTSM:SECOND_PTGL:SECOND_ZSGL:SECOND_RZXZ:SECOND_BBSJ:SECOND_FWQJK:";                 //系统管理
		String sjManager = "TOP_SJGL:SECOND_YHRZ:SECOND_SYSCONFIG:";                                                     //审计管理
        String raManager = "TOP_RAGN:SECOND_ACCESSADDRESS:SECOND_ACCESSUSER:SECOND_BLACKLIST:SECOND_WHITELIST:SECOND_BLACKAPP:SECOND_ONLINEUSER:SECOND_ANDROIDCONFIG:SECOND_TMVERSION:SECOND_ALLOWPROCESS:SECOND_TCSTRATEGY:";         //ra功能
        String caManager = "TOP_CAGN:SECOND_ADD:";              //SECOND_USERCERT:
        String pmManager = "TOP_USERMG:SECOND_USERMG:SECOND_ROLEMG:SECOND_RESOURCEMG:SECOND_PERMISSIONMG";
        String permission = qxManager + wlManager + xtManager + sjManager+raManager+caManager+pmManager;
        if(isExistLicense){
	    	try{
                String license = License.getModules();//许可证允许的权限
                permission += license;
            } catch (Exception e) {
                logger.error("读取USB-KEY出错!");
            }
		}
		String[] permissions = permission.split(":");
		List<String> lps = new ArrayList<String>();
		for (int i = 0; i < permissions.length; i++) {
			lps.add(permissions[i]);
		}
		return lps;
	}
}
