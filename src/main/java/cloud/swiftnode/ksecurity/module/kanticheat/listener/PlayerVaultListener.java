package cloud.swiftnode.ksecurity.module.kanticheat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-02-13.
 */
public class PlayerVaultListener implements Listener {
    private Listener parent;

    public PlayerVaultListener(Listener parent) {
        this.parent = parent;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTp(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN) {
            return;
        }
        try {
            Method method = parent.getClass().getMethod("onTeleport", PlayerTeleportEvent.class);
            method.invoke(parent, e);
        } catch (Exception ex) {
            // Ignore
        }
    }
}
