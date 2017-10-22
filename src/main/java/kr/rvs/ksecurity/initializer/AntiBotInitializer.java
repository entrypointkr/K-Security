package kr.rvs.ksecurity.initializer;

import kr.rvs.ksecurity.antibot.PingAntiBot;
import kr.rvs.ksecurity.antibot.TransactionAntiBot;
import kr.rvs.ksecurity.util.Config;
import kr.rvs.ksecurity.util.Static;
import kr.rvs.ksecurity.util.Version;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Junhyeong Lim on 2017-10-19.
 */
public class AntiBotInitializer implements Initializer { //TODO: ClassNotFoundException
    private boolean transaction = false;

    @Override
    public Result check(JavaPlugin plugin, Config config) {
        Result result = Initializer.super.check(plugin, config);
        if (!Version.BUKKIT.equals(new Version(1, 5, 2))) {
            result.setMessage("1.5.2 환경이 아니므로 제한된 기능으로 작동합니다.");
        } else {
            Plugin protocolLib = Bukkit.getPluginManager().getPlugin("ProtocolLib");
            if (protocolLib != null && protocolLib.isEnabled()) {
                transaction = true;
            } else {
                result.setMessage("ProtocolLib 이 없어 제한된 기능으로 작동합니다.");
            }
        }
        return result;
    }

    @Override
    public void init(JavaPlugin plugin) {
        AtomicInteger transactionCounter = new AtomicInteger();
        AtomicInteger pingCounter = new AtomicInteger();
        Metrics metrics = Bukkit.getServicesManager().load(Metrics.class);
        metrics.addCustomChart(new Metrics.AdvancedPie("blockCountByServer", () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put(Static.getAddress(), transactionCounter.get() + pingCounter.get());
            return map;
        }));
        metrics.addCustomChart(new Metrics.AdvancedPie("blockCount", () -> {
            Map<String, Integer> map = new HashMap<>();
            map.put("transaction", transactionCounter.get());
            map.put("ping", pingCounter.get());
            return map;
        }));
        if (transaction) {
            TransactionAntiBot.init(plugin, transactionCounter);
        } else {
            PingAntiBot antiBot = new PingAntiBot(pingCounter);
            Bukkit.getPluginManager().registerEvents(antiBot, plugin);
        }
    }
}
