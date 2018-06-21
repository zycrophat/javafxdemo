package steffan.javafxdemo.app.main;

import java.util.concurrent.ThreadFactory;

public class DaemonizingThreadFactory implements ThreadFactory {

    private ThreadFactory threadFactory;

    public DaemonizingThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = threadFactory.newThread(r);
        t.setDaemon(true);
        return t;
    }
}
