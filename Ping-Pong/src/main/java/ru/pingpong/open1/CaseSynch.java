package ru.pingpong.open1;

public class CaseSynch {
    private String last = "PONG";

    private synchronized void action(String message) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if(last.equals(message)) {
                    wait();
                } else {
                    System.out.println(message);
                    last = message;
                    Thread.sleep(1_000);
                    notifyAll();
                }
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        CaseSynch pingPong = new CaseSynch();
        new Thread(()-> pingPong.action("ping")).start();
        new Thread(()-> pingPong.action("PONG")).start();
    }
}
