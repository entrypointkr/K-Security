package cloud.swiftnode.ksecurity.module.kgui;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.gui.KAlert;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.gui.LogItem;
import cloud.swiftnode.ksecurity.module.kgui.listener.LogListener;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.bukkit.Bukkit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Junhyeong Lim on 2017-02-13.
 */
public class Controller implements Initializable {
    @FXML
    private TableView<LogItem> view;
    @FXML
    private TableColumn conColumn;
    @FXML
    private TableColumn timeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resizeColumn(view, conColumn);
        Stage stage = KFX.getStage();
        stage.widthProperty().addListener(((observable, oldValue, newValue) -> {
            resizeColumn(view, conColumn);
        }));
        view.getItems().addListener((ListChangeListener<LogItem>) c -> {
            if (view.getItems().size() > 500) {
                view.getItems().remove(view.getItems().get(0));
            }
        });
        Bukkit.getPluginManager().registerEvents(new LogListener(view), KSecurity.inst);
    }

    @SuppressWarnings("unchecked")
    private void resizeColumn(TableView view, TableColumn column) {
        Platform.runLater(() -> {
            double totalWidth = view.getWidth();
            double usedWidth = 0;
            for (TableColumn element : (Iterable<TableColumn>) view.getColumns()) {
                if (element == column)
                    continue;
                usedWidth += element.getWidth();
            }
            column.setPrefWidth(totalWidth - usedWidth);
        });
    }

    @FXML
    public void onClear() {
        view.getItems().clear();
    }

    @FXML
    public void onSave() {
        File file = Static.getLogFile();
        if (!file.getParentFile().isDirectory()) {
            file.getParentFile().mkdirs();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (LogItem item : view.getItems()) {
                writer.write(item.getTime() + ": " + item.getLog());
                writer.newLine();
            }
            writer.flush();
            new KAlert().setContextText(
                    Lang.SAVED_LOG.builder()
                            .single(Lang.Key.VALUE, file.getAbsolutePath()).build())
                    .show();
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
    }
}
