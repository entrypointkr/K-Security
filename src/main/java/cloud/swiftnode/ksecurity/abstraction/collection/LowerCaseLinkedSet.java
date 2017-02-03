package cloud.swiftnode.ksecurity.abstraction.collection;

import java.util.LinkedHashSet;

/**
 * Created by Junhyeong Lim on 2017-02-03.
 */
public class LowerCaseLinkedSet extends LinkedHashSet<String> {
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
