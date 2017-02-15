package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.gui.KAlert;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import javafx.scene.control.Alert;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-01-25.
 */
public final class KPluginManager implements PluginManager {
    private PluginManager parent;
    /**
     * 리플렉션으로 접근하는 플러그인을 지원하기 위한 필드
     * 문제 발생 가능성 있음
     */
    private Server server;
    private Map<Pattern, PluginLoader> fileAssociations = new HashMap<>();
    private List<Plugin> plugins = new ArrayList<Plugin>();
    private Map<String, Plugin> lookupNames = new HashMap<String, Plugin>();
    private static File updateDirectory = null;
    private SimpleCommandMap commandMap;
    private Map<String, Permission> permissions = new HashMap<>();
    private Map<Boolean, Set<Permission>> defaultPerms = new LinkedHashMap<>();
    private Map<String, Map<Permissible, Boolean>> permSubs = new HashMap<>();
    private Map<Boolean, Map<Permissible, Boolean>> defSubs = new HashMap<>();
    private boolean useTimings = false;

    // 필요성 검토 필요
    @SuppressWarnings("unchecked")
    public KPluginManager(PluginManager parent) {
        ProxyBuilder pxBuilder = new ProxyBuilder();
        KMethodIntercepter.KMethodIntercepterBuilder icBuilder = new KMethodIntercepter.KMethodIntercepterBuilder(parent);

        this.parent = parent;

        List<String> escapeList = new ArrayList<>(Arrays.asList("server", "parent", "useTimings",
                "updateDirectory", "commandMap"));
        try {
            for (Field field : getClass().getDeclaredFields()) {
                try {
                    if (escapeList.contains(field.getName())) continue;
                    field.setAccessible(true);
                    Object obj = Reflections.getDecFieldObj(parent, field.getName());
                    pxBuilder.setCallback(icBuilder.build(field.getName()));
                    Class<?>[] interfaces = obj.getClass().getInterfaces();
                    if (interfaces != null && interfaces.length > 0) {
                        pxBuilder.setInterfaces(obj.getClass().getInterfaces());
                    }
                    field.set(this, pxBuilder.build());
                } catch (Exception ex) {
                    // Ignore
                }
            }

            server = Bukkit.getServer();
            useTimings = parent.useTimings();
            updateDirectory = (File) Reflections.getDecFieldObj(parent, "updateDirectory");

            pxBuilder.setCallback(icBuilder.build("commandMap"));
            pxBuilder.setInterfaces(CommandMap.class);
            pxBuilder.setSuperClass(SimpleCommandMap.class);
            pxBuilder.setArgumentTypes(Server.class);
            pxBuilder.setArguments(server);
            commandMap = pxBuilder.build();

        } catch (Exception e) {
            // Ignore
        }
    }

    class ProxyBuilder {
        private Class<?> superCls;
        private Class[] interfaces;
        private Callback callback;
        private Class[] argumentTypes;
        private Object[] arguments;

        public ProxyBuilder setSuperClass(Class<?> superCls) {
            this.superCls = superCls;
            return this;
        }

        public ProxyBuilder setInterfaces(Class<?>... interfaces) {
            this.interfaces = interfaces;
            return this;
        }

        public ProxyBuilder setCallback(Callback callback) {
            this.callback = callback;
            return this;
        }

        public ProxyBuilder setArgumentTypes(Class<?>... argumentTypes) {
            this.argumentTypes = argumentTypes;
            return this;
        }

        public ProxyBuilder setArguments(Object... arguments) {
            this.arguments = arguments;
            return this;
        }

        public <T> T build() {
            Enhancer enhancer = new Enhancer();
            enhancer.setClassLoader(getClass().getClassLoader());
            if (superCls != null) {
                enhancer.setSuperclass(superCls);
            }
            if (interfaces != null) {
                enhancer.setInterfaces(interfaces);
            }
            if (callback != null) {
                enhancer.setCallback(callback);
            }

            T ret;
            if (argumentTypes != null && arguments != null) {
                ret = (T) enhancer.create(argumentTypes, arguments);
            } else {
                ret = (T) enhancer.create();
            }
            flush();
            return ret;
        }

        private void flush() {
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    field.set(this, null);
                } catch (Exception ex) {
                    // Ignore
                }
            }
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
            Static.log(Lang.SELF_DEFENCE.builder()
                    .single(Lang.Key.PLUGIN_NAME, Static.getRequestPlugin().getName()));
            Bukkit.broadcastMessage(Lang.SELF_DEFENCE.builder().build());
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

    public void addPlugin(Plugin plugin) throws NoSuchFieldException, IllegalAccessException {
        List<Plugin> plugins = (List<Plugin>) Reflections.getDecFieldObj(parent, "plugins");
        plugins.add(plugin);
        Reflections.setDecField(parent, "plugins", plugins);
    }
}
