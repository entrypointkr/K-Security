package cloud.swiftnode.kspam.abstraction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class SpamProcessor implements Processor {
    protected Deniable deniable;
    protected Set<Checkable> checkableList;

    public SpamProcessor(Deniable deniable, Checkable... checkable) {
        this.deniable = deniable;
        this.checkableList = new HashSet<>(Arrays.asList(checkable));
    }

    public void process() {
        for (Checkable checkable : checkableList) {
            if (checkable.check()) {
                deniable.deny();
                return;
            }
        }
    }
}
