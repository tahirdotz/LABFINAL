import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GovernmentCar {
    private String licenseNumber;
    private String fuelAmount;
    private String fuelType;
    private BRTA brta;

    public GovernmentCar(String licenseNumber, String fuelAmount, String fuelType, BRTA brta) {
        this.licenseNumber = licenseNumber;
        this.fuelAmount = fuelAmount;
        this.fuelType = fuelType;
        this.brta = brta;
    }

    public void recordRefuel() {
        Calculator calculator = new Calculator(fuelAmount, fuelType, brta);
        double totalBill = calculator.calculateTotalBill();
        saveRefuelDetails(totalBill);
    }

    private void saveRefuelDetails(double totalBill) {
        String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        String record = String.format("%s - %s - %s - %.2f - %s%n", licenseNumber, fuelAmount, fuelType, totalBill, currentTime);

        try (PrintWriter writer = new PrintWriter(new FileWriter("GovernmentCarRefuelRecords.txt", true))) {
            writer.print(record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
