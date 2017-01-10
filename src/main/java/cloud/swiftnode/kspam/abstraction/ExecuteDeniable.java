package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.Static;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class ExecuteDeniable implements Deniable {
    protected boolean delayed = false;

    public ExecuteDeniable(boolean delayed) {
        this.delayed = delayed;
    }

    @Override
    public void deny() {
        if (delayed) {
            Static.runTask(new Runnable() {
                @Override
                public void run() {
                    executeDeny();
                }
            });
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

    public enum Mode {
        ASYNC,
        SYNC,
        NONE
    }
}
