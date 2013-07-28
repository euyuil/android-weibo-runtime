package com.euyuil.weibo;

import com.euyuil.weibo.json.AccessToken;

import java.util.Date;

/**
 * Created by Yue on 13-7-28.
 */

public class WeiboIdentity {

    private String userId;
    private String accessToken;
    private Date expireDate;

    public WeiboIdentity() {
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
        return new Date(now.getTime() + seconds * 1000);
    }
}
