package com.hzih.ra.entity;

/**
 * Created by IntelliJ IDEA.
 * User: hhm
 * Date: 12-8-1
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public class HzihCa {
    private String  cn;

    public String getHzihdn() {
        return hzihdn;
    }
    private String repassword;

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    @Override
    public String toString() {
        return "HzihCa{" +
                "cn='" + cn + '\'' +
                ", hzihdn='" + hzihdn + '\'' +
                ", hzihprovince='" + hzihprovince + '\'' +
                ", hzihpassword='" + hzihpassword + '\'' +
                ", hzihcity='" + hzihcity + '\'' +
                ", hzihorganization='" + hzihorganization + '\'' +
                ", hzihinstitutions='" + hzihinstitutions + '\'' +
                ", hzihcastatus='" + hzihcastatus + '\'' +
                ", hzihprivatekey='" + hzihprivatekey + '\'' +
                ", hzihcaserialNumber='" + hzihcaserialNumber + '\'' +
                ", hzihcertificates='" + hzihcertificates + '\'' +
                ", hzihparentca='" + hzihparentca + '\'' +
                ", hzihcavalidity='" + hzihcavalidity + '\'' +
                ", hzihcacreatedate='" + hzihcacreatedate + '\'' +
                ", hzihcaenddate='" + hzihcaenddate + '\'' +
                ", hzihcertificatetype='" + hzihcertificatetype + '\'' +
                '}';
    }

    public void setHzihdn(String hzihdn) {
        this.hzihdn = hzihdn;
    }

    private String  hzihdn;
    private String  hzihprovince;
    private String  hzihpassword; 
    private String  hzihcity; 
    private String  hzihorganization; 
    private String  hzihinstitutions; 
    private String  hzihcastatus;
    private String  hzihprivatekey;
    private String  hzihcaserialNumber;
    private String  hzihcertificates;
    private String  hzihparentca;
    private String  hzihcavalidity;
    private String  hzihcacreatedate;
    private String  hzihcaenddate;
    private String  hzihcertificatetype;

    public static String getLdapObjectAttributeName(){
         return "hzihca";
     }
    public static String getLdapCnAttributeName(){
        return "cn";
    }
    public static String getLdapHzihProvinceAttributeName(){
        return "hzihprovince";
    }
    public static String getLdapHzihPasswordAttributeName(){
        return "hzihpassword";
    }
    public static String getLdapHzihCityAttributeName(){
        return "hzihcity";
    }
    public static String getLdapHzihOrganizationAttributeName(){
        return "hzihorganization";
    }
    public static String getLdapHzihInstitutionsAttributeName(){
        return "hzihinstitutions";
    }
    public static String getLdapHzihCaStatusAttributeName(){
        return "hzihcastatus";
    }
    public static String getLdapHzihPrivateKeyAttributeName(){
        return "hzihprivatekey";
    }
    public static String getLdapHzihCaSerialNumberAttributeName(){
        return "hzihcaserialNumber";
    }
    public static String getLdapHzihCertificatesAttributeName(){
        return "hzihcertificates";
    }
    public static String getLdapHzihParentCaAttributeName(){
        return "hzihparentca";
    }
    public static String getLdapHzihCaValidityAttributeName(){
        return "hzihcavalidity";
    }
    public static String getLdapHzihCaCreateDateAttributeName(){
        return "hzihcacreatedate";
    }
    public static String getLdapHzihCaEndDateAttributeName(){
        return "hzihcaenddate";
    }
    public static String getLdapHzihCertificateTypeAttributeName(){
        return "hzihcertificatetype";
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getHzihprovince() {
        return hzihprovince;
    }

    public void setHzihprovince(String hzihprovince) {
        this.hzihprovince = hzihprovince;
    }

    public String getHzihpassword() {
        return hzihpassword;
    }

    public void setHzihpassword(String hzihpassword) {
        this.hzihpassword = hzihpassword;
    }

    public String getHzihcity() {
        return hzihcity;
    }

    public void setHzihcity(String hzihcity) {
        this.hzihcity = hzihcity;
    }

    public String getHzihorganization() {
        return hzihorganization;
    }

    public void setHzihorganization(String hzihorganization) {
        this.hzihorganization = hzihorganization;
    }

    public String getHzihinstitutions() {
        return hzihinstitutions;
    }

    public void setHzihinstitutions(String hzihinstitutions) {
        this.hzihinstitutions = hzihinstitutions;
    }

    public String getHzihcastatus() {
        return hzihcastatus;
    }

    public void setHzihcastatus(String hzihcastatus) {
        this.hzihcastatus = hzihcastatus;
    }

    public String getHzihprivatekey() {
        return hzihprivatekey;
    }

    public void setHzihprivatekey(String hzihprivatekey) {
        this.hzihprivatekey = hzihprivatekey;
    }

    public String getHzihcaserialNumber() {
        return hzihcaserialNumber;
    }

    public void setHzihcaserialNumber(String hzihcaserialNumber) {
        this.hzihcaserialNumber = hzihcaserialNumber;
    }

    public String getHzihcertificates() {
        return hzihcertificates;
    }

    public void setHzihcertificates(String hzihcertificates) {
        this.hzihcertificates = hzihcertificates;
    }

    public String getHzihparentca() {
        return hzihparentca;
    }

    public void setHzihparentca(String hzihparentca) {
        this.hzihparentca = hzihparentca;
    }

    public String getHzihcavalidity() {
        return hzihcavalidity;
    }

    public void setHzihcavalidity(String hzihcavalidity) {
        this.hzihcavalidity = hzihcavalidity;
    }

    public String getHzihcacreatedate() {
        return hzihcacreatedate;
    }

    public void setHzihcacreatedate(String hzihcacreatedate) {
        this.hzihcacreatedate = hzihcacreatedate;
    }

    public String getHzihcaenddate() {
        return hzihcaenddate;
    }

    public void setHzihcaenddate(String hzihcaenddate) {
        this.hzihcaenddate = hzihcaenddate;
    }

    public String getHzihcertificatetype() {
        return hzihcertificatetype;
    }

    public void setHzihcertificatetype(String hzihcertificatetype) {
        this.hzihcertificatetype = hzihcertificatetype;
    }
}
