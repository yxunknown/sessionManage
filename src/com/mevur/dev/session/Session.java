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
        attr = new HashMap<String, Object>();
    }

    public void setAttribute(String key, Object value) {
        this.attr.put(key, value);
    }
    public Object getAttribute(String key) {
        return this.attr.get(key);
    }

    public void setAttribute(String key, Integer value) {
        this.attr.put(key, value);
    }
    public Integer getAttribute(String key) {
        return (Integer) this.attr.get(key); 
    }

}
