import javax.swing.*;
import java.io.*;

public class PublicTransport extends JFrame {
    private String licenseNumber;
    private String fuelAmount;
    private String carType;
    private String fuelType;
    private CarbonEmission carbonEmission;


    public PublicTransport(String licenseNumber, String fuelAmount, String carType, String fuelType, BRTA brta,CarbonEmission carbonEmission) {
        this.licenseNumber = licenseNumber;
        this.fuelAmount = fuelAmount;
        this.carType = carType;
        this.fuelType = fuelType;
        Calculator calculator = new Calculator(fuelAmount, fuelType, brta,false,licenseNumber);
        double totalBill = calculator.calculateTotalBill();
        String totalBillinString = String.valueOf(totalBill);

        if (checkLicense(licenseNumber)) {
            String contactNumber = getContactNumber(licenseNumber);
            if (contactNumber != null) {
                CarSaveDetails publiccarSaveDetails = new CarSaveDetails(licenseNumber, fuelAmount, totalBillinString, contactNumber);
                JOptionPane.showMessageDialog(null, "License Found. Message sent to " + contactNumber);

                try (PrintWriter pw = new PrintWriter(new FileWriter("PublicTransportDataBase.txt", true))) {
                    pw.println(publiccarSaveDetails);
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
            BufferedReader bufferedReader = new BufferedReader(new FileReader("PublicTransportDataBaseBRTA.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(licenseNumber)) {
                    found = true;
                    break;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return found;
    }

    public String getContactNumber(String licenseNumber) {
        String contactNumber = null;
        try (BufferedReader br = new BufferedReader(new FileReader("PublicTransportDataBaseBRTA.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("-");
                if (data.length == 3 && data[1].trim().equals(licenseNumber.trim())) {
                    contactNumber = data[2].trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contactNumber;
    }


}
