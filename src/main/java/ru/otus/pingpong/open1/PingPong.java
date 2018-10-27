package ru.otus.pingpong.open1;


public class PingPong {

    private String last = "PONG";

    private synchronized void action(String message) {
        while (true) {
            if(last.equals(message)) {
                wait(this);
            } else {
                System.out.println(message);
                last = message;
                sleep(1_000);
                notify();
            }
        }
    }

    public static void main(String[] args) {
        PingPong pingPong = new PingPong();
        new Thread(()-> pingPong.action("ping")).start();
        new Thread(()-> pingPong.action("PONG")).start();
    }

    private static void wait(Object object) {
        try {
            object.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


