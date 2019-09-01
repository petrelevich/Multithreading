package ru.pingpong.open2;

/**
 * @author sergey
 * created on 16.10.18.
 */
class Utils {
    private final boolean demoMode;

    Utils(boolean demoMode) {
        this.demoMode = demoMode;
    }

    void print(String msg) {
        if (this.demoMode) {
            System.out.println(msg);
        }
    }

    void sleep() {
        if (!demoMode) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
