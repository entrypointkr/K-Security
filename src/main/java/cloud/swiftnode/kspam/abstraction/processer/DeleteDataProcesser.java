package cloud.swiftnode.kspam.abstraction.processer;

import cloud.swiftnode.kspam.abstraction.RunnableProcesser;
import cloud.swiftnode.kspam.abstraction.convertor.EssnFileConverter;
import cloud.swiftnode.kspam.abstraction.convertor.LegacyEssnFileConvertor;
import cloud.swiftnode.kspam.abstraction.convertor.LegacyPlayerFileConvertor;
import cloud.swiftnode.kspam.abstraction.convertor.PlayerFileConvertor;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

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
        // Save player data
        p.saveData();
        // Delete player data
        final File data = new PlayerFileConvertor(p).convert();
        final File legacyData = new LegacyPlayerFileConvertor(p).convert();
        Static.runTaskLaterAsync(new Runnable() {
            @Override
            public void run() {
                data.delete();
                legacyData.delete();
            }
        }, 20L);
        // Delete Essentials player data
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Essentials");
        if (plugin != null && plugin.isEnabled()) {
            final File essnData = new EssnFileConverter(p, plugin).convert();
            final File legacyEssnData = new LegacyEssnFileConvertor(p, plugin).convert();
            Static.runTaskLaterAsync(new Runnable() {
                @Override
                public void run() {
                    essnData.delete();
                    legacyEssnData.delete();
                }
            }, 20L);
        }
    }
}
