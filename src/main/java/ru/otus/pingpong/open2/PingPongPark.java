package ru.otus.pingpong.open2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;


/**
 * @author sergey
 * created on 14.10.18.
 */
public class PingPongPark {
//https://docs.oracle.com/javase/10/docs/api/java/util/concurrent/locks/LockSupport.html

    private AtomicReference<String> last = new AtomicReference<>("PONG");
    private AtomicReference<Thread> parkedThread = new AtomicReference<>();
    private final Utils utils;
    private final AtomicInteger counter = new AtomicInteger(1_000_000);

    public PingPongPark(boolean demoMode) {
        utils = new Utils(demoMode);
        new Thread(()-> this.action("ping")).start();
        new Thread(()-> this.action("PONG")).start();
    }


    public void action(String message) {
        while (counter.get() > 0) {
            if (last.get().equals(message)) {
                parkedThread.set(Thread.currentThread());
//              Disables the current thread for thread scheduling purposes unless the permit is available.
                LockSupport.park();
                if (Thread.interrupted()) {
                    System.out.println("Thread was interrupted. do nothing");
                }
            } else {
                utils.print(message);
                counter.decrementAndGet();
                last.set(message);
                utils.sleep();
                if (parkedThread.get() != null) {
                    LockSupport.unpark(parkedThread.get()); //order!!!
                }
            }

        }
    }

    public static void main(String[] args) {
        new PingPongPark(true);
    }

    static {
        // Reduce the risk of "lost unpark" due to classloading
        //https://bugs.java.com/view_bug.do?bug_id=8074773
        Class<?> ensureLoaded = LockSupport.class;
    }


}
