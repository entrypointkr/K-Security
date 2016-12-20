package cloud.swiftnode.kspam.abstraction.processer;

import cloud.swiftnode.kspam.abstraction.PunishProcesser;
import cloud.swiftnode.kspam.storage.SpamStorage;
import org.bukkit.event.Event;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class PunishEventProcesser extends PunishProcesser {
    private Event event;

    public PunishEventProcesser(SpamStorage storage, Event event) {
        super(storage);
        this.event = event;
    }

    @Override
    public void process() {
    }
}
