import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PrivateCar extends JFrame {
    private String licenseNumber;
    private String fuelAmount;
    private String carType;
    private String fuelType;

    public PrivateCar(String licenseNumber, String fuelAmount, String carType, String fuelType, BRTA brta) {
        this.licenseNumber = licenseNumber;
        this.fuelAmount = fuelAmount;
        this.carType = carType;
        this.fuelType = fuelType;
        Calculator calculator = new Calculator(fuelAmount, fuelType, brta);
        double totalBill = calculator.calculateTotalBill();
        String totalBillinString = String.valueOf(totalBill);

        if (checkLicense(licenseNumber)) {
            String contactNumber = getContactNumber(licenseNumber);
            if (contactNumber != null) {
                PrivateCarSaveDetails privateCarSaveDetails = new PrivateCarSaveDetails(licenseNumber, fuelAmount, totalBillinString, contactNumber);
                JOptionPane.showMessageDialog(null, "License Found. Message sent to " + contactNumber);

                try (PrintWriter pw = new PrintWriter(new FileWriter("PrivateCarDataBase.txt", true))) {
                    pw.println(privateCarSaveDetails.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Contact number not found for the given license number.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Licence Number");
        }
    }

    public boolean checkLicense(String licenseNumber) {
        boolean found = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("PrivateCarDatabaseBRTA.txt"));
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
        try {
            BufferedReader reader = new BufferedReader(new FileReader("PrivateCarDatabaseBRTA.txt"));
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
