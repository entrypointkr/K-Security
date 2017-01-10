package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;
import org.bukkit.Bukkit;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class Static {
    public static void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(KSpam.INSTANCE, runnable);
    }
}
