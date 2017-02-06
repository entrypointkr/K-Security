package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.ServerOperator;

/**
 * Created by Junhyeong Lim on 2017-02-06.
 */
public class KPermissible extends PermissibleBase {
    private Player player;

    public KPermissible(ServerOperator opable, Player player) {
        super(opable);
        this.player = player;
    }

    @Override
    public void recalculatePermissions() {
        Static.runTask(() -> {
            if (!Static.checkOpable(player)) {
                player.setOp(false);
                Bukkit.broadcastMessage(Lang.DEOP.builder()
                        .single(Lang.Key.VALUE, player.getName())
                        .build());
            }
        });
        super.recalculatePermissions();
    }
}
