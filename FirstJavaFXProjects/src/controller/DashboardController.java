import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private TableView<DataStore.Booking> table;

    @FXML private TableColumn<DataStore.Booking, String> colName;
    @FXML private TableColumn<DataStore.Booking, String> colServices;
    @FXML private TableColumn<DataStore.Booking, String> colAddress;
    @FXML private TableColumn<DataStore.Booking, String> colDateTime;
    @FXML private TableColumn<DataStore.Booking, String> colPhone;
    @FXML private TableColumn<DataStore.Booking, String> colPrice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        table.setItems(DataStore.bookings);

        colName.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().name));

        colServices.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().service));

        colAddress.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().address));

        colDateTime.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().date + " " + data.getValue().time));

        colPhone.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().phone));

        colPrice.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().price));
    }

    // BACK TO HOMEPAGE-BTNSKIE
    @FXML
    private void handleBackHome(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass().getResource("view/start_page.fxml")
        );

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene().getWindow();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}