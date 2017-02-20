package cloud.swiftnode.ksecurity.module.kvaccine;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.abstraction.StorageCountDownLatch;
import cloud.swiftnode.ksecurity.abstraction.mock.MockPlugin;
import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.gui.KAlert;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KHandlerList;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KOperatorMap;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KOperatorSet;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KProxySelector;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor.VirusScanProcessor;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.injector.HandlerInjector;
import cloud.swiftnode.ksecurity.util.injector.OpInterceptInjector;
import cloud.swiftnode.ksecurity.util.injector.PluginManagerInjector;
import cloud.swiftnode.ksecurity.util.injector.ProxyInjector;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * Created by Junhyeong Lim on 2017-01-31.
 */
public class KVaccine extends Module {

    public KVaccine(JavaPlugin parent) {
        super(parent);
    }

    @Override
    public void onEnable() throws Exception {
        // Virus Scan
        Static.runTaskAsync(() -> new VirusScanProcessor().process());
    }

    @SuppressWarnings("unchecked")
    private void startWatcherThread(HashStorage storage) throws Exception {
        Object playerList = Reflections.getDecFieldObj(Bukkit.getServer(), "playerList");
        Class playerListCls = playerList.getClass().getSuperclass();
        Field fieldA = playerListCls.getDeclaredField("operators");
        fieldA.setAccessible(true);
        Field fieldB = Bukkit.getServer().getClass().getDeclaredField("pluginManager");
        fieldB.setAccessible(true);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    System.out.println(Bukkit.getConsoleSender().getClass().getName());
                    Object objA = fieldA.get(playerList);
                    Object objB = fieldB.get(Bukkit.getServer());
                    Object objC = new Object();
                    boolean list = false;
                    Plugin findPlugin = new MockPlugin(false);

                    try {
                        objC = Reflections.getDecFieldObj(objA.getClass().getSuperclass(), objA, "d");
                    } catch (Exception ex) {
                        // Ignore
                    }

                    for (HandlerList handler : HandlerList.getHandlerLists()) {
                        if (!(handler instanceof KHandlerList)) {
                            list = true;
                            break;
                        }
                    }

                    for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                        if (plugin.equals(KSecurity.inst)) {
                            findPlugin = plugin;
                        }
                    }

                    if (!(ProxySelector.getDefault() instanceof KProxySelector)) {
                        ProxySelector.setDefault(new KProxySelector(ProxySelector.getDefault()));
                    }

                    if (!(objA instanceof KOperatorSet) && !(objC instanceof KOperatorMap)
                            || objB.hashCode() != storage.getHash()
                            || KSecurity.inst == null
                            || !KSecurity.inst.isEnabled()
                            || !findPlugin.isEnabled()
                            || list) {
                        detect(Lang.DAMAGE_DETECT.builder(), storage);
                    }
                } catch (InterruptedException ex) {
                    shutdown();
                } catch (Exception ex) {
                    detect(Lang.DAMAGE_EXCEPTION_DETECT.builder().addKey(Lang.Key.VALUE).addVal(ex.toString()), storage);
                }
            }
        }).start();
    }

    private void detect(Lang.MessageBuilder builder, HashStorage storage) {
        try {
            StorageCountDownLatch<Optional<ButtonType>> latch = new StorageCountDownLatch<>(1);
            Static.log(builder);
            new KAlert().setType(Alert.AlertType.ERROR)
                    .setButton(ButtonType.YES, ButtonType.NO)
                    .setContextText(builder.flatBuild())
                    .showAndWait(latch);
            latch.await();
            Optional<ButtonType> btn = latch.getValue();

            if (btn.isPresent() && btn.get() == ButtonType.YES) {
                shutdown();
            } else {
                inject();

                storage.setHash(PluginManagerInjector.process());
                PluginManager manager = Bukkit.getPluginManager();
                Plugin plugin = manager.getPlugin("K-Security");
                List<Plugin> plugins = new ArrayList<>(Arrays.asList(Bukkit.getPluginManager().getPlugins()));
                if (plugin == null) {
                    plugin = new MockPlugin();
                    plugin.onEnable();
                }
                if (!plugins.contains(plugin)) {
                    addPlugin(manager, plugin);
                }
                if (!plugin.isEnabled()) {
                    Reflections.setDecField(JavaPlugin.class, plugin, "isEnabled", true);
                }
                KSecurity.inst = plugin;
            }
        } catch (InterruptedException ex) {
            // Ignore
        } catch (Exception ex) {
            shutdown();
        }
    }

    @Override
    public String getSimpleVersion() {
        return "1.1";
    }

    @Override
    public void onLoad() throws Exception {
        // ETC
        inject();

        // PluginManager
        HashStorage hash = new HashStorage();
        hash.setHash(PluginManagerInjector.process());
        startWatcherThread(hash);
    }

    @SuppressWarnings("unchecked")
    public void addPlugin(PluginManager manager, Plugin plugin) throws NoSuchFieldException, IllegalAccessException {
        List<Plugin> plugins = (List<Plugin>) Reflections.getDecFieldObj(manager, "plugins");
        plugins.add(plugin);
        Reflections.setDecField(parent, "plugins", plugins);
    }

    private void inject() throws Exception {
        ProxyInjector.inject();
        OpInterceptInjector.inject();
        HandlerInjector.inject();
    }

    private void shutdown() {
        Bukkit.savePlayers();
        for (World world : Bukkit.getWorlds()) {
            world.save();
        }
        Bukkit.shutdown();
    }

    private class HashStorage {
        private int hash;

        public int getHash() {
            return hash;
        }

        public void setHash(int hash) {
            this.hash = hash;
        }
    }
}
