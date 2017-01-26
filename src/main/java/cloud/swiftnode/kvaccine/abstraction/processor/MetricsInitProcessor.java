package cloud.swiftnode.kvaccine.abstraction.processor;

import cloud.swiftnode.kvaccine.KVaccine;
import cloud.swiftnode.kvaccine.abstraction.Processor;
import cloud.swiftnode.kvaccine.util.Metrics;
import cloud.swiftnode.kvaccine.util.Static;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class MetricsInitProcessor implements Processor {
    @Override
    public boolean process() {
        try {
            new Metrics((JavaPlugin) KVaccine.INSTANCE);
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return false;
        }
        return true;
    }
}
