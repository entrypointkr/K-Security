package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.KAlert;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import javafx.scene.control.Alert;
import org.bukkit.Bukkit;
import org.bukkit.Server;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-01-25.
 */
public class KPluginManager implements PluginManager {
    private PluginManager parent;
    /**
     * 리플렉션으로 접근하는 플러그인을 지원하기 위한 필드
     * 문제 발생 가능성 있음
     */
    private Server server;
    private Map<Pattern, PluginLoader> fileAssociations = new HashMap<Pattern, PluginLoader>();
    private List<Plugin> plugins = new ArrayList<Plugin>();
    private Map<String, Plugin> lookupNames = new HashMap<String, Plugin>();
    private static File updateDirectory = null;
    private SimpleCommandMap commandMap;
    private Map<String, Permission> permissions = new HashMap<String, Permission>();
    private Map<Boolean, Set<Permission>> defaultPerms = new LinkedHashMap<Boolean, Set<Permission>>();
    private Map<String, Map<Permissible, Boolean>> permSubs = new HashMap<String, Map<Permissible, Boolean>>();
    private Map<Boolean, Map<Permissible, Boolean>> defSubs = new HashMap<Boolean, Map<Permissible, Boolean>>();
    private boolean useTimings = false;

    @SuppressWarnings("unchecked")
    public KPluginManager(PluginManager parent) {
        this.parent = parent;
        try {
            server = Bukkit.getServer();
            plugins = (List<Plugin>) Reflections.getDecFieldObj(parent, "plugins");
            lookupNames = (Map<String, Plugin>) Reflections.getDecFieldObj(parent, "lookupNames");
            updateDirectory = (File) Reflections.getDecFieldObj(parent.getClass(), null, "updateDirectory");
            commandMap = (SimpleCommandMap) Reflections.getDecFieldObj(parent, "commandMap");
            defaultPerms = (Map) Reflections.getDecFieldObj(parent, "defaultPerms");
            permSubs = (Map) Reflections.getDecFieldObj(parent, "permSubs");
            defSubs = (Map) Reflections.getDecFieldObj(parent, "defSubs");
            useTimings = parent.useTimings();
        } catch (Exception e) {
            commandMap = new SimpleCommandMap(Bukkit.getServer());
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
        if (plugin.getName().equals("K-Spam_Community_Edition")) {
            Static.consoleMsg(Lang.LEGACY_VERSION_DETECT.builder());
            new KAlert().setType(Alert.AlertType.ERROR)
                    .setContextText(Lang.LEGACY_VERSION_DETECT.builder().flatBuild())
                    .show();
            return;
        } else if (plugin.getName().equals("SHVaccine")) {
            Static.consoleMsg(Lang.SHVACCINE_DETECT.builder());
            new KAlert().setType(Alert.AlertType.WARNING)
                    .setContextText(Lang.SHVACCINE_DETECT.builder().flatBuild())
                    .show();
        }
        parent.enablePlugin(plugin);
    }

    @Override
    public void disablePlugin(Plugin plugin) {
        if (plugin.getName().equals(KSecurity.inst.getName())) {
            Bukkit.broadcastMessage(Lang.SELF_DEFENCE.builder()
                    .single(Lang.Key.PLUGIN_NAME, Static.getRequestPlugin().getName()).build());
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
