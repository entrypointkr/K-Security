package cloud.swiftnode.ksecurity.module.kgui.abstraction.gui;

import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Junhyeong Lim on 2017-02-14.
 */
public class LogItem {
    private SimpleStringProperty time;
    private SimpleStringProperty log;

    public LogItem(SimpleStringProperty time, SimpleStringProperty log) {
        this.time = time;
        this.log = log;
    }

    public LogItem(String log) {
        this.time = new SimpleStringProperty(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
        this.log = new SimpleStringProperty(log);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getLog() {
        return log.get();
    }

    public SimpleStringProperty logProperty() {
        return log;
    }

    public void setLog(String log) {
        this.log.set(log);
    }
}
