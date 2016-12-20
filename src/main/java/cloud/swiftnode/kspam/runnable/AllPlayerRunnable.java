package cloud.swiftnode.kspam.runnable;

import cloud.swiftnode.kspam.abstraction.checker.SpamHttpChecker;
import cloud.swiftnode.kspam.abstraction.processer.PunishSpamProcesser;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class AllPlayerRunnable extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p : Static.getOnlinePlayers()) {
            SpamStorage storage = new SpamStorage(Result.ERROR, p.getAddress().getAddress());
            new SpamHttpChecker(storage).check();
            new PunishSpamProcesser(storage, p).process();
        }
    }
}
