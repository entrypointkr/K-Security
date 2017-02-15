package cloud.swiftnode.ksecurity.module.kgui;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.gui.LogItem;
import cloud.swiftnode.ksecurity.module.kgui.listener.LogListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.bukkit.Bukkit;

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
        conColumn.setText("내용");
        timeColumn.setText("시간");
        Platform.runLater(() -> {
            resizeColumn(view, conColumn);
            Stage stage = (Stage) view.getScene().getWindow();
            stage.widthProperty().addListener(((observable, oldValue, newValue) -> {
                resizeColumn(view, conColumn);
            }));
        });
        Bukkit.getPluginManager().registerEvents(new LogListener(view), KSecurity.inst);
    }

    @SuppressWarnings("unchecked")
    private void resizeColumn(TableView view, TableColumn column) {
        double totalWidth = view.getWidth();
        double usedWidth = 0;
        for (TableColumn element : (Iterable<TableColumn>) view.getColumns()) {
            if (element == column)
                continue;
            usedWidth += element.getWidth();
        }
        column.setPrefWidth(totalWidth - usedWidth);
    }
}
