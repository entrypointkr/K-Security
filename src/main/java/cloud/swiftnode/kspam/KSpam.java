package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.checker.UpdateChecker;
import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.metrics.BlockedGraph;
import cloud.swiftnode.kspam.metrics.PlayerGraph;
import cloud.swiftnode.kspam.runnable.AllPlayerRunnable;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
        Bukkit.getConsoleSender().sendMessage(Lang.INTRO.toString());
        instantiate();
    }

    private void instantiate() {
        // Listener register
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        // Update check
        Static.runTaskAsync(new UpdateChecker());
        // Check all player every hour
        if (getConfig().getBoolean("check-timer", true)) {
            new AllPlayerRunnable().runTaskTimerAsynchronously(this, 3600 * 20, 3600 * 20);
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
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (label.toLowerCase()) {
            case "kspam":
                Static.msgLineLoop(sender, new Version(getDescription().getVersion()).toString());
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
            default:
                return false;
        }
    }
}
