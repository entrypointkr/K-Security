package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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

    public static void consoleMsg(String msg) {
        Bukkit.getConsoleSender().sendMessage(colorize(msg));
    }

    public static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String readAllText(URL url) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()));
        String all = null;
        String line;
        while ((line = reader.readLine()) != null) {
            if (all != null) {
                all += line;
            } else {
                all = line;
            }
        }
        reader.close();
        return all;
    }
}
