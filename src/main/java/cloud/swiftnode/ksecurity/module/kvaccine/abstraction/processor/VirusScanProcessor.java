package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.abstraction.Processor;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.KTray;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.asm.KClassVisitor;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import com.github.plushaze.traynotification.notification.Notifications;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class VirusScanProcessor implements Processor {
    @Override
    public boolean process() {
        Bukkit.broadcastMessage(Lang.SCAN_START.builder().build());

        int detectCount = 0;
        long startTime = System.currentTimeMillis();

        Map<ClassLoader, Plugin> pluginMap = getPluginMap();
        List<Class<?>> classList;
        List<String> escapeList = new ArrayList<>();

        try {
            classList = new PluginClassLoaderFacade(KSecurity.inst.getPluginLoader()).getClasses();
        } catch (Exception e) {
            Static.consoleMsg(e);
            return false;
        }

        // Process
        for (Class<?> cls : classList) {
            Plugin plugin = pluginMap.get(cls.getClassLoader());

            if (plugin == null || escapeList.contains(plugin.getName())) continue;

            KClassVisitor classVisitor = new KClassVisitor(Opcodes.ASM5);
            ClassReader reader;
            try {
                reader = new ClassReader(cls.getClassLoader().getResourceAsStream(cls.getName().replaceAll("\\.", "/") + ".class"));
            } catch (Exception e) {
                continue;
            }
            reader.accept(classVisitor, 0);
            if (classVisitor.find) {
                Bukkit.broadcastMessage(Lang.SOCKET_DETECTED.builder()
                        .single(Lang.Key.PLUGIN_NAME, plugin.getName())
                        .build());
                detectCount += 1;
                escapeList.add(plugin.getName());
            }
        }

        // Broadcast
        String coloredCount = "&a" + detectCount + "&f";

        if (detectCount > 0) {
            coloredCount = "&c" + detectCount + "&f";
            Bukkit.broadcastMessage(Lang.SCAN_WARNING.builder().build());
            new KTray().setMessage(Lang.SCAN_WARNING.builder().flatBuild())
                    .setNotify(Notifications.ERROR)
                    .showAndDismiss(5);
        } else {
            Bukkit.broadcastMessage(Lang.SCAN_SAFE.builder()
                    .build());
            new KTray().setMessage(Lang.SCAN_SAFE.builder().flatBuild())
                    .setNotify(Notifications.SUCCESS)
                    .showAndDismiss(5);
        }

        Bukkit.broadcastMessage(Lang.SCAN_RESULT.builder()
                .addKey(Lang.Key.FIND_COUNT, Lang.Key.PLUGIN_COUNT, Lang.Key.TIME)
                .addVal(coloredCount, Bukkit.getPluginManager().getPlugins().length, System.currentTimeMillis() - startTime)
                .build());
        return true;
    }

    private Map<ClassLoader, Plugin> getPluginMap() {
        Map<ClassLoader, Plugin> pluginMap = new HashMap<>();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            pluginMap.put(plugin.getClass().getClassLoader(), plugin);
        }
        return pluginMap;
    }

    class PluginClassLoaderFacade {
        private Object handle;

        public PluginClassLoaderFacade(Object handle) {
            this.handle = handle;
        }

        public List<Class<?>> getClasses() throws NoSuchFieldException, IllegalAccessException {
            List<Class<?>> ret = new ArrayList<>();
            @SuppressWarnings("unchecked")
            Map<String, Class<?>> classMap = (Map<String, Class<?>>) Reflections.getDecFieldObj(handle, "classes");
            Class<?>[] classes = classMap.values().toArray(new Class<?>[classMap.values().size()]);
            Collections.addAll(ret, classes);
            return ret;
        }
    }
}
