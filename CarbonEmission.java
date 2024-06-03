import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CarbonEmission {
    private int totalAmount;
    private double totalMoney;

    public CarbonEmission(String licenseNumber) {
        totalAmount = 0;
        totalMoney = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("PrivateCarDataBase.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String currentLicenseNumber = parts[0].trim().split(" ")[1];
                    if (currentLicenseNumber.equals(licenseNumber)) {
                        String fuelAmountStr = parts[2].trim();
                        if (fuelAmountStr.startsWith("FuelAmount:")) {
                            String fuelAmountValue = fuelAmountStr.substring("FuelAmount:".length()).trim();
                            try {
                                int fuel = Integer.parseInt(fuelAmountValue);
                                totalAmount += fuel;
                                totalMoney += Double.parseDouble(parts[3].trim().split(" ")[1]);
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid fuel amount: " + fuelAmountValue);
                            }
                        } else {
                            System.err.println("Invalid line format: " + line);
                        }
                    }
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public double getTotalMoney() {
        return totalMoney;
    }
}

