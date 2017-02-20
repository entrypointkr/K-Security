package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-02-20.
 */
public final class KHandlerArrayList extends ArrayList<HandlerList> {
    public KHandlerArrayList(Collection<? extends HandlerList> c) {
        super(c);
    }

    public KHandlerArrayList() {
    }

    @Override
    public boolean add(HandlerList handlerList) {
        if (!(handlerList instanceof KHandlerList)) {
            handlerList = new KHandlerList(handlerList);
        }
        return super.add(handlerList);
    }

    @Override
    public void add(int index, HandlerList element) {
        if (!(element instanceof KHandlerList)) {
            element = new KHandlerList(element);
        }
        super.add(index, element);
    }
}
