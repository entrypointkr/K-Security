package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor;

import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KOperatorMap;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KOperatorSet;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KProxySelector;
import cloud.swiftnode.ksecurity.util.InterceptorFactory;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.ProxySelector;
import java.util.Map;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-01-25.
 */
public class LowInjector {
    public static int process() {
        try {
            ProxySelector selector = ProxySelector.getDefault();
            if (!(selector instanceof KProxySelector)
                    || (selector.getClass() != KProxySelector.class)) {
                ProxySelector.setDefault(new KProxySelector(ProxySelector.getDefault()));
            }

            // OP Intercept
            Object playerList = Reflections.getDecFieldObj(Bukkit.getServer(), "playerList");
            Class playerListCls = playerList.getClass().getSuperclass();
            Object operators = Reflections.getDecFieldObj(playerListCls, playerList, "operators");
            if (operators instanceof Set) {
                Set operatorSet = (Set) operators;
                Set<String> kOperatorSet = new KOperatorSet();
                kOperatorSet.addAll(operatorSet);
                Reflections.setDecField(playerListCls, playerList, "operators", kOperatorSet);
            } else if (operators.getClass().getName().contains("OpList")) {
                Class opListSuperCls = operators.getClass().getSuperclass();
                Map map = (Map) Reflections.getDecFieldObj(opListSuperCls, operators, "d");
                KOperatorMap newMap = new KOperatorMap();
                newMap.putAll(map);
                Reflections.setDecField(opListSuperCls, operators, "d", newMap);
            }

            PluginManager manager = Bukkit.getPluginManager();
            PluginManager newManager = getManager();

            for (Field field : manager.getClass().getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object obj = field.get(manager);
                    Reflections.setDecField(newManager, field.getName(), obj);
                } catch (Exception ex) {
                    // Ignore
                }
            }

            Reflections.setDecField(Bukkit.getServer(), "pluginManager", newManager);
            return newManager.hashCode();
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
        return -1;
    }

    private static PluginManager getManager() throws Exception {
        Constructor constructor = InterceptorFactory.createKPluginManager().getConstructor(Server.class, SimpleCommandMap.class);
        return (PluginManager) constructor.newInstance(null, null);
    }
}
