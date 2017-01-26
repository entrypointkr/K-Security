package cloud.swiftnode.kvaccine.abstraction.deniable;

import cloud.swiftnode.kvaccine.abstraction.ExecuteDeniable;
import org.bukkit.event.Cancellable;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class CancellableDeniable extends ExecuteDeniable {
    private Cancellable cancellable;

    public CancellableDeniable(boolean delayed, Cancellable cancellable) {
        super(delayed);
        this.cancellable = cancellable;
    }

    @Override
    public void executeDeny() {
        cancellable.setCancelled(true);
    }
}
