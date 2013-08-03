package com.euyuil.weibo;

import android.content.Context;
import android.content.Intent;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeiboAuthorizeActivity extends Activity {

    private class AcquireTokensFromCode extends AsyncTask<Void, Void, String> {

        private String code;

        public AcquireTokensFromCode(String code) {
            this.code = code;
        }

        @Override
        protected void onPostExecute(String s) {
            AccessToken accessToken = new Gson().fromJson(s, AccessToken.class);
            WeiboAuthorizeActivity.this.onAccessToken(accessToken);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return new WeiboApiQuery(WeiboApiQuery.Method.POST, WeiboApiQuery.Uri.TOKEN)
                    .parameter("client_id", WeiboConfig.getClientId())
                    .parameter("client_secret", WeiboConfig.getClientSecret())
                    .parameter("grant_type", "authorization_code")
                    .parameter("code", this.code)
                    .parameter("redirect_uri", WeiboApiQuery.Uri.REDIRECT)
                    .execute()
                    .result();
        }
    }

    private class AuthorizeWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // super.shouldOverrideUrlLoading(view, url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (url.startsWith(WeiboApiQuery.Uri.REDIRECT)) {
                Matcher matcher = Pattern.compile("\\?code=(.*)$").matcher(url);
                if (matcher.find()) {
                    final String code = matcher.group(1);
                    WeiboAuthorizeActivity.this.onGrantAccess(code);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_auth);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new AuthorizeWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        // Request authorization, next step is onGrantAccess.
        try {
            webView.loadUrl(WeiboApiQuery.Uri.AUTHORIZE +
                    "?client_id=" + WeiboConfig.getClientId() +
                    "&display=mobile" +
                    "&redirect_uri=" + URLEncoder.encode(WeiboApiQuery.Uri.REDIRECT, "utf-8")
            );

        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
    }

    protected void onGrantAccess(String code) {
        // Request access token, next step is onAccessToken.
        new AcquireTokensFromCode(code).execute();
    }

    protected void onAccessToken(AccessToken accessToken) {

        // Access token acquired. Authorization process finished.

        WeiboIdentity identity = new WeiboIdentity(accessToken);

        boolean oosError = false;
        ObjectOutputStream oos = null;
        try {
            File providerDirectory = getDir(identity.getProvider(), Context.MODE_PRIVATE);
            if (!providerDirectory.exists())
                providerDirectory.mkdirs();
            oos = new ObjectOutputStream(
                    new FileOutputStream(new File(providerDirectory, identity.getUserId())));
            oos.writeObject(identity);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            oosError = true;
        }

        if (oos != null) {
            try {
                oos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                oosError = true;
            }
        }

        if (oosError == true) {
            setResult(Activity.RESULT_CANCELED); // TODO RESULT_FAILED should be better.
        } else {

            Intent data = new Intent();

            data.putExtra("universal", identity.toString());
            data.putExtra("provider", identity.getProvider());
            data.putExtra("identity", identity.getUserId());

            setResult(Activity.RESULT_OK, data);
        }

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weibo_auth, menu);
        return true;
    }
}
