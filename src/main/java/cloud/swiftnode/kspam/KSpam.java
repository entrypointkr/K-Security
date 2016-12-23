package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.checker.SpamHttpChecker;
import cloud.swiftnode.kspam.abstraction.convertor.IpStringConvertor;
import cloud.swiftnode.kspam.abstraction.processer.CheckAllProcesser;
import cloud.swiftnode.kspam.abstraction.processer.DeleteAllDataProcesser;
import cloud.swiftnode.kspam.abstraction.processer.CacheReadProcesser;
import cloud.swiftnode.kspam.abstraction.processer.CacheWriteProcesser;
import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.metrics.BlockedGraph;
import cloud.swiftnode.kspam.metrics.PlayerGraph;
import cloud.swiftnode.kspam.runnable.UpdateBukkicRunnable;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class KSpam extends JavaPlugin {
    private static KSpam INST;

    public static KSpam getInst() {
        return INST;
    }

    @Override
    public void onEnable() {
        INST = this;
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(
                Lang.INTRO.toString().replace("{0}",
                        StaticStorage.getVersionStorage().getCurrVer().toString()));
        instantiate();
    }

    @Override
    public void onDisable() {
        // Cache write
        new CacheWriteProcesser().process();
        // Spam data delete
        new DeleteAllDataProcesser().process();
        // TODO: Disable custom graph
        // 필요성 검토 요함
//        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
//            if (plugin.isEnabled()) {
//                Bukkit.getScheduler().runTask(plugin, new Runnable() {
//                    @Override
//                    public void run() {
//                        Bukkit.getPluginManager().enablePlugin(KSpam.getInst());
//                    }
//                });
//            }
//        }
    }

    private void instantiate() {
        // Listener register
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        // Update check
        new UpdateBukkicRunnable().runTaskTimerAsynchronously(this, 3600 * 20, 3600 * 20);
        // Check all player every hour
        if (getConfig().getBoolean("check-timer", true)) {
            Static.runTaskTimerAsync(new CheckAllProcesser(), 3600 * 20, 3600 * 20);
        }
        // Metrics
        try {
            Metrics metrics = new Metrics(this);
            metrics.createGraph("Blocked count")
                    .addPlotter(new BlockedGraph("Blocked count"));
            metrics.createGraph("Player count")
                    .addPlotter(new PlayerGraph("Player count"));
            metrics.start();
        } catch (Exception ex) {
            Static.consoleMsg(
                    Lang.PREFIX + Lang.EXCEPTION.toString("Metrics 접속 실패 " + ex.getMessage()));
        }
        // Cache load
        new CacheReadProcesser().process();
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command command, String label, final String[] args) {
        switch (label.toLowerCase()) {
            case "kspam":
                sender.sendMessage(getDescription().getCommands().keySet().toString());
                sender.sendMessage(new Version(getDescription().getVersion()).toString());
                sender.sendMessage(String.valueOf(StaticStorage.getCachedIpSet().size()));
                sender.sendMessage(String.valueOf(StaticStorage.getSpamFileSet().size()));
                return true;
            case "kspamerror":
                if (sender.isOp()) {
                    StaticStorage.setErrorMessage(!StaticStorage.isErrorMessage());
                    sender.sendMessage(Lang.PREFIX + Lang.SWITCH.toString(StaticStorage.isErrorMessage()));
                    sender.sendMessage(Lang.PREFIX + StaticStorage.getPlayerSet().toString());
                } else {
                    sender.sendMessage(Lang.PREFIX + Lang.NO_PERM.toString());
                }
                return true;
            case "kspamcheck":
                if (sender.isOp()) {
                    Static.runTaskAsync(new Runnable() {
                        @Override
                        public void run() {
                            String ip;
                            if (args.length <= 0) {
                                ip = "1.1.1.1";
                            } else {
                                Player p = Bukkit.getPlayer(args[0]);
                                if (p != null) {
                                    ip = new IpStringConvertor(p).convert();
                                } else {
                                    ip = args[0];
                                }
                            }
                            SpamStorage storage = new SpamStorage(Result.ERROR, ip);
                            new SpamHttpChecker(storage).check();
                            sender.sendMessage(Lang.PREFIX + Lang.CHECK.toString(ip, storage.getResult(), storage.getType()));
                        }
                    });
                } else {
                    sender.sendMessage(Lang.PREFIX + Lang.NO_PERM.toString());
                }
                return true;
            case "kspamforce":
                if (sender.isOp()) {
                    StaticStorage.setForceMode(!StaticStorage.isForceMode());
                    sender.sendMessage(Lang.PREFIX + Lang.SWITCH.toString(StaticStorage.isForceMode()));
                } else {
                    sender.sendMessage(Lang.PREFIX + Lang.NO_PERM.toString());
                }
                return true;
            case "kspamrem":
                if (sender.isOp()) {
                    if (args.length > 0) {
                        String ip = args[0];
                        StaticStorage.getCachedIpSet().remove(ip);
                        sender.sendMessage(Lang.PREFIX + Lang.REMOVE.toString(ip));
                    } else {
                        return false;
                    }
                } else {
                    sender.sendMessage(Lang.PREFIX + Lang.NO_PERM.toString());
                }
                return true;
            case "kspamadd":
                if (sender.isOp()) {
                    if (args.length > 0) {
                        String ip;
                        Player p = Bukkit.getPlayer(args[0]);
                        if (p != null) {
                            ip = new IpStringConvertor(p).convert();
                            p.kickPlayer(Lang.PREFIX + Lang.KICK.toString());
                        } else {
                            ip = args[0];
                        }
                        StaticStorage.getCachedIpSet().add(ip);
                        sender.sendMessage(Lang.PREFIX + Lang.ADD.toString(ip));
                    } else {
                        return false;
                    }
                } else {
                    sender.sendMessage(Lang.PREFIX + Lang.NO_PERM.toString());
                }
            default:
                return false;
        }
    }
}
