
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class OcspUtilsTest {
    public static String serviceUrl = "http://172.16.2.8:8080/ra/OcspAction_ocsp.action";

    public static void main(String args[])throws Exception{
        KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream("D:\\cert\\User.pfx");
        char[] nPassword = null;
        nPassword = "123qwe".toCharArray();
        ks.load(fis, nPassword);
        fis.close();
//        System.out.println("keystore type=" + ks.getType() + "   证书条数：" + ks.size());
        String keyAlias = null;
        Certificate cert=null ;
        Enumeration enumeration = ks.aliases();
        X509Certificate x509=null;
        if  (enumeration.hasMoreElements())
        {
            keyAlias = (String)enumeration.nextElement();
//            System.out.println("alias=[" + keyAlias + "]");
//            System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
            cert = ks.getCertificate(keyAlias);
            x509= (X509Certificate) cert;
        }
        String  subjectX500Principal  = x509.getSubjectDN().toString();
        String  notBefore=String.valueOf(x509.getNotBefore().getTime());         //得到开始有效日期
        String  notAfter=String.valueOf(x509. getNotAfter().getTime());          //得到截止日期
//          String serialNumber=x509.getSerialNumber().toString(16);                 //得到序列号   16进制
        String serialNumber=x509.getSerialNumber().toString();                   //得到序列号
        String issuerX500Principal=x509.getIssuerDN().toString();      //得到发行者名
        String sigAlgName=x509.getSigAlgName();           //得到签名算法
        String sigAlgOID=x509.getSigAlgOID();             //得到公钥算法
        PublicKey publicKey=x509.getPublicKey();          //得到公钥算法
        String base64cert=null;
        base64cert=new String(Base64.encodeBase64(cert.getEncoded()));
        String[][] params = new String[][] {
                {"subjectX500Principal",subjectX500Principal},
                {"notBefore",notBefore} ,
                {"notAfter",notAfter}  ,
                {"serialNumber",serialNumber}   ,
                {"issuerX500Principal",issuerX500Principal}    ,
                {"sigAlgName",sigAlgName}     ,
                {"sigAlgOID",sigAlgOID}     ,
                {"base64cert",base64cert}     ,
                {"publicKey",publicKey.toString()}
        };
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout( 10 * 1000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5 * 1000);
        PostMethod post = new PostMethod(serviceUrl);
        post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5 * 1000);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        for (String[] param : params) {
            post.addParameter(param[0], param[1]);
        }
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(post);
//            System.out.println("statusCode=="+statusCode);
            if (statusCode == 200) {
//                System.out.println("数据:" + post.getResponseBodyAsString());
//                System.out.println("charset:" + post.getResponseCharSet());
            }
        } catch (Exception e) {
//            System.out.println("访问接口失败::"+e.getMessage());
        }
    }
}
