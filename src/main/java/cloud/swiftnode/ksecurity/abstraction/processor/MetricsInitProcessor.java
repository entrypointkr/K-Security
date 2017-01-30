package cloud.swiftnode.ksecurity.abstraction.processor;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.abstraction.Processor;
import cloud.swiftnode.ksecurity.util.Metrics;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class MetricsInitProcessor implements Processor {
    @Override
    public boolean process() {
        try {
            new Metrics((JavaPlugin) KSecurity.inst);
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return false;
        }
        return true;
    }
}
