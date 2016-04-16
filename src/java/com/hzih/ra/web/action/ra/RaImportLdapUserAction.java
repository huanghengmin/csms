package com.hzih.ra.web.action.ra;
import com.hzih.ra.domain.CaUser;
import com.hzih.ra.service.CaUserService;
import com.hzih.ra.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

public class RaImportLdapUserAction extends ActionSupport {

    private Logger log = Logger.getLogger(RaImportLdapUserAction.class);

    public CaUserService getCaUserService() {
        return caUserService;
    }

    public void setCaUserService(CaUserService caUserService) {
        this.caUserService = caUserService;
    }

    private CaUserService caUserService;


    /**
     * 得到LDAP连接
     * @return 目录对象
     */
    public DirContext getLdapDirContext(String ip,String port,String admin,String pwd){
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://"+ip+":"+port);
        env.put(Context.AUTHORITATIVE, "simple");
        env.put(Context.SECURITY_PRINCIPAL, admin);
        env.put(Context.SECURITY_CREDENTIALS, pwd);
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        DirContext ctx =null;
        if (env == null) {          // 参数为空
            log.info("请配置ldap连接参数!!!!");
        } else {
            try {
                ctx = new InitialDirContext(env);
                log.info("ldap 连接开启！！");
            } catch (NamingException e) {
                log.info("创建ldap连接不成功！");
                e.printStackTrace();
            }
        }
        return ctx;
    }

    /**
     *
     * @param attrs
     * @param attributeName     属性名称
     * @return
     * @throws NamingException
     */
    public String getAttrValue(javax.naming.directory.Attributes attrs, String attributeName) {
        String att = new String("") ;
        Attribute attribute = attrs.get(attributeName);
        try{
            for (NamingEnumeration all = attribute.getAll(); all.hasMoreElements();) {
                Object o = all.nextElement();
                att = o.toString();
            }
        }catch (Exception e){
            log.info(e.getMessage());
            return null;
        }
        return  att;
    }

    
    public  String importRaUser() throws IOException, NamingException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String baseDN = request.getParameter("baseDN");
        String admin=request.getParameter("admin");
        String password =request.getParameter("password");
        String  ip=request.getParameter("ip");
        String port=request.getParameter("port") ;
        String json ="{success:false}";
        DirContext ctx = getLdapDirContext(ip,port,admin,password);
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration en = null;
        if(ctx!=null){
            //3已发证 4吊销 5废除
            en = ctx.search(baseDN, "(&(objectClass=hzihuser)(|(hzihcastatus=2)(hzihcastatus=3)(hzihcastatus=4)(hzihcastatus=5)))",constraints);
            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    javax.naming.directory.Attributes attribute = si.getAttributes();
                    CaUser caUser = new CaUser();
                    String cn= getAttrValue(attribute,"cn");
                    caUser.setCn(cn);
                    String dn =  si.getNameInNamespace();
                    caUser.setHzihdn(dn);
                    String hzihprovince= getAttrValue(attribute,"hzihprovince");
                    caUser.setHzihprovince(hzihprovince);
                    String hzihpassword= getAttrValue(attribute,"hzihpassword");
                    caUser.setHzihpassword(hzihpassword);
//                    String hzihcity= getAttrValue(attribute,"hzihcity");
//                    caUser.setHzihcity(hzihcity);
//                    String hzihorganization= getAttrValue(attribute,"hzihorganization");
//                    caUser.setHzihorganization(hzihorganization);
//                    String hzihinstitutions= getAttrValue(attribute,"hzihinstitutions");
//                    caUser.setHzihinstitutions(hzihinstitutions);
                    String hzihcastatus= getAttrValue(attribute,"hzihcastatus");
                    caUser.setHzihcastatus(hzihcastatus);
                    String hzihcaserialNumber= getAttrValue(attribute,"hzihcaserialNumber");
                    caUser.setHzihcaserialNumber(hzihcaserialNumber);
                    String hzihparentca= getAttrValue(attribute,"hzihparentca");
                    caUser.setHzihparentca(hzihparentca);
                    String hzihcertificatetype= getAttrValue(attribute,"hzihcertificatetype");
                    caUser.setHzihcertificatetype(hzihcertificatetype);
                    String hzihid= getAttrValue(attribute,"hzihid");
                    caUser.setHzihid(hzihid);
                    String hzihphone= getAttrValue(attribute,"hzihphone");
                    caUser.setHzihphone(hzihphone);
                    String hzihaddress= getAttrValue(attribute,"hzihaddress");
                    caUser.setHzihaddress(hzihaddress);
                    String hzihemail= getAttrValue(attribute,"hzihemail");
                    caUser.setHzihemail(hzihemail);
                    String hzihjobnumber= getAttrValue(attribute,"hzihjobnumber");
                    caUser.setHzihjobnumber(hzihjobnumber);
                    String hzihcavalidity= getAttrValue(attribute,"hzihcavalidity");
                    caUser.setHzihcavalidity(hzihcavalidity);
                    try {
                       CaUser checkCaUser = caUserService.findBySerialNumber(hzihcaserialNumber);
                        if (checkCaUser != null){
                            checkCaUser.setCn(cn);
                            checkCaUser.setHzihdn(dn);
                            checkCaUser.setHzihprovince(hzihprovince);
                            checkCaUser.setHzihpassword(hzihpassword);
//                            checkCaUser.setHzihcity(hzihcity);
//                            checkCaUser.setHzihorganization(hzihorganization);
//                            checkCaUser.setHzihinstitutions(hzihinstitutions);
                            checkCaUser.setHzihcastatus(hzihcastatus);
                            checkCaUser.setHzihcaserialNumber(hzihcaserialNumber);
                            checkCaUser.setHzihparentca(hzihparentca);
                            checkCaUser.setHzihcertificatetype(hzihcertificatetype);
                            checkCaUser.setHzihid(hzihid);
                            checkCaUser.setHzihphone(hzihphone);
                            checkCaUser.setHzihaddress(hzihaddress);
                            checkCaUser.setHzihemail(hzihemail);
                            checkCaUser.setHzihjobnumber(hzihjobnumber);
                            checkCaUser.setHzihcavalidity(hzihcavalidity);
                            caUserService.modify(checkCaUser);
                        }else  {
                            CaUser idUser = caUserService.checkUserName(cn);
                            if(idUser!=null){
                                caUser.setId(idUser.getId());
                                caUser.setPhonenetid(idUser.getPhonenetid());
                                caUser.setTerminal_pwd(idUser.getTerminal_pwd());
                                caUser.setTerminalid(idUser.getTerminalid());
                                caUser.setTerminal_pwd_audit(idUser.getTerminal_pwd_audit());
                                caUser.setStatus(idUser.getStatus());
                                caUser.setCreatedate(idUser.getCreatedate());
                                caUser.setLogindate(idUser.getLogindate());
                                caUser.setOnlinetime(idUser.getOnlinetime());
                                caUserService.modify(caUser);
                            } else {
                                caUser.setCreatedate(new Date());
                                caUserService.add(caUser);
                            }
                        }
                    } catch (Exception e) {
                        log.info(e.getMessage());
                    }
                }
                ctx.close();
                String msg = "导入Ca用户成功!";
                json = "{success:true,msg:'"+msg+"'}";
            }
        }
        actionBase.actionEnd(response,json,result);
        return null;
    }
}


