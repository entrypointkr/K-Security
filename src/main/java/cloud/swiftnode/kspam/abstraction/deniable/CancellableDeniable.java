package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.Deniable;
import org.bukkit.event.Cancellable;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class CancellableDeniable implements Deniable {
    private Cancellable cancellable;

    public CancellableDeniable(Cancellable cancellable) {
        this.cancellable = cancellable;
    }

    @Override
    public void deny() {
        cancellable.setCancelled(true);
    }
}
