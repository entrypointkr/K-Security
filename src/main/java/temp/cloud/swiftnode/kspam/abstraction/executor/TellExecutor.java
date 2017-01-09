package temp.cloud.swiftnode.kspam.abstraction.executor;

import temp.cloud.swiftnode.kspam.abstraction.SpamExecutor;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.util.Lang;
import temp.cloud.swiftnode.kspam.util.Tracer;
import org.bukkit.command.CommandSender;

/**
 * Created by Junhyeong Lim on 2017-01-09.
 */
public class TellExecutor extends SpamExecutor {
    private CommandSender sender;

    public TellExecutor(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void execute(Tracer tracer, DeniableInfoAdapter adapter, long startTime) {
        sender.sendMessage(Lang.DEBUG.builder()
                .addKey(Lang.Key.PROCESSOR_NAME, Lang.Key.CHECKER_NAME, Lang.Key.CHECKER_RESULT, Lang.Key.TIME)
                .addVal(tracer.getLastProcessor().name(), tracer.getLastChecker().name(), tracer.getResult(), System.currentTimeMillis() - startTime).prefix().build());
    }
}
