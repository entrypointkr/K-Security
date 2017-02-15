package cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.Deniable;
import cloud.swiftnode.ksecurity.util.Lang;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class EmptyDeniable implements Deniable {
    @Override
    public void deny() {

    }

    @Override
    public Lang.MessageBuilder getDenyMsg() {
        return null;
    }

    @Override
    public void setDenyMsg(Lang.MessageBuilder msg) {

    }
}
