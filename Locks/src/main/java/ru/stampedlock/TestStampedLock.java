package ru.stampedlock;

import java.util.concurrent.locks.StampedLock;

public class TestStampedLock {
    private volatile long counter = 0;
    private final StampedLock sl = new StampedLock();

    public static void main(String[] args) throws InterruptedException {
        new TestStampedLock().go();
    }



    private void go() throws InterruptedException {
        Thread t1 = new Thread(() -> counterReader(1));
        Thread t2 = new Thread(() -> counterReader(2));
        Thread t3 = new Thread(this::counterWriter);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
    }

    private void counterReader(int id) {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                long stamp = sl.tryOptimisticRead();
                long tmp = counter;
                if (!sl.validate(stamp)) {
                    System.out.println("    id:" + id + " protected value has been changed");
                    stamp = sl.readLock();
                    System.out.println("    id:" + id + " new readLock");
                    try {
                        tmp = counter;
                    } finally {
                        sl.unlockRead(stamp);
                    }
                }
                System.out.println("    id:" + id + " current value:" + tmp);
                Thread.sleep(1_000);
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    private void counterWriter() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                long stamp = sl.writeLock();
                try {
                    long tmp = counter;
                    System.out.println("start counter modification:" + tmp);
                    Thread.sleep(10_000);
                    tmp++;
                    counter = tmp;
                    System.out.println("end counter modification:" + tmp);

                } finally {
                    sl.unlockWrite(stamp);
                }
                Thread.sleep(30_000);
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
