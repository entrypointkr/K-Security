package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.gui;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Junhyeong Lim on 2017-02-03.
 */
public class KGUI extends Application {
    public static void start() {
        LauncherImpl.launchApplication(KGUI.class, new String[0]);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
