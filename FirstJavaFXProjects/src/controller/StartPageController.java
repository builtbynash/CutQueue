import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

public class StartPageController {

    @FXML
    private Button startButton;

    @FXML
    private void handleStartClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/second_page.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    
    @FXML
    private void handleAdminClick(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/loginadmin.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}