package com.mevur.dev.util;

import com.sun.istack.internal.NotNull;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * a servlet manage helper tool class
 * util servlet by HaspMap and
 * the <bold>key</strong> is <bold>id</bold> of servlet
 * the <bold>value</bold> is instance of javax.servlet.http.HttpSession
 * Created by mevur on 2018/3/31.
 * @Author mevur
 * @Date 2018-03-31
 */
public class SessionHelper {

    private Map<String, HttpSession> sessionMap;

    private static SessionHelper helper;

    private SessionHelper() {
        sessionMap = new HashMap<String, HttpSession>();
    }

    /**
     * get an instance of SessionHelper
     * @return A SessionHelper instance
     */
    public static SessionHelper getInstance() {
        if (null == helper) {
            helper = new SessionHelper();
        }
        return helper;
    }

    /**
     * check weather if sessionId contained in map
     * @param sessionId check servlet id
     * @return if the servlet id contained in map : true,
     * otherwise false
     */
    public boolean containSession(String sessionId) {
        return this.sessionMap.containsKey(sessionId);
    }

    /**
     * get a servlet which id is sessionId
     * @param sessionId id of servlet
     * @return a HttpSession
     */
    public HttpSession getSession(@NotNull String sessionId) {
        return sessionMap.get(sessionId);
    }

    /**
     * update servlet with new value
     * @param sessionId id of old servlet that you wanna update
     * @param newSession new HttpSession value
     */
    public void update(@NotNull String sessionId,
                       @NotNull HttpSession newSession) {
        this.sessionMap.replace(sessionId, newSession);
    }

    /**
     * add a new servlet into map
     * @param sessionId id of servlet
     * @param session a HttpSession instance
     */
    public synchronized void add(@NotNull String sessionId,
                    @NotNull HttpSession session) {
        this.sessionMap.put(sessionId, session);
    }

}
