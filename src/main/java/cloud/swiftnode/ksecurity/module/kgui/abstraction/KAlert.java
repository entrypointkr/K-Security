package cloud.swiftnode.ksecurity.module.kgui.abstraction;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.lang.StringUtils;

import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-02-04.
 */
public class KAlert {
    private Alert handle;

    public KAlert() {
        handle = new Alert(Alert.AlertType.INFORMATION);
        handle.setTitle("K-Security");
        handle.setHeaderText("K-Security");
    }

    public KAlert setType(Alert.AlertType type) {
        handle.setAlertType(type);
        return this;
    }

    public KAlert setHeaderText(String s) {
        handle.setHeaderText(s);
        return this;
    }

    public KAlert setContextText(String... strs) {
        handle.setContentText(StringUtils.join(strs, "\n"));
        return this;
    }

    public Optional<ButtonType> showAndWait() {
        return handle.showAndWait();
    }

    public KAlert setTitle(String s) {
        handle.setTitle(s);
        return this;
    }

    public void show() {
        handle.show();
    }
}
