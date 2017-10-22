package kr.rvs.ksecurity;

import kr.rvs.ksecurity.injector.BukkitInjector;
import kr.rvs.ksecurity.util.Updater;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class UpdaterTest extends Assert {
    @Before
    public void init() {
        BukkitInjector.injectServer();
    }

    @Test
    public void update() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Updater.getDataAsync(data -> {
            if (data != null)
                latch.countDown();
        });
        latch.await(10, TimeUnit.SECONDS);
    }
}
