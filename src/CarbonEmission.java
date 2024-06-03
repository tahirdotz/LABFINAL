import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CarbonEmission {
    private double totalAmount = 0;

    public void calculateTotalFuelAmount(String licenseNumber) {

        try (BufferedReader br = new BufferedReader(new FileReader("PrivateTransportDatabase.txt"))) {
            String line;

            while((line = br.readLine())!=null){

                String[] parts = line.split(",");

                if(parts[2].equals(licenseNumber)) {
                    double fuelAmount = Double.parseDouble(parts[3]);
                    totalAmount += fuelAmount;
                }

            }
        }
        catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}

