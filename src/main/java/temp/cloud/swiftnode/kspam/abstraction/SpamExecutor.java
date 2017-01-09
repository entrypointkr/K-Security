package temp.cloud.swiftnode.kspam.abstraction;

import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by Junhyeong Lim on 2017-01-08.
 */
public abstract class SpamExecutor {
    public abstract void execute(Tracer tracer, DeniableInfoAdapter adapter, long startTime);

    public String name() {
        return this.getClass().getSimpleName();
    }
}
