package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.StaticStorage;
import cloud.swiftnode.kspam.util.URLs;
import cloud.swiftnode.kspam.util.Version;
import org.bukkit.Bukkit;
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

    @Override
    public void onEnable() {
        INSTANCE = this;
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
    }

    @SuppressWarnings("unchecked")
    private void cacheInit() {
        if (!getDataFolder().isDirectory()) {
            getDataFolder().mkdirs();
        }
        File file = new File(getDataFolder(), "K-Spam.cache");
        try {
            if (!file.isFile()) {
                FileOutputStream outStream = new FileOutputStream(file);
                URL url = URLs.CACHE.toUrl();
                ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                outStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                outStream.close();
            }
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(file));
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
