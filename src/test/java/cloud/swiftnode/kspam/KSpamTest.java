package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.sender.MockCommandSender;
import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.StringInfo;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.deniable.EmptyDeniable;
import cloud.swiftnode.kspam.abstraction.executor.BaseSpamExecutor;
import cloud.swiftnode.kspam.abstraction.executor.DebugSpamExecutor;
import cloud.swiftnode.kspam.abstraction.processor.AsyncLoginProcessor;
import cloud.swiftnode.kspam.abstraction.processor.SyncLoginProcessor;
import org.bukkit.command.CommandSender;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class KSpamTest {
    @Test
    public void prcsTest() {
        StringInfo mockInfo = new StringInfo("12.32.12.32", UUID.randomUUID().toString(), "EntryPoint");
        CommandSender sender = new MockCommandSender();
        SpamExecutor executor = new DebugSpamExecutor(new BaseSpamExecutor(), sender);
        DeniableInfoAdapter adapter = new DeniableInfoAdapter(false, new EmptyDeniable(), mockInfo);
        SpamProcessor syncPrcs = new SyncLoginProcessor(executor, adapter);
        SpamProcessor asyncPrcs = new AsyncLoginProcessor(executor, adapter);
        asyncPrcs.removeChecker("SwiftnodeChecker");
        syncPrcs.process();
        asyncPrcs.process();
    }
}
