package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.storage.PlayerStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class Static {
    public static String convertToIp(InetAddress addr) {
        return addr.toString().replace("/", "");
    }

    public static void removePlayerInStorage(Player p) {
        PlayerStorage.getInst().getPlayerSet().remove(p.getName());
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

    public static int toIntVer(String ver) {
        return Integer.parseInt(ver.replace(".", ""));
    }

    public static void consoleMsg(String str) {
        Bukkit.getConsoleSender().sendMessage(str);
    }

    public static void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(KSpam.getInst(), runnable);
    }
}
