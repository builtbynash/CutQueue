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
            if (!name.matches("^[A-Za-z]+(?:[' ][A-Za-z]+)*,\\s[A-Za-z]+(?:[' ][A-Za-z]+)*\\s[A-Z]\\.$")) {
                name_error.setText("Format: Dela Cruz, Juan M.");
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

            // CONTACT (PH format: 09XXXXXXXXX)
            if (!contact.matches("^09\\d{9}$")) {
                contact_error.setText("Use: 09XXXXXXXXX");
                isValid = false;
            } else {
                contact_error.setText("");
            }

            // EMAIL
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                email_error.setText("Enter valid email");
                isValid = false;
            } else {
                email_error.setText("");
            }

            saveinfo.setDisable(!isValid);
        }

        @FXML
        private void gotowindow3(ActionEvent event) {
            try {

                DataStore.tempName = name_textfield.getText();
                DataStore.tempAddress = address_textfield.getText();
                DataStore.tempPhone = contact_textfield.getText();
                
                Parent root = FXMLLoader.load(getClass().getResource("/view/hairselections.fxml"));

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

        @FXML
        private void handleBackClick(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/start_page.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }