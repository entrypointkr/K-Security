package cloud.swiftnode.kspam.runnable;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.storage.CheckStorage;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-19.
 */
public class ProcessRunnable implements Runnable {
    private Player p;
    private Result result;

    public ProcessRunnable(Player p, Result result) {
        this.p = p;
        this.result = result;
    }

    @Override
    public void run() {
        if (result == Result.TRUE) {
            Bukkit.getScheduler().runTask(KSpam.getInst(), new Runnable() {
                @Override
                public void run() {
                    p.kickPlayer(Lang.PREFIX + "\n" + Lang.KICK);
                }
            });
            Bukkit.getConsoleSender().sendMessage(Lang.PREFIX + Lang.KICKED.toString(p.getName()));
            CheckStorage.getCachedIpList().add(
                    Static.convertToIp(p.getAddress().getAddress()));
        } else if (result == Result.ERROR) {
            Set<String> playerList = StaticStorage.getPlayerSet();
            playerList.add(p.getName());
            if (KSpam.getInst().isSwitchOn()) {
                Bukkit.getConsoleSender().sendMessage(
                        Lang.PREFIX + Lang.ERROR.toString(playerList.toString()));
            }
        } else if (result == Result.FALSE) {
            Static.removePlayerInStorage(p);
        }
        // Else?
    }
}
