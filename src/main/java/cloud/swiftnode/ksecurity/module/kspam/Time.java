package cloud.swiftnode.ksecurity.module.kspam;

/**
 * Created by Junhyeong Lim on 2017-09-15.
 */
public class Time {
    private final long time;

    public Time() {
        this.time = System.currentTimeMillis() + 30 * 1000;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= time;
    }
}
