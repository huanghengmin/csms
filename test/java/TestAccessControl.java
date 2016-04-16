import com.hzih.ra.utils.ServiceResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-28
 * Time: 下午5:46
 * To change this template use File | Settings | File Templates.
 */
public class TestAccessControl {

    public static ServiceResponse callOcspService(String[][] params) {
        String requestUrl = "http://192.168.1.110:8000/ra/AccessControls_author.action";
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5 * 1000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5 * 1000);
        PostMethod post = new PostMethod(requestUrl);
        post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5 * 1000);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        for (String[] param : params) {
            post.addParameter(param[0], param[1]);
        }
        ServiceResponse response = new ServiceResponse();
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(post);
            response.setCode(statusCode);
            if (statusCode == 200) {
                String data = post.getResponseBodyAsString();
                response.setData(data);
            }
        } catch (Exception e) {
        }
        return response;
    }
    
    
    public static void main(String args[])throws Exception{
        String[][] params = new String[][] {
                {"CERT_HEX_SN","851767AED53CAA57"},
                {"username","User2"} ,
                {"uri","http://192.168.4.169:82/ra"} ,
        };
        ServiceResponse serviceResponse = callOcspService(params);
        String serviceResponseData = serviceResponse.getData();
        if(serviceResponseData!=null){
           System.out.print(serviceResponseData);
        }
    }
}
