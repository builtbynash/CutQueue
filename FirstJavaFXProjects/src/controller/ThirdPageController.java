import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ThirdPageController {

    public void setUserData(String name, String address, String contact, String email) {
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Contact: " + contact);
        System.out.println("Email: " + email);
    }

    // BACK BUTTON
    public void goback_2ndWindow(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/second_page.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}