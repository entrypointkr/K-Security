package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.Static;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class ExecuteDeniable implements Deniable {
    protected Mode mode = Mode.NONE;

    public ExecuteDeniable(Mode mode) {
        this.mode = mode;
    }

    @Override
    public void deny() {
        if (mode == Mode.ASYNC) {
            Static.runTaskAsync(new Runnable() {
                @Override
                public void run() {
                    executeDeny();
                }
            });
        } else if (mode == Mode.SYNC) {
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

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public enum Mode {
        ASYNC,
        SYNC,
        NONE
    }
}
