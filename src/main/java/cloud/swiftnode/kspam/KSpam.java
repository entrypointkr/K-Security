package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.convertor.StringToIpConverter;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.executor.BaseSpamExecutor;
import cloud.swiftnode.kspam.abstraction.executor.DebugSpamExecutor;
import cloud.swiftnode.kspam.abstraction.processor.AsyncLoginProcessor;
import cloud.swiftnode.kspam.abstraction.processor.CacheInitProcessor;
import cloud.swiftnode.kspam.abstraction.processor.CacheSaveProcessor;
import cloud.swiftnode.kspam.abstraction.processor.GCProcessor;
import cloud.swiftnode.kspam.abstraction.processor.MetricsInitProcessor;
import cloud.swiftnode.kspam.abstraction.processor.SyncLoginProcessor;
import cloud.swiftnode.kspam.abstraction.processor.UpdateCheckProcessor;
import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.listener.ServerListener;
import cloud.swiftnode.kspam.util.Config;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.StaticStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class KSpam extends JavaPlugin {
    public static KSpam INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);
        saveDefaultConfig();
        new CacheInitProcessor().process();
        new UpdateCheckProcessor().process();
        new MetricsInitProcessor().process();
        if (getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
            Static.consoleMsg(Lang.PROTOCOL_LIB_DETECT.builder().prefix());
            Static.protocolLibHook();
        } else {
            Static.consoleMsg(Lang.PROTOCOL_LIB_NOT_DETECT.builder().prefix());
            StaticStorage.protocolLib = false;
        }
        Static.runTaskTimerAsync(new Runnable() {
            @Override
            public void run() {
                new GCProcessor().process();
            }
        }, 20L, getConfig().getInt(Config.GC_PERIOD, 6) * 3600);
        Static.consoleMsg(Lang.INTRO.builder()
                .single(Lang.Key.KSPAM_VERSION, Static.getVersion()));
        config.addDefault("플러그인_종료시_서버끄기", false);
    }

    @Override
    public void onDisable() {
        saveConfig();
        new CacheSaveProcessor().process();
        if (config.getBoolean("플러그인_종료시_서버끄기")) {
        //OP가 플러그인 종료후 봇테러 날리는일 방지
        System.out.println("경고! K-SPAM 플러그인이 종료되었습니다. config.yml 정책에 의해 서버와 함께 종료됩니다.");
        Bukkit.shutdown();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isOp = sender.isOp();
        // Lazy
        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("force")) {
                    if (!isOp) {
                        break;
                    }
                    StaticStorage.forceMode = !StaticStorage.forceMode;
                    sender.sendMessage(Lang.SET.builder().single(Lang.Key.VALUE, StaticStorage.forceMode).prefix().build());
                    return true;
                } else if (args[0].equalsIgnoreCase("info")) {
                    sender.sendMessage(Lang.NEW_VERSION.builder().single(Lang.Key.NEW_VERSION, StaticStorage.getNewVer()).prefix().build());
                    sender.sendMessage(Lang.CURRENT_VERSION.builder().single(Lang.Key.KSPAM_VERSION, StaticStorage.getCurrVer()).prefix().build());
                    sender.sendMessage(Lang.PREFIX + String.valueOf(StaticStorage.cachedSet.size()));
                    sender.sendMessage(Lang.PREFIX + String.valueOf(StaticStorage.firstKickCachedSet.size()));
                    sender.sendMessage(Lang.PREFIX + String.valueOf(StaticStorage.validateSet.size()));
                    return true;
                } else if (args[0].equalsIgnoreCase("debug")) {
                    if (!isOp) {
                        break;
                    }
                    getConfig().set(Config.DEBUG_MODE, !getConfig().getBoolean(Config.DEBUG_MODE, false));
                    sender.sendMessage(Lang.SET.builder().single(Lang.Key.VALUE, getConfig().getBoolean(Config.DEBUG_MODE)).prefix().build());
                    return true;
                } else if (args[0].equalsIgnoreCase("firstkick")) {
                    if (!isOp) {
                        break;
                    }
                    getConfig().set(Config.FIRST_LOGIN_KICK, !getConfig().getBoolean(Config.FIRST_LOGIN_KICK, true));
                    sender.sendMessage(Lang.SET.builder().single(Lang.Key.VALUE, getConfig().getBoolean(Config.FIRST_LOGIN_KICK)).prefix().build());
                    return true;
                } else if (args[0].equalsIgnoreCase("alert")) {
                    if (!isOp) {
                        break;
                    }
                    getConfig().set(Config.ALERT, !getConfig().getBoolean(Config.ALERT, false));
                    sender.sendMessage(Lang.SET.builder().single(Lang.Key.VALUE, getConfig().getBoolean(Config.ALERT)).prefix().build());
                    return true;
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("check")) {
                    if (!isOp) {
                        break;
                    }
                    String info = new InfoFacade(args[1]).get();
                    if (info == null) {
                        sender.sendMessage(Lang.INVALID_IP.builder().prefix().build());
                        return true;
                    }
                    sender.sendMessage(Lang.COMMAND_CHECK.builder().single(Lang.Key.VALUE, info).prefix().build());
                    final DeniableInfoAdapter adapter = new DeniableInfoAdapter(false, info);
                    final SpamExecutor executor = new DebugSpamExecutor(new BaseSpamExecutor(), sender);
                    new SyncLoginProcessor(executor, adapter).process();
                    Static.runTaskAsync(new Runnable() {
                        @Override
                        public void run() {
                            new AsyncLoginProcessor(executor, adapter).process();
                        }
                    });
                    return true;
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (!isOp) {
                        break;
                    }
                    String info = new InfoFacade(args[1]).get();
                    if (info == null) {
                        sender.sendMessage(Lang.INVALID_IP.builder().prefix().build());
                        return true;
                    }
                    sender.sendMessage(Lang.PREFIX.toString() + StaticStorage.cachedSet.remove(info));
                    return true;
                }
        }
        return false;
    }

    class InfoFacade {
        private String target;

        InfoFacade(String target) {
            this.target = target;
        }

        String get() {
            Player player = Bukkit.getPlayer(target);
            if (player != null) {
                return new StringToIpConverter(player.getAddress().getAddress().toString()).convert();
            }
            Matcher matcher = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
                    .matcher(target);
            if (matcher.find()) {
                return target;
            } else {
                return null;
            }
        }
    }
}
