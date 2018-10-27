package ru.otus.pingpong.open2;

import java.util.concurrent.locks.LockSupport;

/**
 * @author sergey
 * created on 16.10.18.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread t = Thread.currentThread();

        System.out.println("begin");
        new Thread(() ->{

            LockSupport.unpark(t);
            System.out.println(t.getState());
        }).start();

        Thread.sleep(1000);
        LockSupport.park();

        System.out.println("end");

    }
}
