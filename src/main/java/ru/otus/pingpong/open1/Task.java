package ru.otus.pingpong.open1;

import java.time.LocalDateTime;

/**
 * @author sergey
 * created on 17.06.18.
 */
public class Task {
    private final Object obj = new Object();

    private void waitTask() {
        try {
            obj.wait();
            System.out.println("OK!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //waitTask()

        Task task = new Task();
        new Thread(task::action1).start();
        sleep(5_000);
        new Thread(task::action2).start();
    }

    private void action1() {
        try {
            synchronized (obj) {
                System.out.println(LocalDateTime.now() + " action1:" + Thread.currentThread().getName());
                obj.wait();
                System.out.println(LocalDateTime.now() + " action1 end");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void action2() {
        synchronized (obj) {
            System.out.println(LocalDateTime.now() + " action2:" + Thread.currentThread().getName());
            obj.notifyAll();
        }
    }

    private static void sleep(long mils) {
        try {
            Thread.sleep(mils);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
