package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.executor.TellExecutor;
import cloud.swiftnode.kspam.abstraction.processor.AsynchronousSpamProcessor;
import cloud.swiftnode.kspam.abstraction.processor.SynchronizeSpamProcessor;
import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.StaticStorage;
import cloud.swiftnode.kspam.util.URLs;
import cloud.swiftnode.kspam.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class KSpam extends JavaPlugin {
    public static KSpam INSTANCE;
    private static final String DEBUG_MODE = "debug-mode";

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        cacheInit();
        updateCheck();
        metricsInit();
        Static.consoleMsg(Lang.INTRO.builder()
                .single(Lang.Key.KSPAM_VERSION, Static.getVersion()));
    }

    @Override
    public void onDisable() {
        cacheSave();
        saveConfig();
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
                    getConfig().set(DEBUG_MODE, !getConfig().getBoolean(DEBUG_MODE, false));
                    sender.sendMessage(Lang.SET.builder().single(Lang.Key.VALUE, getConfig().getBoolean(DEBUG_MODE)).prefix().build());
                    return true;
                }
            case 2:
                if (args[0].equalsIgnoreCase("check")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        DeniableInfoAdapter adapter = new DeniableInfoAdapter(target);
                        SpamExecutor executor = new TellExecutor(sender);
                        new SynchronizeSpamProcessor(adapter, executor).process();
                        new AsynchronousSpamProcessor(adapter, executor).process();
                        return true;
                    }
                }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void cacheInit() {
        if (!getDataFolder().isDirectory()) {
            getDataFolder().mkdirs();
        }
        File file = new File(getDataFolder(), "K-Spam.cache");
        if (!file.isFile()) {
            try (FileOutputStream outStream = new FileOutputStream(file)) {
                URL url = URLs.CACHE.toUrl();
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                outStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (Exception ex) {
                Static.consoleMsg(ex);
            }
        }
        try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(file))) {
            StaticStorage.cachedSet = (Set<String>) inStream.readObject();
            Static.consoleMsg(Lang.CACHE_COUNT.builder()
                    .prefix().single(Lang.Key.CACHE_COUNT, StaticStorage.cachedSet.size()));
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
    }

    private void cacheSave() {
        try {
            File file = new File(getDataFolder(), "K-Spam.cache");
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file));
            outStream.writeObject(StaticStorage.cachedSet);
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
    }

    private void updateCheck() {
        try {
            URL url = new URL("https://github.com/EntryPointKR/K-SPAM/releases/latest");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("<span class=\"css-truncate-target\">")) {
                    continue;
                }
                StaticStorage.setNewVer(new Version(
                        Static.substring(line, "<span class=\"css-truncate-target\">", "</span>")));
                if (StaticStorage.getCurrVer().beforeEquals(StaticStorage.getNewVer())) {
                    Static.consoleMsg(
                            Lang.UPDATE_INFO_NEW.builder().prefix(),
                            Lang.NEW_VERSION.builder().single(Lang.Key.NEW_VERSION, StaticStorage.getNewVer()).prefix(),
                            Lang.CURRENT_VERSION.builder().single(Lang.Key.KSPAM_VERSION, StaticStorage.getCurrVer()).prefix()
                    );
                } else {
                    Static.consoleMsg(Lang.UPDATE_INFO_NO.builder().prefix());
                }
                return;
            }
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
    }

    private void metricsInit() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
    }
}
