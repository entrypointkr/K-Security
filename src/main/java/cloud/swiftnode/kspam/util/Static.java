package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class Static {
    public static void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(KSpam.INSTANCE, runnable);
    }


    public static void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(KSpam.INSTANCE, runnable);
    }

    public static void consoleMsg(String... msgs) {
        for (String msg : msgs) {
            Bukkit.getConsoleSender().sendMessage(msg);
        }
    }

    public static void consoleMsg(Lang.MessageBuilder... builders) {
        for (Lang.MessageBuilder builder : builders) {
            consoleMsg(builder.build());
        }
    }

    public static void consoleMsg(Exception ex) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(stream));
        consoleMsg(Lang.EXCEPTION.builder().single(Lang.Key.EXCEPTION_MESSAGE, new String(stream.toByteArray())).prefix());
    }

    public static long time() {
        return System.currentTimeMillis();
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
        return temp.cloud.swiftnode.kspam.KSpam.INSTANCE.getDescription().getVersion();
    }
}
