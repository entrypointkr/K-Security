package cloud.swiftnode.kspam.abstraction.executor;

import cloud.swiftnode.kspam.abstraction.DeniableInfo;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class DebugSpamExecutor extends DecorateSpamExecutor {
    private CommandSender sender;

    public DebugSpamExecutor(SpamExecutor parent) {
        super(parent);
        this.sender = Bukkit.getConsoleSender();
    }

    public DebugSpamExecutor(SpamExecutor parent, CommandSender sender) {
        super(parent);
        this.sender = sender;
    }

    @Override
    public boolean execute(SpamProcessor processor, SpamChecker checker, DeniableInfo adapter) {
        boolean ret;
        long time = Static.time();
        ret = parent.execute(processor, checker, adapter);
        sender.sendMessage(Lang.DEBUG.builder()
                .addKey(Lang.Key.PROCESSOR_NAME, Lang.Key.EXECUTOR_NAME, Lang.Key.CHECKER_NAME, Lang.Key.INFO, Lang.Key.CHECKER_RESULT, Lang.Key.TIME)
                .addVal(processor.name(), this.name(), checker.name(), checker.getLastInfo(), parent.getLastResult(), Static.time() - time)
                .prefix()
                .build());
        return ret;
    }
}
