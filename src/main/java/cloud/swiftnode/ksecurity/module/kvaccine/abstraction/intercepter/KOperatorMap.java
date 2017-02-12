package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import cloud.swiftnode.ksecurity.util.Static;

import java.util.HashMap;

/**
 * Created by Junhyeong Lim on 2017-02-07.
 */
public final class KOperatorMap<V> extends HashMap<String, V> {
    @Override
    public V put(String key, V value) {
        Static.checkOpable(key);
        return super.put(key, value);
    }
}
