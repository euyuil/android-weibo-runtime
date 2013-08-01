package com.euyuil.weibo;

import com.euyuil.weibo.json.AccessToken;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Yue on 13-7-28.
 */

public class WeiboIdentity implements Serializable {

    private static final String PROVIDER = "weibo";

    private String userId;
    private String accessToken;
    private Date expireDate;

    public WeiboIdentity() {
    }

    // TODO Re-consider this one.
    public WeiboIdentity(String universal)
            throws WeiboInvalidIdentityException, WeiboInvalidProviderException {

        String[] parts = universal.split("/");

        if (parts.length != 2) {
            throw new WeiboInvalidIdentityException();
        }

        String provider = parts[0];

        if (provider.equals(PROVIDER)) {
            this.userId = parts[1];
        } else {
            throw new WeiboInvalidProviderException();
        }
    }

    public WeiboIdentity(AccessToken accessToken) {
        this.userId = accessToken.uid;
        this.accessToken = accessToken.access_token;
        this.expireDate = calculateExpireDate(new Date(), Integer.parseInt(accessToken.expires_in));
    }

    private Date calculateExpireDate(Date now, int seconds) {
        /*
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.SECOND, seconds);
        return c.getTime();
        */
        return new Date(now.getTime() + seconds * 1000); // TODO Find some way more elegant.
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getProvider() {
        return PROVIDER;
    }

    @Override
    public String toString() {
        return PROVIDER + "/" + userId;
    }
}
