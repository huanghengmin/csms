package com.hzih.ra.utils;

public class CodeUtil {
	//终端请求指令
	public static final String CONNECT = "0x00000000";//连接
	public static final String VIOLATION = "0x00000001";//获取违规记录
	public static final String VIEW = "0x00000002";//获取是否要截屏
	public static final String UPDATE_VIEW = "0x00000012";//上传截屏图片
	public static final String CONFIG = "0x00000003";//获取终端配置

	public static final String ALLOW_PROCESS = "0x00000004";//获取白名单进程
	public static final String STOP_PROCESS = "0x00000005";//获取黑名单进程

    public static final String ALLOW_URL = "0x00000022";//获取白名单网址
    public static final String STOP_URL = "0x00000023";//获取黑名单网址


	public static final String A_S_PROCESS = "0x00000006";//获取黑白名单进程
	public static final String GET_PWD = "0x00000007";//重置密码
	public static final String SET_PWD = "0x000000015";//上传密码
	public static final String REPORT_URL = "0x00000008";//上报访问记录
	public static final String REPORT_WIFI = "0x00000009";//上报wifi违规

    public static final String REPORT_3G = "0x00000020";//上报3G违规
    public static final String REPORT_BROADBAND = "0x00000021";//上报宽带违规

	public static final String REPORT_BLUE_TOOTH = "0x00000010";//上报蓝牙违规
	public static final String REPORT_PROCESS = "0x00000011";//上报终端运行进程

    public static final String REPORT_ALL_PROCESS = "0x00000019";//上报终端所有软件

	public static final String GET_VERSION = "0x00000013"; //获取终端版
	public static final String DOWNLOAD_APK = "0x00000014";//下载终端apk
    public static final String GET_PWD_END ="0x00000016";//返回初置密码状态
    public static final String LOCATION ="0x00000017";//是否上传地理位置
    public static final String UPDATE_LOCATION ="0x00000018";//上传地理位置

	

	//服务端回复指令
	public static final String SUCCESS = "0x10000000";//请求成功
	public static final String BLOCK = "0x10000001";//已阻断
    public static final String NO_BLOCK = "0x10000009";//未阻断

    public static final String STRATEGY_NO_UPDATE= "0x10000010";//未更新过策略
    public static final String STRATEGY_UPDATE = "0x10000011";//更新过策略

	public static final String VIEW_TRUE = "0x10000002";//要截屏
	public static final String VIEW_FALSE ="0x10000003";//不截屏
	public static final String AUDIT_PWD_SUCCESS = "0x10000004";//审批密码
	public static final String AUDIT_PWD_FAIL = "0x10000005";//审批完成
    public static final String AUDIT_PWD_ING = "0x10000006";//审批完成
    public static final String LOCATION_TRUE = "0x10000007";//上传地理位置
    public static final String LOCATION_FALSE ="0x10000008";//不上传地理位置
	
}
