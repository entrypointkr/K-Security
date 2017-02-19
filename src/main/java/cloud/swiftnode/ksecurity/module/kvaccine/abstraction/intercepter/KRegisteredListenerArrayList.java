package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import cloud.swiftnode.ksecurity.KSecurity;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-02-19.
 */
public final class KRegisteredListenerArrayList extends ArrayList<RegisteredListener> {
    public KRegisteredListenerArrayList(Collection<? extends RegisteredListener> c) {
        super(c);
    }

    @Override
    public RegisteredListener remove(int index) {
        RegisteredListener listener = get(index);
        if (isEnabled() && equals(listener, "K-Security")) {
            return null;
        }
        return super.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        if (isEnabled() && equals(o, "K-Security")) {
            return false;
        }
        return super.remove(o);
    }

    private boolean isEnabled() {
        return KSecurity.inst.isEnabled();
    }

    private boolean equals(Object listener, String name) {
        return listener instanceof RegisteredListener
                && ((RegisteredListener) listener).getPlugin().getName().equals(name);
    }
}
