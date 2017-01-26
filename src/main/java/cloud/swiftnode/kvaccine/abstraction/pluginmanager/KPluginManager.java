package cloud.swiftnode.kvaccine.abstraction.pluginmanager;

import cloud.swiftnode.kvaccine.KVaccine;
import cloud.swiftnode.kvaccine.util.Lang;
import cloud.swiftnode.kvaccine.util.Reflections;
import cloud.swiftnode.kvaccine.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;

import java.io.File;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-01-25.
 */
public class KPluginManager implements PluginManager {
    private PluginManager parent;
    /**
     * 리플렉션으로 commandMap 에 접근하는 플러그인을 지원하기 위한 필드
     */
    private SimpleCommandMap commandMap;

    public KPluginManager(PluginManager parent) {
        this.parent = parent;
        try {
            this.commandMap = (SimpleCommandMap) Reflections.getDeclaredFieldObject(parent, "commandMap");
        } catch (Exception e) {
            this.commandMap = new SimpleCommandMap(Bukkit.getServer());
        }
    }

    @Override
    public void registerInterface(Class<? extends PluginLoader> loader) throws IllegalArgumentException {
        parent.registerInterface(loader);
    }

    @Override
    public Plugin getPlugin(String name) {
        return parent.getPlugin(name);
    }

    @Override
    public Plugin[] getPlugins() {
        return parent.getPlugins();
    }

    @Override
    public boolean isPluginEnabled(String name) {
        return parent.isPluginEnabled(name);
    }

    @Override
    public boolean isPluginEnabled(Plugin plugin) {
        return parent.isPluginEnabled(plugin);
    }

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
        return parent.loadPlugin(file);
    }

    @Override
    public Plugin[] loadPlugins(File directory) {
        return parent.loadPlugins(directory);
    }

    @Override
    public void disablePlugins() {
        parent.disablePlugins();
    }

    @Override
    public void clearPlugins() {
        parent.clearPlugins();
    }

    @Override
    public void callEvent(Event event) throws IllegalStateException {
        parent.callEvent(event);
    }

    @Override
    public void registerEvents(Listener listener, Plugin plugin) {
        parent.registerEvents(listener, plugin);
    }

    @Override
    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin) {
        parent.registerEvent(event, listener, priority, executor, plugin);
    }

    @Override
    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled) {
        parent.registerEvent(event, listener, priority, executor, plugin, ignoreCancelled);
    }

    @Override
    public void enablePlugin(Plugin plugin) {
        parent.enablePlugin(plugin);
    }

    @Override
    public void disablePlugin(Plugin plugin) {
        if (plugin.getName().equals(KVaccine.INSTANCE.getName())) {
            Static.consoleMsg(Lang.SELF_DEFENCE.builder().build());
            return;
        }
        parent.disablePlugin(plugin);
    }

    @Override
    public Permission getPermission(String name) {
        return parent.getPermission(name);
    }

    @Override
    public void addPermission(Permission perm) {
        parent.addPermission(perm);
    }

    @Override
    public void removePermission(Permission perm) {
        parent.removePermission(perm);
    }

    @Override
    public void removePermission(String name) {
        parent.removePermission(name);
    }

    @Override
    public Set<Permission> getDefaultPermissions(boolean op) {
        return parent.getDefaultPermissions(op);
    }

    @Override
    public void recalculatePermissionDefaults(Permission perm) {
        parent.recalculatePermissionDefaults(perm);
    }

    @Override
    public void subscribeToPermission(String permission, Permissible permissible) {
        parent.subscribeToPermission(permission, permissible);
    }

    @Override
    public void unsubscribeFromPermission(String permission, Permissible permissible) {
        parent.unsubscribeFromPermission(permission, permissible);
    }

    @Override
    public Set<Permissible> getPermissionSubscriptions(String permission) {
        return parent.getPermissionSubscriptions(permission);
    }

    @Override
    public void subscribeToDefaultPerms(boolean op, Permissible permissible) {
        parent.subscribeToDefaultPerms(op, permissible);
    }

    @Override
    public void unsubscribeFromDefaultPerms(boolean op, Permissible permissible) {
        parent.unsubscribeFromDefaultPerms(op, permissible);
    }

    @Override
    public Set<Permissible> getDefaultPermSubscriptions(boolean op) {
        return parent.getDefaultPermSubscriptions(op);
    }

    @Override
    public Set<Permission> getPermissions() {
        return parent.getPermissions();
    }

    @Override
    public boolean useTimings() {
        return parent.useTimings();
    }
}
