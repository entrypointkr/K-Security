package cloud.swiftnode.ksecurity.util.injector;

import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KHandlerArrayList;
import cloud.swiftnode.ksecurity.util.Reflections;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-02-19.
 */
@SuppressWarnings("unchecked")
public class HandlerInjector {
    public static void inject() throws Exception {
        KHandlerArrayList newList = new KHandlerArrayList();
        List<HandlerList> oldList = (List<HandlerList>) ((ArrayList<HandlerList>) Reflections.getDecFieldObj(HandlerList.class, null, "allLists")).clone();
        for (HandlerList handler : oldList) {
            newList.add(handler);
        }
        Reflections.setDecField(HandlerList.class, null, "allLists", newList);
    }
}
