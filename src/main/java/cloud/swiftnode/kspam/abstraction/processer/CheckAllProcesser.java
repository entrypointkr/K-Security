package cloud.swiftnode.kspam.abstraction.processer;

import cloud.swiftnode.kspam.abstraction.RunnableProcesser;
import cloud.swiftnode.kspam.abstraction.checker.SpamHttpChecker;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.entity.Player;

/**
 * Created by EntryPoint on 2016-12-21.
 */
public class CheckAllProcesser extends RunnableProcesser {
    @Override
    public void process() {
        for (Player p : Static.getOnlinePlayers()) {
            SpamStorage storage = new SpamStorage(Result.ERROR, p.getAddress().getAddress());
            new SpamHttpChecker(storage).check();
            new PunishSpamProcesser(storage, p).process();
        }
    }
}
