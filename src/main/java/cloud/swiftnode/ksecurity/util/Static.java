package cloud.swiftnode.ksecurity.util;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.abstraction.mock.MockPlugin;
import cloud.swiftnode.ksecurity.abstraction.mock.MockSender;
import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.gui.KTray;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.executor.DebugSpamExecutor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.executor.PunishSpamExecutor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
import java.util.UUID;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class Static {
    public static void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(KSecurity.inst, runnable);
    }

    public static void runTaskLaterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(KSecurity.inst, runnable, delay);
    }

    public static void runTaskTimerAsync(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(KSecurity.inst, runnable, delay, period);
    }

    public static void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(KSecurity.inst, runnable);
    }

    public static void consoleMsg(String... msgs) {
        CommandSender sender = Bukkit.getConsoleSender();
        if (sender == null) {
            sender = new MockSender();
        }
        for (String msg : msgs) {
            sender.sendMessage(msg);
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
        return readAllText(url, "K-Security v" + StaticStorage.getCurrVer());
    }

    public static String substring(String target, String a, String b) {
        String parse = target.substring(target.indexOf(a) + a.length());
        return parse.substring(0, parse.indexOf(b));
    }

    public static String getVersion() {
        return KSecurity.inst.getDescription().getVersion();
    }

    public static FileConfiguration getConfig() {
        return KSecurity.inst.getConfig();
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

    public static Plugin getRequestPlugin() {
        StackTraceElement[] elements = new Exception().getStackTrace();

        for (int i = 2; i < elements.length; i++) {
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

    public static String getModulesInfo(boolean prefix) {
        String ret = "";
        for (Module module : KSecurity.getModuleManager().getModules()) {
            if (!ret.equals("")) {
                ret += "\n";
            }
            if (prefix) {
                ret += Lang.PREFIX.toString();
            }
            ret += module.getName() + " Module: &f" + module.getSimpleVersion();
        }
        return Lang.colorize(ret);
    }

    public static void sendModulesInfo(CommandSender sender) {
        String[] splited = getModulesInfo(true).split("\n");
        for (String str : splited) {
            sender.sendMessage(str);
        }
    }

    public static boolean isOpable(Player player) {
        return !Bukkit.getPluginManager().isPluginEnabled(KSecurity.inst)
                || Config.getOpList().size() <= 0
                || player == null
                || !player.isOp()
                || Config.getOpList().contains(player.getName().toLowerCase());
    }

    public static void checkOpable(String name) {
        Player player = Bukkit.getPlayer(name);
        if (player == null) {
            try {
                player = Bukkit.getPlayer(UUID.fromString(name));
            } catch (Throwable th) {
                Static.consoleMsg(new Exception(th));
            }
        }
        checkOpable(player);
    }

    public static void checkOpable(Player player) {
        if (KSecurity.inst.isEnabled()) {
            Static.runTask(() -> {
                if (player != null
                        && !Static.isOpable(player)) {
                    player.setOp(false);
                    Static.log(Lang.DEOP.builder()
                            .single(Lang.Key.VALUE, player.getName()));
                }
            });
        }
    }

    public static void log(String text) {
        Event event = EventFactory.createFxLogEvent(text);
        Bukkit.getPluginManager().callEvent(event);
        Bukkit.broadcastMessage(text);
    }

    public static void log(Lang.MessageBuilder builder) {
        log(builder.build());
    }
}
