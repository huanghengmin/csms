package com.hzih.ra.entity;

/**
 * Created by IntelliJ IDEA.
 * User: hhm
 * Date: 12-8-1
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
public class HzihUser extends HzihCa{
    private String hzihid;
    private String hzihphone;
    private String hzihaddress;
    private String hzihemail;
    private String hzihjobnumber;

    public static String getLdapObjectAttributeName(){
        return "hzihuser";
    }
    public static String getLdapHzihIdAttributeName(){
        return "hzihid";
    }
    public static String getLdapHzihPhoneAttributeName(){
        return "hzihphone";
    }
    public static String getLdapHzihAddressAttributeName(){
        return "hzihaddress";
    }
    public static String getLdapHzihEmailAttributeName(){
        return "hzihemail";
    }
    public static String getLdapHzihJobNumberAttributeName(){
        return "hzihjobnumber";
    }

    public String getHzihid() {
        return hzihid;
    }

    public void setHzihid(String hzihid) {
        this.hzihid = hzihid;
    }

    public String getHzihphone() {
        return hzihphone;
    }

    public void setHzihphone(String hzihphone) {
        this.hzihphone = hzihphone;
    }

    public String getHzihaddress() {
        return hzihaddress;
    }

    public void setHzihaddress(String hzihaddress) {
        this.hzihaddress = hzihaddress;
    }

    public String getHzihemail() {
        return hzihemail;
    }

    public void setHzihemail(String hzihemail) {
        this.hzihemail = hzihemail;
    }

    public String getHzihjobnumber() {
        return hzihjobnumber;
    }

    public void setHzihjobnumber(String hzihjobnumber) {
        this.hzihjobnumber = hzihjobnumber;
    }


}
