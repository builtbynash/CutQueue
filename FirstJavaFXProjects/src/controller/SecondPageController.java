

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Label;

public class SecondPageController {

    @FXML
    private Button saveinfo;

    @FXML
    private TextField name_textfield, address_textfield, contact_textfield, emailadd_textflied;

    @FXML
    private Label name_error, address_error, contact_error, email_error;

    @FXML
    public void initialize() {
        saveinfo.setDisable(true);

        name_textfield.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        address_textfield.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        contact_textfield.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        emailadd_textflied.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
    }

    private void validateForm() {
        String name = name_textfield.getText().trim();
        String address = address_textfield.getText().trim();
        String contact = contact_textfield.getText().trim();
        String email = emailadd_textflied.getText().trim();

        boolean isValid = true;

        // NAME
        if (!name.matches("^[A-Za-z' ]+,\\s[A-Za-z'. ]+$")) {
            name_error.setText("Format: Last, First M.");
            isValid = false;
        } else {
            name_error.setText("");
        }

        // ADDRESS
        if (address.length() < 10) {
            address_error.setText("Enter full address");
            isValid = false;
        } else {
            address_error.setText("");
        }

        // CONTACT
        if (!contact.matches("\\d{10,11}")) {
            contact_error.setText("Enter valid number");
            isValid = false;
        } else {
            contact_error.setText("");
        }

        // EMAIL 
        if (!email.endsWith("@gmail.com")) {
            email_error.setText("Must be a Gmail (@gmail.com)");
            isValid = false;
        } else {
            email_error.setText("");
        }
        saveinfo.setDisable(!isValid);
    }

    @FXML
    private void gotowindow3(ActionEvent event) throws IOException {

        System.out.println("btnclickk");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/third_page.fxml"));
        Parent root = loader.load();

        ThirdPageController controller = loader.getController();

        controller.setUserData(
                name_textfield.getText(),
                address_textfield.getText(),
                contact_textfield.getText(),
                emailadd_textflied.getText()
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    @FXML
    private void handleBackClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/start_page.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}