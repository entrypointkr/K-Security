package cloud.swiftnode.ksecurity.abstraction;

import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class ExecuteDeniable implements Deniable {
    protected boolean delayed = false;
    protected String denyMsg;

    public ExecuteDeniable(boolean delayed) {
        this.delayed = delayed;
        this.denyMsg = Lang.DENY.toString();
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
    public String getDenyMsg() {
        return denyMsg;
    }

    @Override
    public void setDenyMsg(String msg) {
        this.denyMsg = msg;
    }
}
