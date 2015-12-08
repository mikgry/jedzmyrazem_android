package mikolajgrygiel.jedzmyrazem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class ServiceHandler {

    private String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public String makeServiceCall(String url, int method,
                                  JSONObject params, String paramsString) {
        try {
            HttpEntity httpEntity;
            HttpResponse httpResponse = null;

            if (method == POST) {
                httpResponse = sendPostRequest(url, params);
            } else if (method == GET) {
                httpResponse = sendGetRequest(url, paramsString);
            }
            try {
                httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private HttpResponse sendPostRequest(String url, JSONObject params) throws IOException {
        HttpResponse httpResponse;HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        if (params != null) {
            String params_string = params.toString();
            httpPost.setEntity(new StringEntity(params_string));
        }
        httpResponse = LoginActivity.httpClient.execute(httpPost);
        return httpResponse;
    }

    private HttpResponse sendGetRequest(String url, String paramsString) throws IOException {
        HttpResponse httpResponse;
        if (paramsString != null) {
            url += "?" + paramsString;
        }
        HttpGet httpGet = new HttpGet(String.valueOf(new URL(url)));
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        httpResponse = LoginActivity.httpClient.execute(httpGet);
        return httpResponse;
    }
}