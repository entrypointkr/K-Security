package cloud.swiftnode.kspam.runnable.bukkit;

import cloud.swiftnode.kspam.runnable.CheckRunnable;
import cloud.swiftnode.kspam.runnable.ProcessRunnable;
import cloud.swiftnode.kspam.storage.CheckStorage;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by EntryPoint on 2016-12-19.
 */
public class TimerBukkitRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Static.getOnlinePlayers()) {
            String ip = Static.convertToIp(p.getAddress().getAddress());
            CheckStorage storage = new CheckStorage(ip);
            new CheckRunnable(storage).run();
            new ProcessRunnable(p, storage.getResult()).run();
        }
    }
}
