package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.Static;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class ExecuteDeniable implements Deniable {
    protected boolean async = false;

    public ExecuteDeniable(boolean async) {
        this.async = async;
    }

    @Override
    public void deny() {
        if (async) {
            Static.runTaskAsync(new Runnable() {
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

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
