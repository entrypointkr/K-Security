package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.Tracer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public abstract class SpamProcessor implements Processor {
    private Deniable deniable;
    private Tracer tracer;
    private Set<Checker> checkerList;

    public SpamProcessor(Deniable deniable, Tracer tracer, Checker... checker) {
        this.deniable = deniable;
        this.tracer = tracer;
        this.checkerList = new HashSet<>();
        addChecker(checker);
    }

    public void addChecker(Checker... checker) {
        this.checkerList.addAll(Arrays.asList(checker));
    }

    public boolean process() {
        for (Checker checker : checkerList) {
            tracer.setLastChecker(checker);
            tracer.setResult(checker.check());
            if (tracer.getResult() == Tracer.Result.DENY) {
                deniable.deny();
                return true;
            }
        }
        return false;
    }
}
