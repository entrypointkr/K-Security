package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.util.Static;
import org.mcstats.Metrics;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class MetricsInitProcessor implements Processor {
    @Override
    public boolean process() {
        try {
            Metrics metrics = new Metrics(KSpam.INSTANCE);
            metrics.start();
            return true;
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return false;
        }
    }
}
