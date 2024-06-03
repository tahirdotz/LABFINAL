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
    private static double octanePrice;
    private static double dieselPrice;
    private static double petrolPrice;

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


    public BRTA(double octanePrice, double dieselPrice, double petrolPrice){
        BRTA.octanePrice = octanePrice;
        BRTA.dieselPrice = dieselPrice;
        BRTA.petrolPrice = petrolPrice;
    }

    public void readDataFromFile(int index) throws InvalidLicenseNumberException{

        BufferedReader br = null;
        int i = 1;

        try {
            String line;

            if(index==0) {
                br = new BufferedReader(new FileReader("BRTAPrivateData.txt"));
            } else {
                br = new BufferedReader(new FileReader("BRTAPublicData.txt"));
            }

            while ((line = br.readLine()) != null) {
                String checker = licenseNumber;
                String[] parts = line.split(",");
                String name = parts[0];
                String licenseNumber = parts[1];
                String phoneNumber = parts[2];

                if(licenseNumber.equals(checker)){
                    i=0;
                    this.name = name;
                    this.phoneNumber = phoneNumber;
                }
            }

            if(i==1){
                JOptionPane.showMessageDialog(null,"Invalid License Number");
                throw new InvalidLicenseNumberException("No such registered vehicle is found");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayData (){
            System.out.println("License Number transferred to the BRTA : " + licenseNumber);
        }

}