package com.mevur.dev.servlet;

import com.mevur.dev.session.Session;
import com.mevur.dev.util.SessionHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mevur on 2018/3/31.
 */
public class SessionServlet extends HttpServlet {
    private SessionHelper sessionHelper = SessionHelper.getInstance();
    private Logger logger = Logger.getLogger(SessionServlet.class.getName());
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        Date createTime = new Date(session.getCreationTime());
        Date lastAccessTime = new Date(session.getLastAccessedTime());
        Date now = new Date(System.currentTimeMillis());
        long live = (now.getTime() - createTime.getTime()) / 1000;
        int expire = session.getMaxInactiveInterval();
        long remains = expire - live;

        if (sessionHelper.containSession(session.getId())) {
            sessionHelper.update(session.getId());
            if (!sessionHelper.getSession(session.getId()).validate()) {
                //创建新session
                session.invalidate();
                session = req.getSession(true);
                sessionHelper.add(session.getId(), new Session(session.getId(), 20));
            }
        } else {
            sessionHelper.add(session.getId(), new Session(session.getId(), 20));
        }
        logger.log(Level.INFO, sessionHelper.getSession(session.getId()).toString());
        PrintWriter writer = resp.getWriter();
        writer.write(sessionHelper.getSession(session.getId()).toString());
        writer.flush();
        writer.close();
    }
}
