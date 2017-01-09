package temp.cloud.swiftnode.kspam.abstraction.executor;

import temp.cloud.swiftnode.kspam.abstraction.SpamExecutor;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.util.Static;
import temp.cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class DelayedExecutor extends SpamExecutor {
    public abstract void delayedExecute(Tracer tracer, DeniableInfoAdapter adapter, long startTime);

    @Override
    public void execute(final Tracer tracer, final DeniableInfoAdapter adapter, final long startTime) {
        Static.runTask(new Runnable() {
            @Override
            public void run() {
                delayedExecute(tracer, adapter, startTime);
            }
        });
    }
}
