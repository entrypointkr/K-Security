package cloud.swiftnode.kvaccine.abstraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class SpamProcessor implements Processor, Named {
    private List<SpamChecker> checkerList;
    private SpamExecutor executor;
    private DeniableInfo adapter;

    public SpamProcessor(SpamExecutor executor, DeniableInfo adapter) {
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
        Set<String> nameList = Arrays.stream(names).map(String::toLowerCase).collect(Collectors.toSet());
        checkerList.removeIf(checker -> nameList.contains(checker.name().toLowerCase()));
    }

    public DeniableInfo getAdapter() {
        return adapter;
    }

    public void setAdapter(DeniableInfo adapter) {
        this.adapter = adapter;
    }
}
