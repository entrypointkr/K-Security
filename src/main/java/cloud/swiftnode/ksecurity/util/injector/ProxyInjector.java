package cloud.swiftnode.ksecurity.util.injector;

import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KProxySelector;

import java.net.ProxySelector;

/**
 * Created by Junhyeong Lim on 2017-02-18.
 */
public class ProxyInjector {
    public static void inject() {
        ProxySelector selector = ProxySelector.getDefault();
        if (!(selector instanceof KProxySelector)
                || (selector.getClass() != KProxySelector.class)) {
            ProxySelector.setDefault(new KProxySelector(ProxySelector.getDefault()));
        }
    }
}
