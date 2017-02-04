package cloud.swiftnode.ksecurity.module.kgui.abstraction;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

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
        new KAlert().setContextText(
                "플러그인이 활성화되었습니다.",
                "서버는 K-Security 에 의해 보호됩니다.")
                .show();
    }
}
