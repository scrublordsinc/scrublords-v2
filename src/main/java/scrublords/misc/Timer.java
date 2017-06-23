package scrublords.misc;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class Timer implements Runnable {
    public int counter;
    private AtomicBoolean flag;

    public Timer(AtomicBoolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        while (flag.get()) {
            try {
                Thread.sleep(1000);
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        flag.set(false);
    }
}
