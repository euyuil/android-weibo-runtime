package com.euyuil.weibo;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Yue on 13-8-3.
 */

public class WeiboApiQuery {

    public static enum Method {
        GET, POST
    }

    public static class Uri {
        public static final String REDIRECT = "https://api.weibo.com/oauth2/default.html";
        public static final String AUTHORIZE = "https://api.weibo.com/oauth2/authorize";
        public static final String TOKEN = "https://api.weibo.com/oauth2/access_token";
    }

    private String requestUri;
    private Method requestMethod;
    private List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();

    private String requestResult;

    public WeiboApiQuery() {
    }

    public WeiboApiQuery(Method requestMethod, String requestUri) {
        this.requestMethod = requestMethod;
        this.requestUri = requestUri;
    }

    public WeiboApiQuery execute() {
        switch (requestMethod) {
            case GET:
                return executeGet();
            case POST:
                return executePost();
            default:
                break;
        }
        return null;
    }

    private WeiboApiQuery executeGet() {

        this.requestResult = null;

        String actualRequestUri = this.requestUri;
        if (requestParameters.size() > 0) {
            String parameterString = URLEncodedUtils.format(this.requestParameters, "utf-8");
            actualRequestUri = this.requestUri + "?" + parameterString;
        }

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(actualRequestUri);
        try {

            HttpResponse httpResponse = httpClient.execute(httpGet);

            Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
            if (scanner.hasNext()) {
                this.requestResult = scanner.next();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    private WeiboApiQuery executePost() {

        this.requestResult = null;

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(this.requestUri);
        try {

            httpPost.setEntity(new UrlEncodedFormEntity(this.requestParameters));

            HttpResponse httpResponse = httpClient.execute(httpPost);

            Scanner scanner = new Scanner(httpResponse.getEntity().getContent());
            if (scanner.hasNext()) {
                this.requestResult = scanner.next();
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public WeiboApiQuery uri(String requestUri) {
        this.requestUri = requestUri;
        return this;
    }

    public WeiboApiQuery method(Method requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    public WeiboApiQuery parameter(String key, String value) {
        requestParameters.add(new BasicNameValuePair(key, value));
        return this;
    }

    public String result() {
        return requestResult;
    }
}
