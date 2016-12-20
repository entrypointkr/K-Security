package cloud.swiftnode.kspam.metrics;

import cloud.swiftnode.kspam.storage.StaticStorage;
import org.mcstats.Metrics;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class BlockedGraph extends Metrics.Plotter {
    public BlockedGraph(String name) {
        super(name);
    }

    @Override
    public int getValue() {
        return StaticStorage.getCachedIpSet().size();
    }
}
