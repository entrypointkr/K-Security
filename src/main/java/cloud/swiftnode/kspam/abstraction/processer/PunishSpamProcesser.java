package cloud.swiftnode.kspam.abstraction.processer;

import cloud.swiftnode.kspam.abstraction.PunishProcesser;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class PunishSpamProcesser extends PunishProcesser {
    private Object obj;

    public PunishSpamProcesser(SpamStorage storage, Object obj) {
        super(storage);
        this.obj = obj;
    }

    @Override
    public void process() {
        PlayerLoginEvent event = null;
        final Player player;
        if (obj instanceof PlayerLoginEvent) {
            event = (PlayerLoginEvent) obj;
            player = event.getPlayer();
        } else if (obj instanceof Player) {
            player = (Player) obj;
        } else {
            throw new IllegalArgumentException("WTF this obj? " + obj.getClass());
        }
        Result result = storage.getResult();
        String kickMsg = Lang.PREFIX + "\n" + Lang.KICK;
        if (result == Result.TRUE) {
            boolean disallowed = false;
            if (event != null) {
                disallowed = true;
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, kickMsg);
            } else {
                Static.runTask(new Runnable() {
                    @Override
                    public void run() {
                        player.kickPlayer(Lang.PREFIX + "\n" + Lang.KICK);
                    }
                });
                Static.sendOperators(Lang.KICKED.toString(player.getName(), storage.getType()));
            }
            new DeleteDataProcesser(player, disallowed).process();
            StaticStorage.getCachedIpSet().add(storage.getIp());
        } else if (result == Result.ERROR) {
            Set<String> playerList = StaticStorage.getPlayerSet();
            playerList.add(player.getName());
            if (StaticStorage.isErrorMessage()) {
                Static.consoleMsg(Lang.PREFIX + Lang.ERROR.toString(playerList.toString()));
            }
        } else if (result == Result.FALSE) {
            Static.removePlayerInStorage(player);
        }
        // Else?
    }
}
