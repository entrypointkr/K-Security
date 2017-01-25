package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.abstraction.asm.KClassVisitor;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Reflections;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class VirusScanProcessor implements Processor {
    @Override
    public boolean process() {
        List loaderList = new ArrayList<>();
        Map<String, Collection<Class<?>>> pluginClassMap = new HashMap<>();
        int detectCount = 0;

        // Init
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            PluginLoader loader = plugin.getPluginLoader();
            try {
                loaderList.addAll((List) Reflections.getDeclaredFieldObject(loader, "loaders"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Sort
        for (Object obj : loaderList) {
            PluginClassLoaderFacade facade = new PluginClassLoaderFacade(obj);
            try {
                pluginClassMap.put(facade.getPlugin().getName(), facade.getClasses());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // Find
        for (String key : pluginClassMap.keySet()) {
            KClassVisitor classVisitor = new KClassVisitor(Opcodes.ASM5);
            for (Class<?> clazz : pluginClassMap.get(key)) {
                ClassReader reader = null;
                try {
                    reader = new ClassReader(clazz.getClassLoader().getResourceAsStream(clazz.getName().replaceAll("\\.", "/") + ".class"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                reader.accept(classVisitor, 0);
            }
            if (classVisitor.find) {
                Bukkit.broadcastMessage(Lang.SOCKET_DETECTED.builder()
                        .single(Lang.Key.PLUGIN_NAME, key)
                        .build());
                detectCount += 1;
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
                .addVal(coloredCount, pluginClassMap.keySet().size())
                .build());
        return true;
    }

    class PluginClassLoaderFacade {
        private Object handle;

        public PluginClassLoaderFacade(Object handle) {
            this.handle = handle;
        }

        public Plugin getPlugin() throws NoSuchFieldException, IllegalAccessException {
            return (Plugin) Reflections.getDeclaredFieldObject(handle, "plugin");
        }

        public Collection<Class<?>> getClasses() throws NoSuchFieldException, IllegalAccessException {
            Map<String, Class<?>> classMap = (Map<String, Class<?>>) Reflections.getDeclaredFieldObject(handle, "classes");
            return classMap.values();
        }
    }
}
