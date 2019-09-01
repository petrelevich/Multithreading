package ru.pingpong.open2;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author sergey
 * created on 15.10.18.
 */
public class PingPongAtomic {
    private static final String[] MESSAGES = {"ping", "PONG"};
    private final AtomicInteger lastIdx = new AtomicInteger(1);
    private final AtomicInteger counter = new AtomicInteger(1_000_000);
    private final Utils utils;


    PingPongAtomic(boolean demoMode) {
        utils = new Utils(demoMode);
        new Thread(()-> this.action(1, 0)).start();
        new Thread(()-> this.action(0, 1)).start();

    }

    private void action(int msgIdxLast, int msgIdxNew) {
        while (counter.get() > 0) {
            if (lastIdx.compareAndSet(msgIdxLast, msgIdxNew)) {
                utils.print(MESSAGES[msgIdxNew]);
                counter.decrementAndGet();
                utils.sleep();
            }
        }
    }

    public static void main(String[] args) {
        new PingPongAtomic(true);
    }
}
