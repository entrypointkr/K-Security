package kr.rvs.ksecurity.collection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-08-12.
 */
public class NullableArrayList<E> extends ArrayList<E> {
    public NullableArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    public NullableArrayList() {
    }

    public NullableArrayList(Collection<? extends E> c) {
        super(c);
    }

    public E def(E val, E def) {
        return val != null ? val : def;
    }

    @Override
    public E get(int index) {
        return size() > index ? super.get(index) : null;
    }

    public E get(int index, E def) {
        return def(get(index), def);
    }

    @Override
    public E remove(int index) {
        return size() > index ? super.remove(index) : null;
    }

    public E remove(int index, E def) {
        return def(remove(index), def);
    }
}
