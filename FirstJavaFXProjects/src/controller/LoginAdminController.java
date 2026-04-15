import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginAdminController {

    
    @FXML
    private TextField emailfield;       // USERNAME
    @FXML
    private PasswordField emailfield1;  // PASSWORD
    @FXML
    private Button loginButton;         // LOG-BTNSKIE
    @FXML
    private Button backButton;          // BCK-BTNSKIE

    // ADMIN CREDENTIALSKIE haha
    private final String ADMIN_USERNAME = "cutqueue_admin";
    private final String ADMIN_PASSWORD = "CutQueue@2026";

    // BCK-BTN -> go back to start_page.fxml
    @FXML
    private void handleBackClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/start_page.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // LOGINBTN -> CHECK-CREDENTIALSKIE
    @FXML
    private void handleLoginClick(ActionEvent event) throws IOException {
        String enteredUsername = emailfield.getText();
        String enteredPassword = emailfield1.getText();

        if (ADMIN_USERNAME.equals(enteredUsername) && ADMIN_PASSWORD.equals(enteredPassword)) {
            // CORRECTSKIE -> GO TO dashboard.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            // WRONG-CREDENTIALSKIE -> show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Invalid Username or Password");
            alert.setContentText("Please check your credentials and try again.");
            alert.showAndWait();

            // CLEAR-INPUTFIELDS
            emailfield.clear();
            emailfield1.clear();

            
            emailfield.setEditable(true);
            emailfield1.setEditable(true);
            emailfield.requestFocus();

            // DISABLE LOGIN TEMPO hehehe
            loginButton.setDisable(true);
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loginButton.setDisable(false);
            }).start();
        }
    }
}