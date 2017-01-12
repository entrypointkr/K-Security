package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.util.StaticStorage;

/**
 * Created by Junhyeong Lim on 2017-01-12.
 */
public class GCProcessor implements Processor {
    @Override
    public boolean process() {
        StaticStorage.firstKickCachedSet.clear();
        StaticStorage.validateSet.clear();
        return true;
    }
}
