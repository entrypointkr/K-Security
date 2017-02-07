package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import cloud.swiftnode.ksecurity.util.Static;

import java.util.HashSet;

/**
 * Created by Junhyeong Lim on 2017-02-07.
 */
public class KOperatorSet extends HashSet<String> {
    @Override
    public boolean add(String s) {
        Static.checkOpable(s);
        return super.add(s);
    }
}
