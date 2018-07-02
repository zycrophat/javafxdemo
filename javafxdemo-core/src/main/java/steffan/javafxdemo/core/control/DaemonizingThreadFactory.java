package steffan.javafxdemo.core.control;

import java.util.Random;
import java.util.concurrent.ThreadFactory;

public class DaemonizingThreadFactory implements ThreadFactory {

    private ThreadFactory threadFactory;

    public DaemonizingThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = threadFactory.newThread(r);
        t.setName("App_Thread_" + new Random().nextInt());
        t.setDaemon(true);
        return t;
    }
}
