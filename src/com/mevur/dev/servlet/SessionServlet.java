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
import java.util.Set;
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
        Session s = sessionHelper.getSession(session.getId());
        if (null != s && s.validate()) {
           //update session
            s.refresh();
        } else if (null != s && !s.validate()){
           session.invalidate();
           session = req.getSession(true);
        } else {
            s = new Session(session.getId());
            sessionHelper.add(s.getSessionId(), s);
        }
        Set<String> keys = req.getParameterMap().keySet();
        for (String key : keys) {
            System.out.println(key + req.getParameterMap().get(key));
        }

        logger.log(Level.INFO, s.toString());
        PrintWriter writer = resp.getWriter();
        writer.write(s.toString());
        writer.flush();
        writer.close();
    }
}
