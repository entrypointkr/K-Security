package cloud.swiftnode.ksecurity.module.kspam;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.util.StaticStorage;

import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-09-15.
 */
public class FirstKickCachedMapGCThread extends Thread {
    @Override
    public void run() {
        while (KSecurity.inst.isEnabled()) {
            for (Map.Entry<String, Time> entry : StaticStorage.FIRST_KICK_CACHED_MAP.entrySet()) {
                Time time = entry.getValue();
                if (time.isExpired())
                    StaticStorage.FIRST_KICK_CACHED_MAP.remove(entry.getKey());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
