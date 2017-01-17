package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.MockCommandSender;
import cloud.swiftnode.kspam.abstraction.MockPlugin;
import cloud.swiftnode.kspam.abstraction.MockServer;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.deniable.EmptyDeniable;
import cloud.swiftnode.kspam.abstraction.executor.BaseSpamExecutor;
import cloud.swiftnode.kspam.abstraction.executor.DebugSpamExecutor;
import cloud.swiftnode.kspam.abstraction.info.StringInfo;
import cloud.swiftnode.kspam.abstraction.processor.AsyncLoginProcessor;
import cloud.swiftnode.kspam.abstraction.processor.SyncLoginProcessor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.server.ServerListPingEvent;
import org.junit.Test;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.UUID;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class KSpamTest {
    @Test
    public void prcsTest() {
        // Injection
        try {
            Field serverField = Bukkit.class.getDeclaredField("server");
            Field instField = KSpam.class.getDeclaredField("INSTANCE");

            serverField.setAccessible(true);
            instField.setAccessible(true);

            serverField.set(null, new MockServer());
            instField.set(null, new MockPlugin());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // String
        System.out.println("String");
        StringInfo mockInfo = new StringInfo("12.32.12.32", UUID.randomUUID().toString(), "EntryPoint");
        CommandSender sender = new MockCommandSender();
        SpamExecutor executor = new DebugSpamExecutor(new BaseSpamExecutor(), sender);
        DeniableInfoAdapter adapter = new DeniableInfoAdapter(false, new EmptyDeniable(), mockInfo);
        SpamProcessor syncPrcs = new SyncLoginProcessor(executor, adapter);
        SpamProcessor asyncPrcs = new AsyncLoginProcessor(executor, adapter);
        // Escape
        asyncPrcs.removeCheckers("SwiftnodeChecker", "BotscoutChecker");
        //
        syncPrcs.process();
        asyncPrcs.process();
        // PingEvent
        System.out.println("ServerListPing");
        ServerListPingEvent event = null;
        try {
            event = new ServerListPingEvent(InetAddress.getByAddress(new byte[]{12, 32, 12, 32}), "mock", 0, 20);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        adapter.setObj(event);
        syncPrcs.setAdapter(adapter);
        syncPrcs.process();
    }
}
