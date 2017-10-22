package kr.rvs.ksecurity.initializer;

import kr.rvs.ksecurity.util.Config;
import kr.rvs.ksecurity.util.Static;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class BStatsInitializer implements Initializer {
    @Override
    public Result check(JavaPlugin plugin, Config config) {
        return Result.ofDefault();
    }

    @Override
    public void init(JavaPlugin plugin) {
        // Main
        File configFile = new File(new File(plugin.getDataFolder().getParentFile(), "bStats"), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!config.getBoolean("enabled", true)) {
            config.set("enabled", true);
            try {
                config.save(configFile);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            Static.log(ChatColor.RED + "bStats 가 비활성화되어있어 활성화했습니다.");
        }
        Metrics metrics = new Metrics(plugin);
        metrics.addCustomChart(new Metrics.AdvancedPie("players_by_server", () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put(Static.getAddress(), Static.getOnlinePlayers().size());
            return map;
        }));
    }
}
