package cloud.swiftnode.ksecurity.module.kanticheat.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Created by Junhyeong Lim on 2017-02-11.
 */
public class PlayerCheatEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private CheatType type;

    public PlayerCheatEvent(Player who, CheatType type) {
        super(who);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public CheatType getType() {
        return type;
    }

    public void setType(CheatType type) {
        this.type = type;
    }

    public enum CheatType {
        SHOPKEEPER,
    }
}
