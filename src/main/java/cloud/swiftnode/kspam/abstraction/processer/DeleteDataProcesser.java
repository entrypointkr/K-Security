package cloud.swiftnode.kspam.abstraction.processer;

import cloud.swiftnode.kspam.abstraction.RunnableProcesser;
import cloud.swiftnode.kspam.abstraction.convertor.EssnFileConverter;
import cloud.swiftnode.kspam.abstraction.convertor.LegacyEssnFileConverter;
import cloud.swiftnode.kspam.abstraction.convertor.LegacyPlayerFileConverter;
import cloud.swiftnode.kspam.abstraction.convertor.PlayerFileConverter;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public class DeleteDataProcesser extends RunnableProcesser {
    private Player p;
    private boolean disallowed;

    public DeleteDataProcesser(Player p, boolean disallowed) {
        this.p = p;
        this.disallowed = disallowed;
    }

    @Override
    public void process() {
        if (disallowed) {
            return;
        }
        // World player data
        final Set<File> fileList = StaticStorage.getSpamFileSet();
        File data = new PlayerFileConverter(p).convert();
        File legacyData = new LegacyPlayerFileConverter(p).convert();
        if (!data.delete()) {
            fileList.add(data);
        }
        if (!legacyData.delete()) {
            fileList.add(legacyData);
        }
        // Essentials player data
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Essentials");
        if (plugin != null && plugin.isEnabled()) {
            final File essnData = new EssnFileConverter(p, plugin).convert();
            final File legacyEssnData = new LegacyEssnFileConverter(p, plugin).convert();
            Static.runTaskLaterAsync(new Runnable() {
                @Override
                public void run() {
                    if (!essnData.delete()) {
                        fileList.add(essnData);
                    }
                    if (!legacyEssnData.delete()) {
                        fileList.add(legacyEssnData);
                    }
                }
            }, 20L);
        }
    }
}
