package cloud.swiftnode.ksecurity.module.kgui.abstraction;

import com.github.plushaze.traynotification.animations.Animation;
import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notification;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
import javafx.application.Platform;
import javafx.util.Duration;

/**
 * Created by Junhyeong Lim on 2017-02-13.
 */
public class KTray extends Waitable<TrayNotification> {

    public KTray() {
        Runnable init = () -> {
            TrayNotification tray = new TrayNotification(Notifications.INFORMATION);
            tray.setTitle("K-Security");
            tray.setAnimation(Animations.SLIDE);
            latch.setValue(tray);
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

    public KTray setNotify(Notification notify) {
        doAwait(() -> latch.getValue().setNotification(notify));
        return this;
    }

    public KTray setTitle(String title) {
        doAwait(() -> latch.getValue().setTitle(title));
        return this;
    }

    public KTray setMessage(String msg) {
        doAwait(() -> latch.getValue().setMessage(msg));
        return this;
    }

    public KTray setAnimation(Animation animation) {
        doAwait(() -> latch.getValue().setAnimation(animation));
        return this;
    }

    public KTray showAndDismiss(int sec) {
        doAwait(() -> latch.getValue().showAndDismiss(Duration.seconds(sec)));
        return this;
    }
}
