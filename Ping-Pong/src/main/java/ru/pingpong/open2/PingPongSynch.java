package ru.pingpong.open2;


import java.util.concurrent.atomic.AtomicInteger;

public class PingPongSynch {

    private String last = "PONG";
    private final AtomicInteger counter = new AtomicInteger(1_000_000);
    private final Utils utils;

    PingPongSynch(boolean demoMode) {
        utils = new Utils(demoMode);
        new Thread(()-> this.action("ping")).start();
        new Thread(()-> this.action("PONG")).start();
    }

    private synchronized void action(String message) {
        try {
            while (counter.get() > 0) {
                if(last.equals(message)) {
                    this.wait();
                } else {
                    utils.print(message);
                    counter.decrementAndGet();
                    last = message;
                    utils.sleep();
                    notifyAll();
                }
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        new PingPongSynch(true);
    }
}


