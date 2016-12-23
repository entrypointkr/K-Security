package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.FileConverter;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public class LegacyPlayerFileConverter extends FileConverter {
    public LegacyPlayerFileConverter(Object obj) {
        super(obj);
    }

    @Override
    public File convert() {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return new File(p.getWorld().getWorldFolder(), "players/" + p.getName() + ".dat");
        } else {
            throw new IllegalArgumentException("Received " + obj);
        }
    }
}
