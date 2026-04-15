import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataStore {

    public static ObservableList<Booking> bookings =
            FXCollections.observableArrayList();

    // TEMP STORAGE (from second page)
    public static String tempName = "";
    public static String tempAddress = "";
    public static String tempPhone = "";

    public static class Booking {

        public String name;
        public String service;
        public String address;
        public String date;
        public String time;
        public String phone;
        public String price;

        public Booking(String name, String service, String address,
                       String date, String time, String phone, String price) {
            this.name = name;
            this.service = service;
            this.address = address;
            this.date = date;
            this.time = time;
            this.phone = phone;
            this.price = price;
        }
    }
}