import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HairSelectionController implements Initializable {

    // ── Service Cards ─────────────────────────────────────────────
    @FXML private VBox cardRegular, cardFade;
    @FXML private VBox cardUndercut, cardBalayage;
    @FXML private VBox cardFullColor, cardHighlights;
    @FXML private VBox cardClassicShave, cardBeardTrim;
    @FXML private VBox cardHotTowel, cardGroomingPkg;

    // ── Form fields ───────────────────────────────────────────────
    @FXML private Label lblSelectedStyle;
    @FXML private DatePicker appointmentDate;
    @FXML private ComboBox<String> appointmentTime;
    @FXML private ComboBox<String> barberChoice;

    // ── Overlay ────────────────────────────────────────────────────
    @FXML private StackPane overlayPane;
    @FXML private VBox panelPreview;
    @FXML private VBox panelConfirmed;

    // ── Preview labels ─────────────────────────────────────────────
    @FXML private Label previewStyle, previewDate, previewTime, previewBarber, previewPrice;

    // ── Confirmed labels ───────────────────────────────────────────
    @FXML private Label confirmedStyle, confirmedDate, confirmedTime, confirmedBarber, confirmedPrice;

    //  MULTI SELECT STORAGE
    private List<String> selectedServices = new ArrayList<>();
    private int totalPrice = 0;

    private boolean isEditing = false;

    // ── BOOKING SYSTEM ─────────────────────────────────────────────
    static class Booking {
        String date;
        String time;
        String barber;
        String service;
        String price;

        Booking(String date, String time, String barber, String service, String price) {
            this.date = date;
            this.time = time;
            this.barber = barber;
            this.service = service;
            this.price = price;
        }
    }

    private static List<Booking> bookings = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (appointmentTime != null) {
            appointmentTime.getItems().addAll(
                    "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM",
                    "12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM",
                    "4:00 PM", "5:00 PM"
            );
        }

        if (barberChoice != null) {
            barberChoice.getItems().addAll(
                    "Barber A - Juan",
                    "Barber B - Pedro",
                    "Barber C - Marco"
            );
        }
    }

    // ── SELECT MULTIPLE SERVICES WITH LIMIT ────────────────────────
    @FXML
    private void selectStyle(ActionEvent event) {

        Button btn = (Button) event.getSource();

        String[] data = btn.getUserData().toString().split("\\|");
        String style = data[0];
        int price = Integer.parseInt(data[1]);

        VBox card = (VBox) btn.getParent();

        if (selectedServices.contains(style)) {
            // REMOVE
            selectedServices.remove(style);
            totalPrice -= price;
            card.setStyle(normalStyle());
        } else {

            // LIMIT CHECK (MAX 3)
            if (selectedServices.size() == 3) {
                showAlert("You can only select up to 3 services.");
                return;
            }

            // ADD
            selectedServices.add(style);
            totalPrice += price;
            card.setStyle(activeStyle());
        }

        updateSelectedLabel();
    }

    private void updateSelectedLabel() {
        if (selectedServices.isEmpty()) {
            lblSelectedStyle.setText("None");
        } else {
            lblSelectedStyle.setText(String.join(", ", selectedServices) + " (₱" + totalPrice + ")");
        }
    }

    private String normalStyle() {
        return "-fx-background-color: #e0e0e0; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15;";
    }

    private String activeStyle() {
        return "-fx-background-color: #d0f0d0; -fx-border-color: #4caf50; -fx-border-width: 2; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15;";
    }

    private List<VBox> allCards() {
        return List.of(
                cardRegular, cardFade,
                cardUndercut, cardBalayage,
                cardFullColor, cardHighlights,
                cardClassicShave, cardBeardTrim,
                cardHotTowel, cardGroomingPkg
        );
    }

    private boolean isSlotTaken(String date, String time, String barber) {
        for (Booking b : bookings) {
            if (b.date.equals(date)
                    && b.time.equals(time)
                    && b.barber.equals(barber)) {
                return true;
            }
        }
        return false;
    }

    // OPEN PREVIEW
    @FXML
    private void openPreviewPopup() {

        if (!validateForm()) return;

        String dateStr = appointmentDate.getValue()
                .format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

        previewStyle.setText(String.join(", ", selectedServices));
        previewPrice.setText("₱" + totalPrice);

        previewDate.setText(dateStr);
        previewTime.setText(appointmentTime.getValue());
        previewBarber.setText(barberChoice.getValue());

        showPanel(panelPreview, panelConfirmed);
        overlayPane.setVisible(true);
    }

    @FXML
    private void editAppointment() {

        overlayPane.setVisible(false);
        lockForm(false);

        isEditing = true;

        showPanel(panelPreview, panelConfirmed);
    }

    @FXML
    private void cancelAppointment() {

        overlayPane.setVisible(false);
        resetForm();
    }

    // FINAL CONFIRM 
    @FXML
    private void finalConfirm(ActionEvent event) {

        confirmedStyle.setText(previewStyle.getText());
        confirmedPrice.setText(previewPrice.getText());

        confirmedDate.setText(previewDate.getText());
        confirmedTime.setText(previewTime.getText());
        confirmedBarber.setText(previewBarber.getText());

        String date = appointmentDate.getValue()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        DataStore.bookings.add(
            new DataStore.Booking(
                DataStore.tempName,
                String.join(", ", selectedServices),
                DataStore.tempAddress,
                date,
                appointmentTime.getValue(),
                DataStore.tempPhone,
                String.valueOf(totalPrice)
            )
        );

        isEditing = false;

        showPanel(panelConfirmed, panelPreview);
        lockForm(true);
    }

    //BACK BUTTON

    @FXML
    private void handleBackClick(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("view/second_page.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    //DONE BUTTON 
    @FXML
    private void doneAndGoHome(ActionEvent event) {

        try {
            resetForm();
            overlayPane.setVisible(false);

            Parent root = FXMLLoader.load(getClass().getResource("view/start_page.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void closePopup() {
        overlayPane.setVisible(false);
    }

    // HELPERS 
    private void showPanel(VBox show, VBox hide) {
        show.setVisible(true);
        show.setManaged(true);
        hide.setVisible(false);
        hide.setManaged(false);
    }

    private void lockForm(boolean lock) {

        appointmentDate.setDisable(lock);
        appointmentTime.setDisable(lock);
        barberChoice.setDisable(lock);

        for (VBox c : allCards()) {
            if (c != null) c.setDisable(lock);
        }
    }

    private boolean validateForm() {

        if (selectedServices.isEmpty()) {
            showAlert("Please select at least one service.");
            return false;
        }

        if (appointmentDate.getValue() == null) {
            showAlert("Please select a date.");
            return false;
        }

        if (appointmentTime.getValue() == null) {
            showAlert("Please select a time.");
            return false;
        }

        if (barberChoice.getValue() == null) {
            showAlert("Please select a barber.");
            return false;
        }

        String date = appointmentDate.getValue()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (!isEditing && isSlotTaken(date, appointmentTime.getValue(), barberChoice.getValue())) {
            showAlert("This slot is already booked.");
            return false;
        }

        return true;
    }

    private void resetForm() {

        selectedServices.clear();
        totalPrice = 0;

        lblSelectedStyle.setText("None");

        appointmentDate.setValue(null);
        appointmentTime.setValue(null);
        barberChoice.setValue(null);

        isEditing = false;

        for (VBox c : allCards()) {
            if (c != null) c.setStyle(normalStyle());
        }

        lockForm(false);
    }

    private void showAlert(String msg) {

        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("CUTQUEUE");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}