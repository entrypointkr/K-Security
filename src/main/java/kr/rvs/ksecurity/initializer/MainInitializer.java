package kr.rvs.ksecurity.initializer;

import kr.rvs.ksecurity.util.Config;
import kr.rvs.ksecurity.util.Updater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class MainInitializer implements Initializer {
    @Override
    public Result check(JavaPlugin plugin, Config config) {
        return Result.ofDefault();
    }

    @Override
    public void init(JavaPlugin plugin) {
        // Send intro
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                Bukkit.getScheduler().runTaskLater(plugin, () ->
                        event.getPlayer().sendMessage(
                                ChatColor.WHITE + "본 서버는 보안 플러그인 " + ChatColor.YELLOW + "KSecurity " + ChatColor.WHITE + "를 사용 중입니다."),
                        20);
            }
        }, plugin);
    }
}
