package cloud.swiftnode.ksecurity.module.kspam.abstraction;

import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public interface Info {
    String getName();

    String getIp();

    String getUniqueId() throws IllegalStateException;

    Player getPlayer();
}
