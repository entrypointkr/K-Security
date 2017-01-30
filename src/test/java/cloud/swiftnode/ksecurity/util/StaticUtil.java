package cloud.swiftnode.ksecurity.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created by Junhyeong Lim on 2017-01-18.
 */
public class StaticUtil {
    private static Random random = new Random();

    public static InetAddress getRandomAddr() throws UnknownHostException {
        byte[] addrs = new byte[4];
        getRandom().nextBytes(addrs);
        return InetAddress.getByAddress(addrs);
    }

    public static String getRandomName() {
        int max = 12;
        char[] seed = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ".toCharArray();
        char[] nameChars = new char[max];
        for (int i = 0; i < max; i++) {
            nameChars[i] = seed[getRandom().nextInt(seed.length)];
        }
        return new String(nameChars);
    }

    public static Random getRandom() {
        return random;
    }
}
