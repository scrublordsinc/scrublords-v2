package scrublords.main;

import java.util.Random;

/**
 * @author Denis Dimitrov <denis.k.dimitrov@gmail.com>.
 */
public class Demo {
    public static void main(String[] args) {
        Random r = new Random();
        int Low = 400/2;
        int High = 800/2;
        int Result = r.nextInt(High-Low) + Low;
        System.out.println(Result);
    }
}
