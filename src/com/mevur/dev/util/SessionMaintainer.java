/**
 * SessionMaintainer.class
 * Created in Intelij IDEA
 * <p>
 * Write Some Describe of this class here
 *
 * @author Mevur
 * @date 04/25/18 22:11
 */
package com.mevur.dev.util;

public class SessionMaintainer implements Runnable {
    private SessionHelper sessionHelper;
    private long duration;

    public SessionMaintainer(SessionHelper sessionHelper, long duration) {
        this.sessionHelper = sessionHelper;
        this.duration = duration;
    }
    @Override
    public void run() {
        while (true) {
            synchronized (sessionHelper) {
                try {
                    sessionHelper.refresh();
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
