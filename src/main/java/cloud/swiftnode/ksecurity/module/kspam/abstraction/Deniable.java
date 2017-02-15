package cloud.swiftnode.ksecurity.module.kspam.abstraction;

import cloud.swiftnode.ksecurity.util.Lang;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public interface Deniable {
    void deny();

    Lang.MessageBuilder getDenyMsg();

    void setDenyMsg(Lang.MessageBuilder msg);
}
