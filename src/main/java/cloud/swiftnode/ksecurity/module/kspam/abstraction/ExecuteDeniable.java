package cloud.swiftnode.ksecurity.module.kspam.abstraction;

import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class ExecuteDeniable implements Deniable {
    protected boolean delayed = false;
    protected Lang.MessageBuilder denyMsg;

    public ExecuteDeniable(boolean delayed) {
        this.delayed = delayed;
        this.denyMsg = Lang.DENY.builder();
    }

    @Override
    public void deny() {
        if (delayed) {
            Static.runTask(this::executeDeny);
        } else {
            executeDeny();
        }
    }

    public abstract void executeDeny();

    public boolean isDelayed() {
        return delayed;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    @Override
    public Lang.MessageBuilder getDenyMsg() {
        return denyMsg;
    }

    @Override
    public void setDenyMsg(Lang.MessageBuilder msg) {
        this.denyMsg = msg;
    }
}
