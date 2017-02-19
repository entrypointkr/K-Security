package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.util.injector.HandlerInjector;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-02-19.
 */
@SuppressWarnings("unchecked")
public class KHandlerArrayList extends ArrayList<HandlerList> {
    public KHandlerArrayList(Collection<? extends HandlerList> c) {
        super(c);
    }

    @Override
    public boolean add(HandlerList handlerList) {
        if (isEnabled()) {
            HandlerInjector.wrapping(handlerList);
        }
        return super.add(handlerList);
    }

    @Override
    public void add(int index, HandlerList element) {
        if (isEnabled()) {
            HandlerInjector.wrapping(element);
        }
        super.add(index, element);
    }

    private static boolean isEnabled() {
        return KSecurity.inst.isEnabled();
    }

}
