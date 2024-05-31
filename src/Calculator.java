public class Calculator {
    private double fuelAmount;
    private String fuelType;
    private BRTA brta;
    public double totalBill;

    public Calculator(String fuelAmount, String fuelType, BRTA brta)
    {
        this.fuelAmount = Double.parseDouble(fuelAmount);
        this.fuelType = fuelType;
        this.brta = brta;
    }

    public double calculateTotalBill()
    {


        if (fuelType.equals("Petrol"))
        {
            return fuelAmount * brta.getPetrolPrice();
        }
        else if (fuelType.equals("Diesel"))
        {
            return fuelAmount * brta.getDieselPrice();
        }
        else if (fuelType.equals("Octane"))
        {
            return fuelAmount * brta.getOctanePrice();
        }

        System.out.println("Fuel Amount: " + fuelAmount);
        System.out.println("Fuel Type: " + fuelType);
        System.out.println("Price per liter: " + brta.getPetrolPrice());


        return 10;
    }
}
