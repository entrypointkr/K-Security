package cloud.swiftnode.ksecurity.module.kvaccine;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.abstraction.StorageCountDownLatch;
import cloud.swiftnode.ksecurity.abstraction.mock.MockPlugin;
import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.KAlert;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KOperatorMap;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KOperatorSet;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KPluginManager;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KProxySelector;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor.HighInjectionProcessor;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor.LowInjectionProcessor;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor.VirusScanProcessor;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.net.ProxySelector;
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
        // Injection
        Static.runTask(() -> new HighInjectionProcessor().process());

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
                    Object objA = fieldA.get(playerList);
                    Object objB = fieldB.get(Bukkit.getServer());
                    Object objC = new Object();

                    try {
                        objC = Reflections.getDecFieldObj(objA.getClass().getSuperclass(), objA, "d");
                    } catch (Exception ex) {
                        // Ignore
                    }

                    if (!(objA instanceof KOperatorSet) && !(objC instanceof KOperatorMap)
                            || objA.getClass() != KOperatorSet.class && objC.getClass() != KOperatorMap.class
                            || !(objB instanceof KPluginManager)
                            || objB.getClass() != KPluginManager.class
                            || ProxySelector.getDefault().getClass() != KProxySelector.class
                            || KSecurity.inst == null) {
                        detect(Lang.DAMAGE_DETECT.builder());
                    }
                } catch (InterruptedException ex) {
                    shutdown();
                } catch (Exception ex) {
                    detect(Lang.DAMAGE_EXCEPTION_DETECT.builder().addKey(Lang.Key.VALUE).addVal(ex.toString()));
                }
            }
        }).start();
    }

    private void detect(Lang.MessageBuilder builder) {
        try {
            StorageCountDownLatch<Optional<ButtonType>> latch = new StorageCountDownLatch<>(1);
            Bukkit.broadcastMessage(builder.clone().build());
            new KAlert().setType(Alert.AlertType.ERROR)
                    .setButton(ButtonType.YES, ButtonType.NO)
                    .setContextText(builder.clone().flatBuild())
                    .showAndWait(latch);
            latch.await();
            Optional<ButtonType> btn = latch.getValue();

            if (btn.get() == ButtonType.YES) {
                shutdown();
            } else {
                Plugin plugin = Bukkit.getPluginManager().getPlugin("K-Security");
                if (plugin != null) {
                    KSecurity.inst = plugin;
                } else {
                    KSecurity.inst = new MockPlugin();
                }
                new HighInjectionProcessor().process();
                new LowInjectionProcessor().process();
            }
        } catch (InterruptedException ex) {
            // Ignore
        } catch (Exception ex) {
            shutdown();
        }
    }

    private void shutdown() {
        Bukkit.savePlayers();
        for (World world : Bukkit.getWorlds()) {
            world.save();
        }
        Bukkit.shutdown();
    }

    @Override
    public String getSimpleVersion() {
        return "1.1";
    }

    @Override
    public void onLoad() {
        new LowInjectionProcessor().process();
    }
}
