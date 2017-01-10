package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.convertor.ObjectToDeniableConverter;
import cloud.swiftnode.kspam.abstraction.convertor.ObjectToInfoConverter;
import org.bukkit.event.player.PlayerEvent;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class DeniableInfoAdapter implements Deniable, Info {
    private Deniable deniable;
    private Info info;

    public DeniableInfoAdapter(Deniable deniable, Info info) {
        this.deniable = deniable;
        this.info = info;
    }

    public DeniableInfoAdapter(PlayerEvent e) {
        this.deniable = new ObjectToDeniableConverter(e).convert();
        this.info = new ObjectToInfoConverter(e).convert();
    }

    @Override
    public String getName() {
        return info.getName();
    }

    @Override
    public String getIp() {
        return info.getIp();
    }

    @Override
    public String getUniqueId() {
        return info.getUniqueId();
    }

    @Override
    public void deny() {
        deniable.deny();
    }
}
