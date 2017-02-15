package cloud.swiftnode.ksecurity.module.kgui.abstraction.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Junhyeong Lim on 2017-02-15.
 */
public class FxLogEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private String log;

    public FxLogEvent(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
