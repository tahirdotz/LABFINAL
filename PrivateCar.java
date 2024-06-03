import javax.swing.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class PrivateCar {

    private String licenseNumber;
    private String fuelAmount;
    private String carType;
    private String fuelType;
    private BRTA brta;

    public PrivateCar(String licenseNumber, String fuelAmount, String carType, String fuelType, BRTA brta) {
        this.licenseNumber = licenseNumber;
        this.fuelAmount = fuelAmount;
        this.carType = carType;
        this.fuelType = fuelType;
        this.brta = brta;
        Calculator calculator = new Calculator(fuelAmount, fuelType, brta, true, licenseNumber);
        double totalBill = calculator.calculateTotalBill();
        String totalBillinString = String.valueOf(totalBill);

        if (checkLicense(licenseNumber)) {
            String contactNumber = getContactNumber(licenseNumber);
            if (contactNumber != null) {

                CarSaveDetails privateCarSaveDetails = new CarSaveDetails(licenseNumber, fuelAmount, totalBillinString, contactNumber);
                try (PrintWriter pw = new PrintWriter(new FileWriter("PrivateCarDataBase.txt", true))) {
                    pw.println(privateCarSaveDetails);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                CarbonEmission carbonEmission = new CarbonEmission(licenseNumber);
                int totalFuelThisMonth = carbonEmission.getTotalAmount();


                double additionalCharges = 0;
                if (totalFuelThisMonth > 100) {
                    additionalCharges = totalBill * 0.1;
                }


                String message = "Total fuel taken in this month: " + totalFuelThisMonth + " liters\n";
                message += "Additional Charges: " + additionalCharges + "\n";


                JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Contact number not found for the given license number.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid License Number");
        }
    }

    public boolean checkLicense(String licenseNumber) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("PrivateCarDatabaseBRTA.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(licenseNumber)) {
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return found;
    }

    public String getContactNumber(String licenseNumber) {
        String contactNumber = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("PrivateCarDatabaseBRTA.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" - ");
                if (data.length == 3 && data[1].equals(licenseNumber)) {
                    contactNumber = data[2];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contactNumber;
    }
}
