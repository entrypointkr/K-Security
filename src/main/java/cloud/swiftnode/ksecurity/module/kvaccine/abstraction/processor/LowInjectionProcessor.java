package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.Processor;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KOperatorMap;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KOperatorSet;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KProxySelector;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;

import java.net.ProxySelector;
import java.util.Map;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-01-25.
 */
public class LowInjectionProcessor implements Processor {
    @Override
    public boolean process() {
        try {
            // Network
            ProxySelector.setDefault(new KProxySelector(ProxySelector.getDefault()));

            // OP Intercept
            Object playerList = Reflections.getDecFieldObj(Bukkit.getServer(), "playerList");
            Class playerListCls = playerList.getClass().getSuperclass();
            Object operators = Reflections.getDecFieldObj(playerListCls, playerList, "operators");
            if (operators instanceof Set) {
                Set operatorSet = (Set) operators;
                Set<String> kOperatorSet = new KOperatorSet();
                kOperatorSet.addAll(operatorSet);
                Reflections.setDecField(playerListCls, playerList, "operators", kOperatorSet);
            } else if (operators.getClass().getName().contains("OpList")){
                Class opListSuperCls = operators.getClass().getSuperclass();
                Map map = (Map) Reflections.getDecFieldObj(opListSuperCls, operators, "d");
                KOperatorMap newMap = new KOperatorMap();
                newMap.putAll(map);
                Reflections.setDecField(opListSuperCls, operators, "d", newMap);
            }
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
        return true;
    }
}
