package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.StaticStorage;
import cloud.swiftnode.kspam.util.URLs;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
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
        Static.consoleMsg(Lang.INTRO.builder()
            .single(Lang.Key.KSPAM_VERSION, Static.getVersion()));
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
            ex.printStackTrace();
        }
    }
}
