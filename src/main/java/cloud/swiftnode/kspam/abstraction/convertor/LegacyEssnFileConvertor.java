package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.PluginFileConverter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public class LegacyEssnFileConvertor extends PluginFileConverter {
    public LegacyEssnFileConvertor(Object obj, Plugin plugin) {
        super(obj, plugin);
    }

    @Override
    public File convert() {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return new File(plugin.getDataFolder(), "userdata/" + p.getName() + ".yml");
        } else {
            throw new IllegalArgumentException("Received " + obj);
        }
    }
}
