package cloud.swiftnode.kspam.runnable;

import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public abstract class ErrorableBukkitRunnable extends BukkitRunnable {
    protected int errorCount;

    public ErrorableBukkitRunnable() {
        this.errorCount = 0;
    }

    public void task(Exception ex, String message) {
        if (errorCount >= 4) {
            Static.consoleMsg(
                    Lang.PREFIX + Lang.EXCEPTION.toString(message + ex.getMessage()));
            cancel();
        } else {
            errorCount = ++errorCount;
            this.run();
        }
    }
}
