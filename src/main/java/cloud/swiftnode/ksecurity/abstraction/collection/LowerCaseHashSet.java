package cloud.swiftnode.ksecurity.abstraction.collection;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Junhyeong Lim on 2017-02-03.
 */
public class LowerCaseHashSet extends HashSet<String> {
    public LowerCaseHashSet() {
    }

    public LowerCaseHashSet(Collection<? extends String> c) {
        super(c);
    }

    @Override
    public boolean add(String s) {
        return super.add(s.toLowerCase());
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(toLower(o));
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(toLower(o));
    }

    private String toLower(Object o) {
        return String.valueOf(o).toLowerCase();
    }
}
