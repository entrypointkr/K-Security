package cloud.swiftnode.kspam.abstraction.processer;

import cloud.swiftnode.kspam.abstraction.PunishProcesser;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
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
            if (event != null) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, kickMsg);
            } else {
                Static.runTask(new Runnable() {
                    @Override
                    public void run() {
                        player.kickPlayer(Lang.PREFIX + "\n" + Lang.KICK);
                    }
                });
                Bukkit.getConsoleSender().sendMessage(Lang.PREFIX + Lang.KICKED.toString(player.getName(), storage.getType()));
                StaticStorage.getCachedIpSet().add(storage.getIp());
            }
            // TODO: Remove Spam player data
        } else if (result == Result.ERROR) {
            Set<String> playerList = StaticStorage.getPlayerSet();
            playerList.add(player.getName());
            if (StaticStorage.isErrorMessage()) {
                Bukkit.getConsoleSender().sendMessage(
                        Lang.PREFIX + Lang.ERROR.toString(playerList.toString()));
            }
        } else if (result == Result.FALSE) {
            Static.removePlayerInStorage(player);
        }
        // Else?
    }
}
