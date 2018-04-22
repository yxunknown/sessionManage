package com.mevur.dev.session;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mevur on 2018/4/1.
 */
public class Session {
    private String sessionId;
    private Date createTime;
    private Date lastAccessTime;
    private long expire;
    private long live;
    private long remain;
    private Map<String, Object> attr;

    public Session(String sessionId) {
        //assign sessionId
        this.sessionId = sessionId;
        //init create time & lastAccessTime
        this.createTime = new Date(System.currentTimeMillis());
        this.lastAccessTime = createTime;
        //default
        //time unit: s
        this.expire = 1800;
        this.live = 0;
        this.remain = expire - live;
        attr = new HashMap<>();
    }

    public Session(String sessionId, long expire) {
        //init session id
        this.sessionId = sessionId;
        //init create time & last access time
        this.createTime = new Date(System.currentTimeMillis());
        this.lastAccessTime = createTime;
        //init expire time
        //default time unit : s
        this.expire = expire;
        //init live time
        this.live = 0;
        this.remain = expire - live;
        attr = new HashMap<>();
    }

    public void setAttribute(String key, Object value) {
        this.attr.put(key, value);
    }
    public Object getAttribute(String key) {
        return this.attr.get(key);
    }

    public void refresh() {
        Date now = new Date(System.currentTimeMillis());
        this.live = (now.getTime() - this.createTime.getTime()) / 1000;
        this.remain = this.expire - this.live;
        this.lastAccessTime = new Date(System.currentTimeMillis());
    }
    public boolean validate() {
        return this.remain > 0;
    }
    @Override
    public String toString() {
        return "{" +
                "sessionId:" + sessionId + "," +
                "createTime:" + createTime + "," +
                "lastAccessTime:" + lastAccessTime + "," +
                "expire:" + expire + "," +
                "live:" + live + "," +
                "remain:" + remain + "," +
                "attr:" + attr + "}";
    }

    public String getSessionId() {
        return sessionId;
    }
}
