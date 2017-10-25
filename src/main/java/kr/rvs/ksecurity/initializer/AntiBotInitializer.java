package kr.rvs.ksecurity.initializer;

import kr.rvs.ksecurity.antibot.PingAntiBot;
import kr.rvs.ksecurity.antibot.TransactionAntiBot;
import kr.rvs.ksecurity.util.Config;
import kr.rvs.ksecurity.util.NMSUtils;
import kr.rvs.ksecurity.util.Static;
import kr.rvs.ksecurity.util.URLs;
import kr.rvs.ksecurity.util.Version;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Created by Junhyeong Lim on 2017-10-19.
 */
public class AntiBotInitializer implements Initializer {
    private boolean transaction = false;

    @Override
    public Result check(JavaPlugin plugin, Config config) {
        Result result = Initializer.super.check(plugin, config);

        if (!Version.BUKKIT.equals(new Version(1, 5, 2))) {
            result.setMessage("1.5.2 환경이 아니므로 제한된 기능으로 작동합니다.");
            return result;
        }

        Class<?> loginVerifierClass = NMSUtils.getNMSClass("ThreadLoginVerifier");
        if (loginVerifierClass == null || Stream.of(loginVerifierClass.getDeclaredMethods()).noneMatch(method -> method.getName().equals("auth"))) {
            String message = "버킷이 구버전입니다. 최신빌드로 교체하십시오.";
            result.setMessage(message);
            result.setSuccess(false);
            Static.showDialog(message);
            Static.browse(URLs.SPIGOT_152_LATEST.toURI());
            return result;
        }

        Plugin protocolLib = Bukkit.getPluginManager().getPlugin("ProtocolLib");
        if (protocolLib != null && protocolLib.isEnabled()) {
            transaction = true;
        } else {
            result.setMessage("ProtocolLib 이 없어 제한된 기능으로 작동합니다.");
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
        metrics.addCustomChart(new Metrics.SingleLineChart("totalBlockCount", () ->
                transactionCounter.get() + pingCounter.get()));
        if (transaction) {
            TransactionAntiBot.init(plugin, transactionCounter);
        } else {
            PingAntiBot antiBot = new PingAntiBot(pingCounter);
            Bukkit.getPluginManager().registerEvents(antiBot, plugin);
        }
    }
}
