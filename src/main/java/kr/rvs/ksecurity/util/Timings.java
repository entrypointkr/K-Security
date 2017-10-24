package kr.rvs.ksecurity.util;

/**
 * Created by Junhyeong Lim on 2017-10-24.
 */
public class Timings {
    private final String name;
    private long startTime = 0;

    public Timings(String name) {
        this.name = name;
    }

    public Timings start() {
        startTime = System.currentTimeMillis();
        return this;
    }

    public void end() {
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println(name + ", " + elapsed + " elapsed");
    }
}
