package cloud.swiftnode.kspam.runnable;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.storage.PlayerStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Result;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-19.
 */
public class ProcessRunnable implements Runnable {
    private Object obj;
    private Result result;

    public ProcessRunnable(Object obj, Result result) {
        this.obj = obj;
        this.result = result;
    }

    @Override
    public void run() {
        String kickMsg = Lang.PREFIX + "\n" + Lang.KICK;
        PlayerLoginEvent e = null;
        Player p;
        if (obj instanceof PlayerLoginEvent) {
            e = (PlayerLoginEvent) obj;
            p = e.getPlayer();
        } else if (obj instanceof Player) {
            p = (Player) obj;
        } else {
            throw new IllegalArgumentException("WTF this obj? " + obj.getClass());
        }
        if (result == Result.TRUE) {
            if (e != null) {
                e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                e.setKickMessage(kickMsg);
            } else {
                p.kickPlayer(kickMsg);
            }
            Bukkit.getConsoleSender().sendMessage(Lang.PREFIX + Lang.KICKED.toString(p.getName()));
        } else if (result == Result.ERROR) {
            Set<String> playerList = PlayerStorage.getInst().getPlayerSet();
            playerList.add(p.getName());
            if (KSpam.getInst().isSwitchOn()) {
                Bukkit.getConsoleSender().sendMessage(
                        Lang.PREFIX + Lang.ERROR.toString(playerList.toString()));
            }
        }
    }
}
