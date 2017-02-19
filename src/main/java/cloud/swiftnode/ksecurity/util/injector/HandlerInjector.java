package cloud.swiftnode.ksecurity.util.injector;

import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KHandlerArrayList;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KRegisteredListenerArrayList;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-02-19.
 */
@SuppressWarnings("unchecked")
public class HandlerInjector {
    public static void inject() throws Exception {
        List<HandlerList> handlerList = HandlerList.getHandlerLists();
        for (HandlerList handler : handlerList) {
            wrapping(handler);
        }
        Reflections.setDecField(HandlerList.class, null, "allLists",
                new KHandlerArrayList((Collection<? extends HandlerList>) Reflections.getDecFieldObj(HandlerList.class, null, "allLists")));
    }

    public static void wrapping(HandlerList handler) {
        try {
            EnumMap<EventPriority, ArrayList<RegisteredListener>> handlerSlots =
                    (EnumMap<EventPriority, ArrayList<RegisteredListener>>) Reflections.getDecFieldObj(handler.getClass(), handler, "handlerslots");
            for (EventPriority priority : handlerSlots.keySet()) {
                handlerSlots.put(priority,
                        new KRegisteredListenerArrayList(handlerSlots.get(priority)));
            }
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
    }
}
