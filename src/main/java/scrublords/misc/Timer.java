package scrublords.misc;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class Timer implements Runnable {
    public int seconds;
    public int minutes;
    private AtomicBoolean flag;

    public Timer(AtomicBoolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        while (flag.get()) {
            try {
                Thread.sleep(1000);
                seconds++;
                if (seconds == 60) {
                    minutes++;
                    seconds = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        flag.set(false);
    }
}
