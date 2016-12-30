package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.Tracer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class SpamProcessor implements Processor {
    private Deniable deniable;
    private Tracer tracer;
    private Set<Checkable> checkableList;

    public SpamProcessor(Deniable deniable, Tracer tracer, Checkable... checkable) {
        this.deniable = deniable;
        this.tracer = tracer;
        this.checkableList = new HashSet<>(Arrays.asList(checkable));
    }

    public boolean process() {
        for (Checkable checkable : checkableList) {
            tracer.setLastChecker(checkable);
            tracer.setResult(checkable.check());
            if (tracer.getResult() == Tracer.Result.DENY) {
                deniable.deny();
                return true;
            }
        }
        return false;
    }
}
