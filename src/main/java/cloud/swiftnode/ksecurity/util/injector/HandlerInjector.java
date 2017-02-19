package cloud.swiftnode.ksecurity.util.injector;

import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KRegisteredListenerArrayList;
import cloud.swiftnode.ksecurity.util.Reflections;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-02-19.
 */
public class HandlerInjector {
    @SuppressWarnings("unchecked")
    public static void inject() throws Exception {
        List<HandlerList> handlerList = HandlerList.getHandlerLists();
        for (HandlerList handler : handlerList) {
            EnumMap<EventPriority, ArrayList<RegisteredListener>> handlerSlots =
                    (EnumMap<EventPriority, ArrayList<RegisteredListener>>) Reflections.getDecFieldObj(handler.getClass(), handler, "handlerslots");
            for (EventPriority priority : handlerSlots.keySet()) {
                handlerSlots.put(priority,
                        new KRegisteredListenerArrayList(handlerSlots.get(priority)));
            }
        }
        HandlerList.unregisterAll();
    }
}
