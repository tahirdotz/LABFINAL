import java.text.SimpleDateFormat;
import java.util.Date;

public class CarSaveDetails {
    private String licenseNumber;
    private String dateAndTime;
    private String fuelAmount;
    private String totalMoney;

    public CarSaveDetails(String licenseNumber, String fuelAmount, String totalMoney, String contactNumber) {
        this.licenseNumber = licenseNumber;
        this.fuelAmount = fuelAmount;
        this.totalMoney = totalMoney;

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateAndTime = dateFormat.format(currentDate);
    }

    @Override
    public String toString() {
        return "LicenseNumber: " + licenseNumber + ", " +
                "DateAndTime: " + dateAndTime + ", " +
                "FuelAmount: " + fuelAmount + ", " +
                "TotalMoney: " + totalMoney;
    }
}
