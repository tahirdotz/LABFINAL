import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class Controller {
    private String licenseNumber;
    private double amountOfFuel;
    private double amountOfCashPaid;
    private String nameOfOwner;
    private String phoneNumber;
    private final BRTA brta;

    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber=licenseNumber;
        sendLicenseNumberToBRTA();
    }

    public void setNameOfOwner(String nameOfOwner){
        this.nameOfOwner=nameOfOwner;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }

    public void setAmountOfFuel(double amountOfFuel){
        this.amountOfFuel=amountOfFuel;
        brta.setAmountOfFuel(amountOfFuel);
    }

    public void setAmountOfCashPaid(double amountOfCashPaid){
        this.amountOfCashPaid=amountOfCashPaid;
        brta.setAmountOfCashPaid(amountOfCashPaid);
    }

    public Controller(){
        brta = new BRTA(126, 108.25, 122);
    }

    public void sendLicenseNumberToBRTA(){
        brta.setLicenseNumber(licenseNumber);
    }

    public BRTA getBrta(){
        return brta;
    }

    public void displayLicenseNumber(){
        System.out.println("License Number Transferred to the Controller : " + licenseNumber);
        System.out.println(amountOfFuel);
        System.out.println(amountOfCashPaid);
        brta.displayData();

    }

    public void writeDataToFile(int index) throws IOException {

        File file = switch (index) {
            case 0 -> new File("PrivateTransportDatabase.txt");
            case 1 -> new File("PublicTransportDatabase.txt");
            default -> throw new IllegalArgumentException("Invalid index: " + index);
        };

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(nameOfOwner + "//" + phoneNumber + "//" + licenseNumber + "//" + amountOfFuel + "//" + amountOfCashPaid);
            bw.newLine();
        }
    }
    

}