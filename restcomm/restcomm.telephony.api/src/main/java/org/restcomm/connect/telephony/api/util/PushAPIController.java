package org.restcomm.connect.telephony.api.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laslo on 9.5.2017..
 */
public class PushAPIController {
    private static final Logger logger = Logger.getLogger(PushAPIController.class);

    private static String url = "http://localhost:3000/job/custom/create";

    public boolean sendPushRequest(String from, String to){
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("field", "username"));
            params.add(new BasicNameValuePair("value", to));
            params.add(new BasicNameValuePair("type ", "0"));
            params.add(new BasicNameValuePair("message ", "{\"type\": \"call\", \"from\": \""+from+"\"}"));

            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse response = httpclient.execute(httpPost);
            httpPost.releaseConnection();
            logger.info("Sending call push request to user \""+to+"\", from user \""+from+"\"");
            return response.getStatusLine().getStatusCode() == 201;

        } catch (ClientProtocolException e) {
            logger.error("Client Protocol Exception while sending push request ", e);
            return false;
        } catch (IOException e) {
            logger.error("IOException while sending push request ", e);
            return false;
        }
    }



}
