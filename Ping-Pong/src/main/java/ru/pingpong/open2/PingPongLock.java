package ru.pingpong.open2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sergey
 * created on 14.10.18.
 */
public class PingPongLock {

    /*
     *   All {@code Lock} implementations <em>must</em> enforce the same
     * memory synchronization semantics as provided by the built-in monitor lock
     *
     * */

    private String last = "PONG";
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition  = lock.newCondition();
    private final AtomicInteger counter = new AtomicInteger(1_000_000);
    private final Utils utils;

    PingPongLock(boolean demoMode) {
        utils = new Utils(demoMode);
        new Thread(()-> this.action("ping")).start();
        new Thread(()-> this.action("PONG")).start();

    }

    private void action(String message) {
        try {
            while (counter.get() > 0) {
                lock.lock();
                try {
                    if(last.equals(message)) {
                        System.out.println("before await, message:" + message);
                        condition.await();
                        System.out.println("after await, message:" + message);
                    } else {
                        utils.print(message);
                        counter.decrementAndGet();
                        last = message;
                        condition.signal();
                        utils.sleep();
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        new PingPongLock(true);
    }


}
