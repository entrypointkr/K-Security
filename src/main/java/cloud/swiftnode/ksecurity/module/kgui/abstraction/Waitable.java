package cloud.swiftnode.ksecurity.module.kgui.abstraction;

import cloud.swiftnode.ksecurity.abstraction.StorageCountDownLatch;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.gui.KAlert;
import cloud.swiftnode.ksecurity.util.Static;
import javafx.application.Platform;

/**
 * Created by Junhyeong Lim on 2017-02-13.
 */
public abstract class Waitable<T> {
    protected StorageCountDownLatch<T> storageLatch = new StorageCountDownLatch<>(1);

    protected void doAwait(KAlert.ExRunnable runnable) {
        try {
            if (!Platform.isFxApplicationThread()) {
                storageLatch.await();
                Platform.runLater(runnable);
            } else {
                runnable.run();
            }
        } catch (Exception e) {
            Static.consoleMsg(e);
        }
    }

    protected interface ExRunnable extends Runnable {
        void runEx() throws Exception;

        default void run() {
            try {
                runEx();
            } catch (Exception ex) {
                Static.consoleMsg(ex);
            }
        }
    }
}
