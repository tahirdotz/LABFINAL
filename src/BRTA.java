import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BRTA {
    private String name;
    private String licenseNumber;
    private String phoneNumber;
    private double amountOfFuel;
    private double amountOfCashPaid;

    private final String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

    public String getName(){
        return name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
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

    public void readDataFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("UserData.txt"))) {
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

                    JOptionPane.showMessageDialog(null,"Message sent to : "+phoneNumber+"\n" +
                            "Amount of fuel refilled : "+amountOfFuel+"\n" +
                            "Amount of cash paid : "+amountOfCashPaid+"\n" +
                            "Transaction ID : "+generateTransactionID(licenseNumber, 4)+"\n"+"Time of activity : "+currentTime);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateTransactionID(String reference, int n){

        String modified = reference.substring(reference.length()-n);
        int large = Integer.parseInt(modified);

        int a = (int) (Math.random()*large);
        int b;
        int seed;

        do {
            b = (int) (Math.random() * large);
            seed = (int) (Math.random() * large);
        }
        while (b == a && seed == a);

        return Integer.toHexString((a*seed) + (b%large));
    }

        public void displayData () {
            System.out.println("License Number transferred to the BRTA : " + licenseNumber);
        }

}