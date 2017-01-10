package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class SpamProcessor implements Processor {
    private SpamChecker[] checkers;
    private SpamExecutor executor;
    private DeniableInfoAdapter adapter;

    public SpamProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        this.executor = executor;
        this.adapter = adapter;
    }

    @Override
    public boolean process() {
        for (SpamChecker checker : checkers) {
            if (executor.execute(this, checker, adapter)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @SafeVarargs
    protected final void setCheckers(Class<? extends SpamChecker>... checkerClasses) {
        SpamChecker[] checkers = new SpamChecker[checkerClasses.length];
        for (int i = 0; i < checkerClasses.length; i++) {
            try {
                checkers[i] = checkerClasses[i].getConstructor(Info.class).newInstance(adapter);
            } catch (Exception ex) {
                // Ignore
            }
        }
        this.checkers = checkers;
    }
}
