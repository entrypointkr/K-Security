package kr.rvs.ksecurity.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class Static {
    private static String address = "unknown";

    public static void init(JavaPlugin plugin) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new URL("http://checkip.amazonaws.com").openStream()));
                address = reader.readLine();
            } catch (IOException e) {
                // Ignore
            }
        });
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onLogin(PlayerLoginEvent event) throws UnknownHostException {
                String host = event.getHostname();
                String addrStr = host.substring(0, host.indexOf(':'));
                InetAddress addr = InetAddress.getByName(addrStr);
                if (!addr.isLoopbackAddress() && !addr.isSiteLocalAddress()) {
                    address = addrStr;
                    event.getHandlers().unregister(this);
                }
            }
        }, plugin);
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[K-Security] " + ChatColor.GRAY + message);
    }

    public static void log(Throwable throwable) {
        log(ChatColor.RED + "에러가 발생했습니다. 개발자에게 제보해주세요. ");
        log(ChatColor.RED + "제보 페이지: https://github.com/EntryPointKR/K-Security/issues");
        throwable.printStackTrace();
    }

    public static <T> T def(Supplier<T> valSupplier, T def) {
        try {
            T val = valSupplier.get();
            if (val != null)
                return val;
        } catch (Throwable throwable) {
            // Ignore
        }
        return def;
    }

    @SuppressWarnings("unchecked")
    public static Collection<Player> getOnlinePlayers() {
        try {
            Method onlinePlayersMethod = Bukkit.class.getMethod("getOnlinePlayers");
            Object ret = onlinePlayersMethod.invoke(null);
            return ret instanceof Collection ?
                    (Collection<Player>) ret :
                    new ArrayList<>(Arrays.asList((Player[]) onlinePlayersMethod.invoke(null)));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    public static InputStream openStream(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
        return connection.getInputStream();
    }

    public static BufferedReader openReader(URL url) throws IOException {
        return new BufferedReader(new InputStreamReader(openStream(url)));
    }

    public static String getAddress() {
        return address;
    }
}
