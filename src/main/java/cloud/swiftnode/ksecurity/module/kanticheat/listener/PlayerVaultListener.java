package cloud.swiftnode.ksecurity.module.kanticheat.listener;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;
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
    private Method cachedMethod;
    private boolean fail = false;

    public PlayerVaultListener(Listener parent) {
        this.parent = parent;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTp(PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN) {
            return;
        }
        try {
            if (cachedMethod == null) {
                cachedMethod = parent.getClass().getMethod("onTeleport", PlayerTeleportEvent.class);
            }
            cachedMethod.invoke(parent, e);
        } catch (Exception ex) {
            if (fail) {
                Bukkit.getPluginManager().registerEvents(parent, KSecurity.inst);
                PlayerTeleportEvent.getHandlerList().unregister(this);
                Static.log(Lang.FIX_PLAYERVAULT_FAIL.builder());
            } else {
                fail = true;
            }
        }
    }
}
