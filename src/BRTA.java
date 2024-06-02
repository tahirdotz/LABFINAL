import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BRTA {
    private String name;
    private String licenseNumber;
    private String phoneNumber;
    private double amountOfFuel;
    private double amountOfCashPaid;
    private static double octanePrice;
    private static double dieselPrice;
    private static double petrolPrice;
    private CarbonEmission carbonEmission;

    public String getName(){
        return name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public static double getOctanePrice(){
        return octanePrice;
    }

    public static double getDieselPrice(){
        return dieselPrice;
    }

    public static double getPetrolPrice(){
        return petrolPrice;
    }

    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber=licenseNumber;
    }

    public void setAmountOfFuel(double amountOfFuel){
        this.amountOfFuel=amountOfFuel;
    }

    public void setAmountOfCashPaid(double amountOfCashPaid){
        this.amountOfCashPaid=amountOfCashPaid;
    }

    public void setCarbonEmission(CarbonEmission carbonEmission) {
        this.carbonEmission = carbonEmission;
    }

    public BRTA(double octanePrice, double dieselPrice, double petrolPrice){
        BRTA.octanePrice = octanePrice;
        BRTA.dieselPrice = dieselPrice;
        BRTA.petrolPrice = petrolPrice;
    }

    public void readDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("BRTAPrivateData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String checker = licenseNumber;
                String[] parts = line.split(",");
                String name = parts[0];
                String licenseNumber = parts[1];
                String phoneNumber = parts[2];

                if(licenseNumber.equals(checker)){
                    this.name = name;
                    this.phoneNumber = phoneNumber;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayData (){
            System.out.println("License Number transferred to the BRTA : " + licenseNumber);
        }

}