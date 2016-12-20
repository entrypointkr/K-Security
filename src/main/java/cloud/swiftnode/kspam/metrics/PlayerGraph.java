package cloud.swiftnode.kspam.metrics;

import cloud.swiftnode.kspam.util.Static;
import org.mcstats.Metrics;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class PlayerGraph extends Metrics.Plotter {
    public PlayerGraph(String name) {
        super(name);
    }

    @Override
    public int getValue() {
        return Static.getOnlinePlayers().length;
    }
}
