package cloud.swiftnode.ksecurity.module.kgui.abstraction;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by Junhyeong Lim on 2017-02-04.
 */
public class KFX extends Application {

    public static void start() {
        launch(KFX.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        int width = 700;
//        int height = 450;
//
//        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
//        primaryStage.setOnCloseRequest(event -> hideStage(primaryStage));
//        primaryStage.setTitle("K-Security");
//        primaryStage.setScene(new Scene(root, width, height));
//        primaryStage.setMinWidth(width);
//        primaryStage.setMinHeight(height);
//        primaryStage.show();
        Platform.setImplicitExit(false);
    }

    private void hideStage(Stage stage) {
        if (SystemTray.isSupported()) {
            stage.hide();
        }
    }
}
