package com.euyuil.weibo;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Yue on 13-8-3.
 */

public class WeiboApiQuery {

    public enum METHOD {
        GET, POST
    }

    private String requestUrl;
    private METHOD requestMethod;
    private List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();

    private String requestResult;

    public WeiboApiQuery() {
    }

    public WeiboApiQuery(METHOD requestMethod, String requestUrl) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
    }

    public WeiboApiQuery execute() {

        this.requestResult = null;

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(requestUrl);
        try {

            httpPost.setEntity(new UrlEncodedFormEntity(requestParameters));

            HttpResponse response;
            response = httpClient.execute(httpPost);

            Scanner scanner = new Scanner(response.getEntity().getContent());
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

    public WeiboApiQuery url(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    public WeiboApiQuery method(METHOD requestMethod) {
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
