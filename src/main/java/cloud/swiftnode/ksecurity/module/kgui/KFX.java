package cloud.swiftnode.ksecurity.module.kgui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Junhyeong Lim on 2017-02-04.
 */
public class KFX extends Application {
    private static Stage stage;

    public static void start() {
        launch(KFX.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int width = 500;
        int height = 350;

        stage = primaryStage;
        FXMLLoader.setDefaultClassLoader(getClass().getClassLoader());
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        primaryStage.setOnCloseRequest(event -> {
            primaryStage.setIconified(true);
            event.consume();
        });
        primaryStage.setTitle("K-Security 이벤트 로그");
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setMinWidth(width);
        primaryStage.setMinHeight(height);
        primaryStage.show();
        Platform.setImplicitExit(false);
    }

    public static Stage getStage() {
        return stage;
    }
}
