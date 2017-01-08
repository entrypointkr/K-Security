package cloud.swiftnode.kspam.abstraction.executor;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Tracer;
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
    public void execute(Tracer tracer, Deniable deniable, long startTime) {
        sender.sendMessage(Lang.DEBUG.builder()
                .addKey(Lang.Key.PROCESSOR_NAME, Lang.Key.CHECKER_NAME, Lang.Key.CHECKER_RESULT, Lang.Key.TIME)
                .addVal(tracer.getLastProcessor().name(), tracer.getLastChecker().name(), tracer.getResult(), System.currentTimeMillis() - startTime).build());
    }
}
