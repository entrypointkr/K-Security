package cloud.swiftnode.ksecurity.module.kgui.abstraction.gui;

import cloud.swiftnode.ksecurity.abstraction.StorageCountDownLatch;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.Waitable;
import cloud.swiftnode.ksecurity.util.Static;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-02-04.
 */
public class KAlert extends Waitable<Alert> {
    public KAlert() {
        final Runnable init = () -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("K-Security");
            alert.setHeaderText("K-Security");
            storageLatch.setValue(alert);
        };
        if (Platform.isFxApplicationThread()) {
            init.run();
            return;
        }
        Platform.runLater(() -> {
            try {
                init.run();
            } finally {
                storageLatch.countDown();
            }
        });
    }

    public KAlert setType(Alert.AlertType type) {
        doAwait(() -> storageLatch.getValue().setAlertType(type));
        return this;
    }

    public KAlert setHeaderText(String s) {
        doAwait(() -> storageLatch.getValue().setHeaderText(s));
        return this;
    }

    public KAlert setContextText(String... strs) {
        doAwait(() -> storageLatch.getValue().setContentText(StringUtils.join(strs, "\n")));
        return this;
    }

    public KAlert setTitle(String s) {
        doAwait(() -> storageLatch.getValue().setTitle(s));
        return this;
    }

    public KAlert setOnCloseRequest(EventHandler<DialogEvent> val) {
        doAwait(() -> storageLatch.getValue().setOnCloseRequest(val));
        return this;
    }

    public KAlert setButton(ButtonType... types) {
        doAwait(() -> {
            Alert alert = storageLatch.getValue();
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(types);
        });
        return this;
    }

    public KAlert show() {
        Platform.runLater(() -> doAwait(() -> storageLatch.getValue().show()));
        return this;
    }

    public KAlert showAndWait(StorageCountDownLatch<Optional<ButtonType>> latch) {
        Platform.runLater(() -> doAwait(() -> {
            latch.setValue(this.storageLatch.getValue().showAndWait());
            latch.countDown();
        }));
        return this;
    }

    public Alert build() {
        try {
            storageLatch.await();
            return storageLatch.getValue();
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
        return null;
    }
}
