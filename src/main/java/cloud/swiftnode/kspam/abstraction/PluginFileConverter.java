package cloud.swiftnode.kspam.abstraction;

import org.bukkit.plugin.Plugin;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public abstract class PluginFileConverter extends FileConverter {
    protected Plugin plugin;

    public PluginFileConverter(Object obj, Plugin plugin) {
        super(obj);
        this.plugin = plugin;
    }
}
