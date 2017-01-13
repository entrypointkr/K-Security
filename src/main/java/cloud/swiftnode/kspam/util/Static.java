package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.executor.DebugSpamExecutor;
import cloud.swiftnode.kspam.abstraction.executor.PunishSpamExecutor;
import cloud.swiftnode.kspam.abstraction.sender.MockCommandSender;
import cloud.swiftnode.kspam.listener.ProtocolListener;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class Static {
    public static void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(KSpam.INSTANCE, runnable);
    }

    public static void runTaskLaterAsync(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(KSpam.INSTANCE, runnable, delay);
    }


    public static void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(KSpam.INSTANCE, runnable);
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        Bukkit.getScheduler().runTaskLater(KSpam.INSTANCE, runnable, delay);
    }

    public static void runTaskTimerAsync(Runnable runnable, long delay, long period) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(KSpam.INSTANCE, runnable, delay, period);
    }

    public static void consoleMsg(String... msgs) {
        CommandSender sender = Bukkit.getConsoleSender() == null ? new MockCommandSender() : Bukkit.getConsoleSender();
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
        return readAllText(url, "K-SPAM v" + StaticStorage.getCurrVer());
    }

    public static String substring(String target, String a, String b) {
        String parse = target.substring(target.indexOf(a) + a.length());
        return parse.substring(0, parse.indexOf(b));
    }

    public static String getVersion() {
        return KSpam.INSTANCE.getDescription().getVersion();
    }

    public static FileConfiguration getConfig() {
        if (KSpam.INSTANCE != null) {
            return KSpam.INSTANCE.getConfig();
        }
        return new YamlConfiguration();
    }

    public static SpamExecutor getDefaultExecutor() {
        if (Static.getConfig().getBoolean(Config.DEBUG_MODE, false)) {
            return new DebugSpamExecutor(new PunishSpamExecutor());
        }
        return new PunishSpamExecutor();
    }

    public static void protocolLibHook() {
        ProtocolLibHook.register(KSpam.INSTANCE);
    }

    private static class ProtocolLibHook {
        static void register(Plugin plugin) {
            int packetId = 35;
            try {
                Class packetTypePlayServer = Class.forName("com.comphenix.protocol.PacketType$Play$Server");
                Field loginPacketTypeField = packetTypePlayServer.getField("LOGIN");
                Object loginPacketType = loginPacketTypeField.get(null);
                Field currentIdField = loginPacketType.getClass().getDeclaredField("currentId");
                currentIdField.setAccessible(true);
                packetId = currentIdField.getInt(loginPacketType);
                System.out.println("Packet ID: " + packetId);
            } catch (Throwable th) {
                th.printStackTrace();
            }
            ProtocolLibrary.getProtocolManager().addPacketListener(new ProtocolListener(plugin, packetId));
        }
    }
}
