import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.util.ArrayList;

public class Controller {
    private String nameOfOwner;
    private String phoneNumber;
    private String licenseNumber;
    private double amountOfFuel;
    private double amountOfCashPaid;
    private double amountOwed;
    private String transactionID;
    private final String currentTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    private int i = 0;
    private final CarbonEmission carbonEmission;
    private final BRTA brta;

    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber=licenseNumber;
        brta.setLicenseNumber(licenseNumber);
    }

    public void setNameOfOwner(String nameOfOwner){
        this.nameOfOwner=nameOfOwner;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }

    public void setAmountOfFuel(double amountOfFuel){
        this.amountOfFuel=amountOfFuel;
    }

    public void setAmountOfCashPaid(double amountOfCashPaid){
        this.amountOfCashPaid=amountOfCashPaid;
    }

    public double getAmountOwed(){
        return amountOwed;
    }
    public void setAmountOwed(double amountOwed){
        this.amountOwed=amountOwed;
    }

    public Controller(double octanePrice, double dieselPrice, double petrolPrice){
        carbonEmission = new CarbonEmission();
        brta = new BRTA(octanePrice, dieselPrice, petrolPrice);
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

    public void readDataFromFile(int index) throws InvalidLicenseNumberException{
        brta.readDataFromFile(index);
    }
    public void addTaxToPayment(double amountOfFuel) throws InvalidAmountPaidException {

        carbonEmission.calculateTotalFuelAmount(licenseNumber);
        double check = carbonEmission.getTotalAmount();

        if(check+amountOfFuel>50){
            amountOwed*=1.1;
            i++;
            System.out.println("Total fuel refilled this month is "+(check+amountOfFuel));
        }
    }

    /******Randomized Hex String generated using a Linear Congruence Generator algorithm*******/
    public void generateTransactionID(String reference, int n){

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

        transactionID = Integer.toHexString((a*seed) + (b%large));
    }

    public void printDigitalReceipt(){
            if(i!=0){
                double initialAmountOwed = amountOwed/1.1;

                JOptionPane.showMessageDialog(null,"Message sent to : "+brta.getPhoneNumber()+"\n" +
                        "Amount of fuel refilled : "+amountOfFuel+"\n" +
                        "Amount of cash paid : "+amountOfCashPaid+"\n"+"Amount Owed : "+amountOwed+"\n"+"Extra Charge (Additional 10%): "+(amountOwed-initialAmountOwed)+"\n"+
                        "Transaction ID : "+transactionID+"\n"+"Time of activity : "+currentTime);
                i=0;
            }
            else {
                JOptionPane.showMessageDialog(null, "Message sent to : " + brta.getPhoneNumber() + "\n" +
                        "Amount of fuel refilled : " + amountOfFuel + "\n" +
                        "Amount of cash paid : " + amountOfCashPaid + "\n" + "Amount Owed : " + amountOwed + "\n" +//later
                        "Transaction ID : " + transactionID + "\n" + "Time of activity : " + currentTime);
            }
    }


    public void writeDataToFile(int index) throws IOException {

        File file = switch (index) {
            case 0 -> new File("PrivateTransportDatabase.txt");
            case 1 -> new File("PublicTransportDatabase.txt");
            default -> throw new IllegalArgumentException("Invalid index: " + index);
        };

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(nameOfOwner + "," + phoneNumber + "," + licenseNumber + "," + amountOfFuel + "," + amountOfCashPaid + "," +transactionID);
            bw.newLine();
        }
    }



}