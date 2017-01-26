package cloud.swiftnode.kspam.abstraction.info;

import cloud.swiftnode.kspam.abstraction.Info;
import org.bukkit.OfflinePlayer;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public abstract class AbstractInfo implements Info {
    @Override
    public String getUniqueId() throws IllegalStateException {
        String uuid = null;
        if (this.getPlayer() != null) {
            try {
                uuid = OfflinePlayer.class.getDeclaredMethod("getUniqueId").invoke(getPlayer()).toString();
            } catch (Throwable t) {
                throw new IllegalStateException("UUID Doesn't support.");
            }
        }
        return uuid;
    }
}
