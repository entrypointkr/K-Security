package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class Static {
    public static void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(KSpam.INSTANCE, runnable);
    }

    public static void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(KSpam.INSTANCE, runnable);
    }

    public static void runTaskLaterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(KSpam.INSTANCE, runnable, delay);
    }

    public static void runTaskTimerAsync(final Runnable runnable, int delay, int period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(KSpam.INSTANCE, runnable, delay, period);
    }

    public static void msgLineLoop(CommandSender sender, String msg) {
        for (String element : msg.split("\n")) {
            sender.sendMessage(element);
        }
    }

    public static void consoleMsg(String msg) {
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    public static void consoleMsg(Lang.MessageBuilder builder) {
        consoleMsg(builder.build());
    }

    public static String readAllText(URL url, String userContentOption) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", userContentOption);
        connection.setConnectTimeout(3000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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

    public static String readAllText(URL url) throws IOException {
        return readAllText(url, "K-SPAM");
    }

    public static String substring(String target, String a, String b) {
        String parse = target.substring(target.indexOf(a) + a.length());
        return parse.substring(0, parse.indexOf(b));
    }

    public static String getVersion() {
        return KSpam.INSTANCE.getDescription().getVersion();
    }
}
