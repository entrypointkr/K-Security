package cloud.swiftnode.ksecurity.module.kgui.abstraction;

import cloud.swiftnode.ksecurity.util.Static;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import org.apache.commons.lang.StringUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Junhyeong Lim on 2017-02-04.
 */
public class KAlert {
    private final CountDownLatch latch = new CountDownLatch(1);
    private final AlertStorage storage = new AlertStorage();

    public KAlert() {
        final Runnable init = () -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("K-Security");
            alert.setHeaderText("K-Security");
            storage.setAlert(alert);
        };
        if (Platform.isFxApplicationThread()) {
            init.run();
            return;
        }
        Platform.runLater(() -> {
            try {
                init.run();
            } finally {
                latch.countDown();
            }
        });
    }

    public KAlert setType(Alert.AlertType type) {
        doAwait(() -> storage.getAlert().setAlertType(type));
        return this;
    }

    public KAlert setHeaderText(String s) {
        doAwait(() -> storage.getAlert().setHeaderText(s));
        return this;
    }

    public KAlert setContextText(String... strs) {
        doAwait(() -> storage.getAlert().setContentText(StringUtils.join(strs, "\n")));
        return this;
    }

    public KAlert setTitle(String s) {
        doAwait(() -> storage.getAlert().setTitle(s));
        return this;
    }

    public KAlert setOnCloseRequest(EventHandler<DialogEvent> val) {
        doAwait(() -> storage.getAlert().setOnCloseRequest(val));
        return this;
    }

    public void show() {
        Platform.runLater(() -> doAwait(() -> storage.getAlert().show()));
    }

    public Alert build() {
        try {
            latch.await();
            return storage.getAlert();
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
        return null;
    }

    private void doAwait(ExRunnable runnable) {
        try {
            if (!Platform.isFxApplicationThread()) {
                latch.await();
            }
            runnable.run();
        } catch (Exception e) {
            Static.consoleMsg(e);
        }
    }

    class AlertStorage {
        private Alert alert;

        public Alert getAlert() {
            return alert;
        }

        public void setAlert(Alert alert) {
            this.alert = alert;
        }
    }

    interface ExRunnable {
        void run() throws Exception;
    }
}
