package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;
import org.bukkit.Bukkit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class Static {
    public static void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(KSpam.INSTANCE, runnable);
    }

    public static void consoleMsg(String... msgs) {
        for (String msg : msgs) {
            Bukkit.getConsoleSender().sendMessage(msg);
        }
    }

    public static void consoleMsg(Lang.MessageBuilder... builders) {
        for (Lang.MessageBuilder builder : builders) {
            consoleMsg(builder.build());
        }
    }

    public static void consoleMsg(Exception ex) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(stream));
        consoleMsg(Lang.EXCEPTION.builder().single(Lang.Key.EXCEPTION_MESSAGE, new String(stream.toByteArray())).prefix());
    }
}
