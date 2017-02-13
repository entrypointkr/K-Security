package cloud.swiftnode.ksecurity.module.kanticheat;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kanticheat.listener.CheatListener;
import cloud.swiftnode.ksecurity.module.kanticheat.listener.PlayerVaultListener;
import cloud.swiftnode.ksecurity.util.Config;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-02-11.
 */
public class KAntiCheat extends Module {
    public KAntiCheat(JavaPlugin parent) {
        super(parent);
    }

    @Override
    public void onEnable() throws Exception {
        Bukkit.getPluginManager().registerEvents(new CheatListener(), parent);
        rpgitemInit();
        playerVaultInit();
    }

    private void rpgitemInit() {
        if (!Config.isRpgItem()) {
            return;
        }
        Plugin rpgitem = Bukkit.getPluginManager().getPlugin("RPG Items");
        rpgitem = rpgitem == null ? Bukkit.getPluginManager().getPlugin("RPG_Items") : rpgitem;
        if (rpgitem != null) {
            PlayerPickupItemEvent.getHandlerList().unregister(rpgitem);
            Static.consoleMsg(Lang.RPGITEM_DETECT.builder());
        }
    }

    private void playerVaultInit() {
        if (!Config.isPlayerVault()) {
            return;
        }
        Plugin pv = Bukkit.getPluginManager().getPlugin("PlayerVaults");
        if (pv != null) {
            HandlerList handlers = PlayerTeleportEvent.getHandlerList();
            Listener original = null;
            for (RegisteredListener element : handlers.getRegisteredListeners()) {
                if (!element.getPlugin().getName().equals("PlayerVaults")) {
                    continue;
                }
                Listener listener = element.getListener();
                if (listener.getClass().getSimpleName().equals("Listeners")) {
                    original = listener;
                    break;
                }
            }
            if (original != null) {
                Listener newListener = new PlayerVaultListener(original);
                handlers.unregister(original);
                Bukkit.getPluginManager().registerEvents(newListener, KSecurity.inst);
                Static.consoleMsg(Lang.PV_DETECT.builder());
            }
        }
    }

    @Override
    public String getSimpleVersion() {
        return "1.0";
    }
}
