package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.abstraction.asm.KClassVisitor;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Reflections;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class VirusScanProcessor implements Processor {
    @Override
    public boolean process() {
        int detectCount = 0;

        Map<ClassLoader, Plugin> pluginMap = getPluginMap();
        List<Class<?>> classList;
        List<String> escapeList = new ArrayList<>();

        try {
            classList = new PluginClassLoaderFacade(KSpam.INSTANCE.getPluginLoader()).getClasses();
        } catch (Exception e) {
            Static.consoleMsg(e);
            return false;
        }

        // Process
        for (Class<?> cls : classList) {
            Plugin plugin = pluginMap.get(cls.getClassLoader());

            if (escapeList.contains(plugin.getName())) continue;

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
            Bukkit.broadcastMessage(Lang.SCAN_WARNING.builder()
                    .build());
        } else {
            Bukkit.broadcastMessage(Lang.SCAN_SAFE.builder()
                    .build());
        }

        Bukkit.broadcastMessage(Lang.SCAN_RESULT.builder()
                .addKey(Lang.Key.FIND_COUNT, Lang.Key.PLUGIN_COUNT)
                .addVal(coloredCount, Bukkit.getPluginManager().getPlugins().length)
                .build());
        return true;
    }

    class PluginClassLoaderFacade {
        private Object handle;

        public PluginClassLoaderFacade(Object handle) {
            this.handle = handle;
        }

        public List<Class<?>> getClasses() throws NoSuchFieldException, IllegalAccessException {
            Map<String, Class<?>> classMap = (Map<String, Class<?>>) Reflections.getDeclaredFieldObject(handle, "classes");
            return new ArrayList<>(classMap.values());
        }
    }

    private Map<ClassLoader, Plugin> getPluginMap() {
        Map<ClassLoader, Plugin> pluginMap = new HashMap<>();
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            pluginMap.put(plugin.getClass().getClassLoader(), plugin);
        }
        return pluginMap;
    }
}
