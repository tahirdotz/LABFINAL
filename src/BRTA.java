public class BRTA {
    private double octanePrice;
    private double dieselPrice;
    private double petrolPrice;

    public BRTA(double octanePrice, double dieselPrice, double petrolPrice)
    {
        this.octanePrice = octanePrice;
        this.dieselPrice = dieselPrice;
        this.petrolPrice = petrolPrice;
    }

    public BRTA() {

    }

    public double getOctanePrice()
    {
        return octanePrice;
    }

    public void setOctanePrice(double octanePrice)
    {
        this.octanePrice = octanePrice;
    }

    public double getDieselPrice()
    {
        return dieselPrice;
    }

    public void setDieselPrice(double dieselPrice)
    {
        this.dieselPrice = dieselPrice;
    }

    public double getPetrolPrice()
    {
        return petrolPrice;
    }

    public void setPetrolPrice(double petrolPrice)
    {
        this.petrolPrice = petrolPrice;
    }

}
