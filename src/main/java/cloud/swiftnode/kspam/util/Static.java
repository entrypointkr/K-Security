package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class Static {
    public static void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(KSpam.getInst(), runnable);
    }

    public static void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(KSpam.getInst(), runnable);
    }

    public static void runTaskLaterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(KSpam.getInst(), runnable, delay);
    }

    public static void runTaskTimerAsync(final Runnable runnable, int delay, int period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(KSpam.getInst(), runnable, delay, period);
    }

    public static void msgLineLoop(CommandSender sender, String msg) {
        for (String element : msg.split("\n")) {
            sender.sendMessage(element);
        }
    }
}
