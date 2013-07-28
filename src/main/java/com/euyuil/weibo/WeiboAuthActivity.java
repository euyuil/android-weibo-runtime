package com.euyuil.weibo;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.euyuil.weibo.json.AccessToken;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeiboAuthActivity extends Activity {

    protected static final String REDIRECT_URI = "https://api.weibo.com/oauth2/default.html";
    protected static final String AUTHORIZE_URI = "https://api.weibo.com/oauth2/authorize";
    protected static final String TOKEN_URI = "https://api.weibo.com/oauth2/access_token";

    private static class AcquireTokensFromCode extends AsyncTask<Void, Void, String> {

        private String clientId, clientSecret, code;

        public AcquireTokensFromCode(String clientId, String clientSecret, String code) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.code = code;
        }

        @Override
        protected void onPostExecute(String s) {
            AccessToken accessToken = new Gson().fromJson(s, AccessToken.class);
        }

        @Override
        protected String doInBackground(Void... voids) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(TOKEN_URI);
            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                nameValuePairs.add(new BasicNameValuePair("client_id", clientId));
                nameValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));
                nameValuePairs.add(new BasicNameValuePair("grant_type", "authorization_code"));
                nameValuePairs.add(new BasicNameValuePair("code", code));
                nameValuePairs.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response;
                response = httpclient.execute(httpPost);

                Scanner scanner = new Scanner(response.getEntity().getContent());
                if (scanner.hasNext()) {
                    String result = scanner.next();
                    return result;
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_auth);

        WebView webView = (WebView) findViewById(R.id.webView);

        final String clientId = getIntent().getStringExtra("client_id");
        final String clientSecret = getIntent().getStringExtra("client_secret");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.startsWith(REDIRECT_URI)) {
                    Matcher matcher = Pattern.compile("\\?code=(.*)$").matcher(url);
                    if (matcher.find()) {
                        final String code = matcher.group(1);
                        new AcquireTokensFromCode(clientId, clientSecret, code).execute();
                    }
                }
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);

        try {

            webView.loadUrl(AUTHORIZE_URI +
                    "?client_id=" + clientId +
                    "&display=mobile" +
                    "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, "utf-8")
            );

        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weibo_auth, menu);
        return true;
    }
    
}