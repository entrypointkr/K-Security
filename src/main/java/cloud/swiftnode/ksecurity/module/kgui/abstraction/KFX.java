package cloud.swiftnode.ksecurity.module.kgui.abstraction;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Created by Junhyeong Lim on 2017-02-04.
 */
public class KFX extends Application {

    public static void start() {
        launch(KFX.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.setImplicitExit(false);
    }
}
