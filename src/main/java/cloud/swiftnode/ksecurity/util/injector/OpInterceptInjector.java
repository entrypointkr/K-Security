package cloud.swiftnode.ksecurity.util.injector;

import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KOperatorMap;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KOperatorSet;
import cloud.swiftnode.ksecurity.util.Reflections;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Junhyeong Lim on 2017-02-18.
 */
public class OpInterceptInjector {
    @SuppressWarnings("unchecked")
    public static void inject() throws Exception {
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
    }

    public static void inject(CountDownLatch latch) {
        try {
            inject();
        } catch (Exception ex) {
            // Ignore
        }
        latch.countDown();
    }
}
