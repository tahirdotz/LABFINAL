public class Calculator {
    private double fuelAmount;
    private String fuelType;
    private BRTA brta;
    private boolean isPrivateCar;
    private String licenseNumber;
    private CarbonEmission carbonEmission;

    public Calculator(String fuelAmount, String fuelType, BRTA brta, boolean isPrivateCar, String licenseNumber) {
        this.fuelAmount = Double.parseDouble(fuelAmount);
        this.fuelType = fuelType;
        this.brta = brta;
        this.isPrivateCar = isPrivateCar;
        this.licenseNumber = licenseNumber;
        this.carbonEmission = new CarbonEmission(licenseNumber);
    }

    public double calculateTotalBill() {
        double fuelPrice = 0;

        if (fuelType.equals("Petrol")) {
            fuelPrice = brta.getPetrolPrice();
        } else if (fuelType.equals("Diesel")) {
            fuelPrice = brta.getDieselPrice();
        } else if (fuelType.equals("Octane")) {
            fuelPrice = brta.getOctanePrice();
        }

        double totalBill = fuelAmount * fuelPrice;
        double additionalCharges = 0;

        if (isPrivateCar && (carbonEmission.getTotalAmount() > 100)) {
            additionalCharges = totalBill * 0.10;
        }

        return totalBill + additionalCharges;
    }
}
