package cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.Deniable;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class EmptyDeniable implements Deniable {
    @Override
    public void deny() {
        // Empty
    }

    @Override
    public String getDenyMsg() {
        return "";
    }

    @Override
    public void setDenyMsg(String msg) {
        // Empty
    }
}
