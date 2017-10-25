package kr.rvs.ksecurity.antibot;

import kr.rvs.ksecurity.util.Static;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class TransactionLatch {
    private final Map<String, CountDownLatch> latchMap = new HashMap<>();

    public boolean await(Player player) {
        CountDownLatch latch = latchMap.computeIfAbsent(player.getName(), k -> new CountDownLatch(1));
        if (latch != null) {
            try {
                return latch.await(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Static.log(e);
            }
        }
        return false;
    }

    public void countDown(Player player) {
        CountDownLatch latch = latchMap.get(player.getName());
        if (latch != null) {
            latch.countDown();
        }
    }

    public void release(Player player) {
        latchMap.remove(player.getName());
    }
}
