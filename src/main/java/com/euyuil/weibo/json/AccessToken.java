package com.euyuil.weibo.json;

/**
 * Created by Yue on 13-7-21.
 */

public class AccessToken {

    public String access_token; // 用于调用access_token，接口获取授权后的access token。

    public String expires_in;   // access_token的生命周期，单位是秒数。

    @Deprecated
    public String remind_in;    // access_token的生命周期（该参数即将废弃，开发者请使用expires_in）。

    public String uid;          // 当前授权用户的UID。
}
