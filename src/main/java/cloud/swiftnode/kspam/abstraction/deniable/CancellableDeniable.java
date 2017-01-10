package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.ExecuteDeniable;
import org.bukkit.event.Cancellable;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class CancellableDeniable extends ExecuteDeniable {
    private Cancellable cancellable;

    public CancellableDeniable(Mode mode, Cancellable cancellable) {
        super(mode);
        this.cancellable = cancellable;
    }

    @Override
    public void executeDeny() {
        cancellable.setCancelled(true);
    }
}
