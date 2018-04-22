package com.mevur.dev.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mevur on 2018/3/31.
 */
public class SessionServlet extends HttpServlet {
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("A servlet arrived:\n");
        sb.append("id: " + session.getId() + "\n")
          .append("now:" + dateFormat.format(now) + "\n")
          .append("create time:" + dateFormat.format(createTime) + "\n")
          .append("last access time:" + dateFormat.format(lastAccessTime) + "\n")
          .append("expire:" + expire + "s \n")
          .append("live:" + live + "\n")
          .append("remains:" + remains);
        logger.log(Level.INFO, sb.toString());
        PrintWriter writer = resp.getWriter();
        writer.write(sb.toString());
        writer.flush();
        writer.close();
    }
}
