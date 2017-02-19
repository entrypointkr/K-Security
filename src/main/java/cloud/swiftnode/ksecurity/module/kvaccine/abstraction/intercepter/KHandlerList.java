package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import cloud.swiftnode.ksecurity.KSecurity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Junhyeong Lim on 2017-02-20.
 */
public final class KHandlerList extends HandlerList {
    private HandlerList parent;

    public KHandlerList(HandlerList parent) {
        this.parent = parent;
    }

    public static void bakeAll() {
        HandlerList.bakeAll();
    }

    public static void unregisterAll() {
        HandlerList.unregisterAll();
    }

    public static void unregisterAll(Plugin plugin) {
        HandlerList.unregisterAll(plugin);
    }

    public static void unregisterAll(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    @Override
    public void register(RegisteredListener listener) {
        parent.register(listener);
    }

    @Override
    public void registerAll(Collection<RegisteredListener> listeners) {
        parent.registerAll(listeners);
    }

    @Override
    public void unregister(RegisteredListener listener) {
        if (listener.getPlugin().equals(KSecurity.inst)) {
            return;
        }
        parent.unregister(listener);
    }

    @Override
    public void unregister(Plugin plugin) {
        if (plugin.equals(KSecurity.inst)) {
            return;
        }
        parent.unregister(plugin);
    }

    @Override
    public void unregister(Listener listener) {
        if (listener.getClass().getName().contains("cloud.swiftnode.ksecurity")) {
            return;
        }
        parent.unregister(listener);
    }

    @Override
    public void bake() {
        parent.bake();
    }

    @Override
    public RegisteredListener[] getRegisteredListeners() {
        return parent.getRegisteredListeners();
    }

    public static ArrayList<RegisteredListener> getRegisteredListeners(Plugin plugin) {
        return HandlerList.getRegisteredListeners(plugin);
    }

    public static ArrayList<HandlerList> getHandlerLists() {
        return HandlerList.getHandlerLists();
    }
}
