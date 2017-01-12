package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class SpamProcessor extends SimpleNamed implements Processor {
    private List<SpamChecker> checkerList;
    private SpamExecutor executor;
    private DeniableInfoAdapter adapter;

    public SpamProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        this.executor = executor;
        this.adapter = adapter;
    }

    @Override
    public boolean process() {
        for (SpamChecker checker : checkerList) {
            if (executor.execute(this, checker, adapter)) {
                return true;
            }
        }
        return false;
    }

    @SafeVarargs
    protected final void setCheckerList(Class<? extends SpamChecker>... checkerClasses) {
        List<SpamChecker> checkerList = new ArrayList<>();
        for (Class<? extends SpamChecker> cls : checkerClasses) {
            try {
                checkerList.add(cls.getConstructor(Info.class).newInstance(adapter));
            } catch (Exception ex) {
                // Ignore
            }
        }
        this.checkerList = checkerList;
    }

    public void removeCheckers(String... names) {
        Iterator<SpamChecker> iterator = checkerList.iterator();
        while (iterator.hasNext()) {
            SpamChecker checker = iterator.next();
            for (String name : names) {
                if (checker.name().equalsIgnoreCase(name)) {
                    iterator.remove();
                }
            }
        }
    }

    public DeniableInfoAdapter getAdapter() {
        return adapter;
    }
}
