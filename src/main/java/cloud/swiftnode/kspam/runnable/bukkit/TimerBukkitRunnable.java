package cloud.swiftnode.kspam.runnable.bukkit;

import cloud.swiftnode.kspam.runnable.CheckRunnable;
import cloud.swiftnode.kspam.runnable.ProcessRunnable;
import cloud.swiftnode.kspam.storage.CheckStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Created by EntryPoint on 2016-12-19.
 */
public class TimerBukkitRunnable extends BukkitRunnable {
    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        Player[] players;
        // Reflect
        try {
            Method method = Bukkit.class.getMethod("getOnlinePlayers");
            Object ret = method.invoke(null);
            if (ret instanceof Collection) {
                players = ((Collection<? extends Player>) ret).toArray(new Player[0]);
            } else if (ret instanceof Player[]) {
                players = (Player[]) ret;
            } else {
                throw new IllegalArgumentException("Illegal return type");
            }
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage(
                    Lang.PREFIX + Lang.EXCEPTION.toString(ex.getMessage()));
            return;
        }
        // All player check
        for (Player p : players) {
            String ip = Static.convertToIp(p.getAddress().getAddress());
            CheckStorage storage = new CheckStorage(ip);
            new CheckRunnable(storage).run();
            new ProcessRunnable(p, storage.getResult()).run();
        }
    }
}
