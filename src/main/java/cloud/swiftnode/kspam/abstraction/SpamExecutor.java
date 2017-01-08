package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by Junhyeong Lim on 2017-01-08.
 */
public abstract class SpamExecutor {
    public abstract void execute(Tracer tracer, Deniable deniable, long startTime);
}
