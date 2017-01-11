package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.executor.DebugSpamExecutor;
import cloud.swiftnode.kspam.abstraction.processor.*;
import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.util.Config;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.StaticStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class KSpam extends JavaPlugin {
    public static KSpam INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        saveDefaultConfig();
        new CacheInitProcessor().process();
        new UpdateCheckProcessor().process();
        new MetricsInitProcessor().process();
        Static.consoleMsg(Lang.INTRO.builder()
                .single(Lang.Key.KSPAM_VERSION, Static.getVersion()));
    }

    @Override
    public void onDisable() {
        saveConfig();
        new CacheSaveProcessor().process();
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
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("check")) {
                    if (!isOp) {
                        break;
                    }
                    String info = new InfoFacade(args[1]).get();
                    sender.sendMessage(Lang.COMMAND_CHECK.builder().single(Lang.Key.VALUE, info).build());
                    final DeniableInfoAdapter adapter = new DeniableInfoAdapter(false, info);
                    final SpamExecutor executor = new DebugSpamExecutor(new EmptySpamExecutor(), sender);
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
                return player.getName();
            }
            return target;
        }
    }
}
