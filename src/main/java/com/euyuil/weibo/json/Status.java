package com.euyuil.weibo.json;

import java.util.Date;

/**
 * Created by Yue on 13-8-5.
 */

public class Status {

    public Date created_at;

    public Integer id;
    public String text;
    public String source;
    public Boolean favorited;
    public Boolean truncated;
    public String in_reply_to_status_id;
    public String in_reply_to_user_id;
    public String in_reply_to_screen_name;
//    "geo": null,
//    "mid": "5612814510546515491",
    public Integer reposts_count;
    public Integer comments_count;
//    "annotations": [],

    public User user;
}
