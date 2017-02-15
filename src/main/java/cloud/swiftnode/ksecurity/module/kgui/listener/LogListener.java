package cloud.swiftnode.ksecurity.module.kgui.listener;

import cloud.swiftnode.ksecurity.module.kgui.abstraction.event.FxLogEvent;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.gui.LogItem;
import javafx.scene.control.TableView;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


/**
 * Created by Junhyeong Lim on 2017-02-15.
 */
public class LogListener implements Listener {
    private TableView<LogItem> view;

    public LogListener(TableView<LogItem> view) {
        this.view = view;
    }

    @EventHandler
    public void onLog(FxLogEvent e) {
        LogItem item = new LogItem(e.getLog());
        view.getItems().add(item);
    }
}
