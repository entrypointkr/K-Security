package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import cloud.swiftnode.ksecurity.util.Config;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.StaticStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-02-03.
 */
public class KPermissible extends PermissibleBase {
    private Permissible permissible = this;
    private Player player;
    private final BoolStorage storage = new BoolStorage();

    public KPermissible(Permissible permissible, Player player) {
        super(player);
        this.permissible = permissible;
        this.player = player;
    }

    @Override
    public void setOp(boolean value) {
        if (player.isOp() == value)
            return;
        checkOpable();
        super.setOp(value);
    }

    @Override
    public void recalculatePermissions() {
        checkOpable();
        super.recalculatePermissions();
    }

    @Override
    public boolean isPermissionSet(String name) {
        return permissible.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return permissible.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(String name) {
        return permissible.hasPermission(name);
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return permissible.hasPermission(perm);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return permissible.addAttachment(plugin, name, value);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return permissible.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return permissible.addAttachment(plugin, name, value, ticks);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return permissible.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        permissible.removeAttachment(attachment);
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return permissible.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return permissible != null && permissible.isOp();
    }

    private void checkOpable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("K-Security")
                || Config.getOpList().size() <= 0)
            return;
        storageInit();
        // Escape
        if (storage.bool) {
            // 버그 예상
            storage.bool = false;
            return;
        }
        playerInit();
        // Check
        if (player != null
                && player.isOp()
                && !Config.getOpList().contains(player.getName())) {
            storage.bool = true;
            Static.runTask(() -> {
                if (player.isOp()) {
                    player.setOp(false);
                    Bukkit.broadcastMessage(Lang.DEOP.builder()
                            .single(Lang.Key.VALUE, player.getName())
                            .build());
                }
                storage.bool = false;
            });
        }
    }

    private void storageInit() {
        if (storage == null) {
            try {
                Reflections.setDecField(getClass(), this, "storage", new BoolStorage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void playerInit() {
        if (player == null) {
            try {
                Object obj = Reflections.getDecFieldObj(getClass().getSuperclass(), this, "opable");
                if (obj instanceof Player) {
                    player = (Player) obj;
                }
            } catch (Exception e) {
                Static.consoleMsg(e);
            }
        }
    }

    private class BoolStorage {
        private boolean bool = false;
    }
}
