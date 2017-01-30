package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.util.Metrics;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class MetricsInitProcessor implements Processor {
    @Override
    public boolean process() {
        try {
            new Metrics((JavaPlugin) KSpam.inst);
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return false;
        }
        return true;
    }
}
