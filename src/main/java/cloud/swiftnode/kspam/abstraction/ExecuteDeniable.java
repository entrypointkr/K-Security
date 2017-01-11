package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;

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
            Static.runTaskLater(new Runnable() {
                @Override
                public void run() {
                    executeDeny();
                }
            }, 20);
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
