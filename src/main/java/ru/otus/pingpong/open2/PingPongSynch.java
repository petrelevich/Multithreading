package ru.otus.pingpong.open2;


import java.util.concurrent.atomic.AtomicInteger;

public class PingPongSynch {

    private String last = "PONG";
    private final AtomicInteger counter = new AtomicInteger(1_000_000);
    private final Utils utils;

    public PingPongSynch(boolean demoMode) {
        utils = new Utils(demoMode);
        new Thread(()-> this.action("ping")).start();
        new Thread(()-> this.action("PONG")).start();
    }

    private synchronized void action(String message) {
        while (counter.get() > 0) {
            if(last.equals(message)) {
                wait(this);
            } else {
                utils.print(message);
                counter.decrementAndGet();
                last = message;
                utils.sleep();
                notify();
            }
        }
    }

    public static void main(String[] args) {
        new PingPongSynch(true);
    }

    private static void wait(Object object) {
        try {
            object.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


