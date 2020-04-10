package pl.jee.klos.service2;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReCaptchaService {
	
	public Boolean validateReCaptcha(String captchaResponse){
		
		
        
        HttpPost httpPost = new HttpPost("https://www.google.com/recaptcha/api/siteverify");

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        
        nameValuePair.add(new BasicNameValuePair("response", captchaResponse));
        nameValuePair.add(new BasicNameValuePair("secret", "6LeuO2EUAAAAANiB0xbiW1bF_3HvT8yWNDLe1H4W"));
        CloseableHttpClient client = HttpClients.createDefault();
        
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
            CloseableHttpResponse httpResponse = client.execute(httpPost);
            JSONObject response = new JSONObject(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
            client.close();
            return (Boolean) response.get("success");
        }
        catch(Exception ex) {
            return false;
        }
    }

}
