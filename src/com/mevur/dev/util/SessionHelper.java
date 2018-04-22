package com.mevur.dev.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mevur.dev.session.Session;
import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * a servlet manage helper tool class
 * util servlet by HaspMap and
 * the <bold>key</strong> is <bold>id</bold> of servlet
 * the <bold>value</bold> is instance of javax.servlet.http.HttpSession
 * Created by mevur on 2018/3/31.
 *
 * @Author mevur
 * @Date 2018-03-31
 */
public class SessionHelper {
    private ExecutorService sessionUpdatePool;

    private final Map<String, Session> sessionMap;

    private static SessionHelper helper;

    private SessionHelper() {
        sessionMap = new HashMap<>();
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("update-session-thread-%d")
                .build();
        sessionUpdatePool = new ThreadPoolExecutor(1, 1, 2L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024),
                threadFactory, new ThreadPoolExecutor.AbortPolicy());
        init();
    }

    /**
     * get an instance of SessionHelper
     *
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
     *
     * @param sessionId check servlet id
     * @return if the servlet id contained in map : true,
     * otherwise false
     */
    public boolean containSession(String sessionId) {
        return this.sessionMap.containsKey(sessionId);
    }

    /**
     * get a servlet which id is sessionId
     *
     * @param sessionId id of servlet
     * @return a HttpSession
     */
    public synchronized Session getSession(@NotNull String sessionId) {
        return sessionMap.get(sessionId);
    }

    /**
     * update servlet with new value
     *
     * @param sessionId id of old servlet that you wanna update
     */
    public void update(@NotNull String sessionId) {
        this.sessionMap.get(sessionId).refresh();
    }

    /**
     * add a new servlet into map
     *
     * @param sessionId id of servlet
     * @param session   a HttpSession instance
     */
    public synchronized void add(@NotNull String sessionId,
                                 @NotNull Session session) {
        this.sessionMap.put(sessionId, session);
    }

    public Map<String, Session> getSessionMap() {
        return sessionMap;
    }

    private void init() {
        sessionUpdatePool.execute(() -> {
            try {
                while (true) {
                    update();
                    sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void update() {
        System.out.println("session updated:" + sessionMap.size());
        synchronized (sessionMap) {
            System.out.println("enter change area:" + sessionMap.size());
            for (Session session : sessionMap.values()) {
                session.refresh();
                if (!session.validate()) {
                    sessionMap.remove(session.getSessionId());
                }
            }
        }
    }
}
