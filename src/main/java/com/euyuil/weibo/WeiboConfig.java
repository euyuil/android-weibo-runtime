package com.euyuil.weibo;

/**
 * Created by Yue on 13-7-31.
 */

public class WeiboConfig {

    private static String clientId = "your_client_id";
    private static String clientSecret = "your_client_secret";

    public static void reset(String clientId, String clientSecret) {
        WeiboConfig.clientId = clientId;
        WeiboConfig.clientSecret = clientSecret;
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public static String getClientId() {
        return clientId;
    }
}
