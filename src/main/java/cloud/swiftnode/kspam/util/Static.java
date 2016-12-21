package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.abstraction.Checker;
import cloud.swiftnode.kspam.storage.StaticStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URL;
import java.util.Collection;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class Static {
    public static String convertToIp(InetAddress addr) {
        return convertToIp(addr.toString());
    }

    public static String convertToIp(String addr) {
        return addr.substring(addr.indexOf("/") + 1);
    }

    public static void removePlayerInStorage(Player p) {
        StaticStorage.getPlayerSet().remove(p.getName());
    }

    public static URL toUrl(String str) {
        URL url = null;
        try {
            url = new URL(str);
        } catch (Exception ex) {
            // Ignore
        }
        return url;
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

    public static void consoleMsg(String str) {
        Bukkit.getConsoleSender().sendMessage(str);
    }

    public static void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(KSpam.getInst(), runnable);
    }

    public static void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(KSpam.getInst(), runnable);
    }

    public static void runTaskAsync(final Checker checker) {
        runTaskAsync(new Runnable() {
            @Override
            public void run() {
                checker.check();
            }
        });
    }

    public static void msgLineLoop(CommandSender sender, String msg) {
        for (String element : msg.split("\n")) {
            sender.sendMessage(element);
        }
    }

    @SuppressWarnings("unchecked")
    public static Player[] getOnlinePlayers() {
        Player[] players = new Player[0];
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
        }
        return players;
    }

    public static String substring(String str, String a, String b) {
        String temp = str.substring(str.indexOf(a) + a.length());
        return temp.substring(0, str.indexOf(b));
    }
}
