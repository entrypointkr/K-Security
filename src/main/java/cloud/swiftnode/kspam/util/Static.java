package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.executor.DebugSpamExecutor;
import cloud.swiftnode.kspam.abstraction.executor.PunishSpamExecutor;
import cloud.swiftnode.kspam.abstraction.mock.MockPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class Static {
    public static void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(KSpam.inst, runnable);
    }

    public static void runTaskLaterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(KSpam.inst, runnable, delay);
    }

    public static void runTaskTimerAsync(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(KSpam.inst, runnable, delay, period);
    }

    public static void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(KSpam.inst, runnable);
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
        if (!Config.isAlert()) {
            return;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(stream));
        consoleMsg(Lang.EXCEPTION.builder().single(Lang.Key.EXCEPTION_MESSAGE, new String(stream.toByteArray())));
    }

    public static long time() {
        return System.currentTimeMillis();
    }

    public static String readAllText(URL url, String userContentOption) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", userContentOption);
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);
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
        return readAllText(url, "K-SPAM v" + StaticStorage.getCurrVer());
    }

    public static String substring(String target, String a, String b) {
        String parse = target.substring(target.indexOf(a) + a.length());
        return parse.substring(0, parse.indexOf(b));
    }

    public static String getVersion() {
        return KSpam.inst.getDescription().getVersion();
    }

    public static FileConfiguration getConfig() {
        return KSpam.inst.getConfig();
    }

    public static SpamExecutor getDefaultExecutor() {
        if (Config.isDebugMode()) {
            return new DebugSpamExecutor(new PunishSpamExecutor());
        }
        return new PunishSpamExecutor();
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
            Static.consoleMsg(ex);
        }
        return players;
    }

    public static boolean isRunning() {
        try {
            Field consoleField = Bukkit.getServer().getClass().getDeclaredField("console");
            consoleField.setAccessible(true);
            Object console = consoleField.get(Bukkit.getServer());
            Method runningMethod = console.getClass().getMethod("isRunning");
            return (boolean) runningMethod.invoke(console);
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return true;
        }
    }

    public static Plugin getRequestPlugin(Exception ex) {
        StackTraceElement[] elements = ex.getStackTrace();

        for (int i = 1; i < elements.length; i++) {
            StackTraceElement element = elements[i];
            try {
                ClassLoader loader = Class.forName(element.getClassName()).getClassLoader();
                if (!StaticStorage.getCachedLoaderPluginMap().containsKey(loader)) {
                    continue;
                }
                return StaticStorage.getCachedLoaderPluginMap().get(loader);
            } catch (ClassNotFoundException e) {
                // Ignore
            }
        }
        return new MockPlugin();
    }
}
