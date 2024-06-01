import java.awt.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.image.*;

/*External-------------------------------------

*///----------------------------------------------

public class GRMS extends JFrame{

    private JTextField licenseInput;
    private JTextField amountOfFuelInput;
    private JTextField amountOfCashPaidInput;

    private JComboBox<String> fuelChoice;

    private JComboBox<String> vehicleType;
    private JButton submitButton;

    private String licenseNumber;
    private double amountOfFuel;
    private double amountOfCashPaid;
    private final Controller controller;

    public GRMS(Controller controller){
        this.controller=controller;
        setTitle("Gas Refuel Management System");
        setSize(400,250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initialComponents();
        addComponentsToFrame();
    }

    private void initialComponents(){
        licenseInput = new JTextField(10);
        amountOfFuelInput = new JTextField(10);
        amountOfCashPaidInput = new JTextField(10);

        String[] options1 = {"Octane", "Petrol", "Diesel"};
        fuelChoice = new JComboBox<>(options1);
        fuelChoice.setSelectedIndex(0);

        String[] options2 = {"Private", "Government"};
        vehicleType = new JComboBox<>(options2);
        vehicleType.setSelectedIndex(0);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    licenseNumber=licenseInput.getText();
                    amountOfFuel=Double.parseDouble(amountOfFuelInput.getText());
                    amountOfCashPaid=Double.parseDouble(amountOfCashPaidInput.getText());

                    controller.setLicenseNumber(licenseNumber);
                    controller.setAmountOfFuel(amountOfFuel);
                    controller.setAmountOfCashPaid(amountOfCashPaid);

                    calculateFuelPrice();

                    controller.brta.readDataFromFile();
                    controller.setNameOfOwner(controller.brta.getName());
                    controller.setPhoneNumber(controller.brta.getPhoneNumber());
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(GRMS.this, "You entered an invalid input \n"+ex.getMessage());
                    ex.printStackTrace();
                }
                catch (SuspectedFoulPlayException ex){
                    JOptionPane.showMessageDialog(GRMS.this, "Message sent to : "+controller.brta.getPhoneNumber()+"\nSuspected foul play involved\nAmount of fuel refilled : "+amountOfFuel+"\nAmount of cash paid : "+amountOfCashPaid);
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
                //JOptionPane.showMessageDialog(GRMS.this, "You entered the license number " + licenseNumber);
            }
        });
    }

    private void addComponentsToFrame(){
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(10,10,10,10));
        inputPanel.setLayout(new GridLayout(5, 2, 20, 5));
        inputPanel.add(new JLabel("License Number"));
        inputPanel.add(licenseInput);
        inputPanel.add(new JLabel("Amount of Fuel"));
        inputPanel.add(amountOfFuelInput);
        inputPanel.add(new JLabel("Amount of Cash Paid"));
        inputPanel.add(amountOfCashPaidInput);
        inputPanel.add(new JLabel("Type of Fuel"));
        inputPanel.add(fuelChoice);
        inputPanel.add(new JLabel("Vehicle Type"));
        inputPanel.add(vehicleType);
        inputPanel.add(submitButton);

        container.add(inputPanel, BorderLayout.CENTER);
        container.add(submitButton, BorderLayout.AFTER_LAST_LINE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controller controller = new Controller();
                GRMS grms = new GRMS(controller);
                grms.setVisible(true);
            }
        });
    }

    public void calculateFuelPrice() throws SuspectedFoulPlayException{
        int index = fuelChoice.getSelectedIndex();
        if(index==0){
            Fuel octane = new Octane();
            boolean valid = octane.validityOfPrice(amountOfFuel, amountOfCashPaid);
            if(!valid){
                throw new SuspectedFoulPlayException("Fuel amount and cash paid do not match up");
            }
        }
        else if(index==1){
            Fuel petrol = new Petrol();
            boolean valid = petrol.validityOfPrice(amountOfFuel, amountOfCashPaid);
            if(!valid){
                throw new SuspectedFoulPlayException("Fuel amount and cash paid do not match up");
            }
        }
        else{
            Fuel diesel = new Diesel();
            boolean valid = diesel.validityOfPrice(amountOfFuel, amountOfCashPaid);
            if(!valid){
                throw new SuspectedFoulPlayException("Fuel amount and cash paid do not match up");
            }
        }
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
    public double getAmountOfFuel(){
        return amountOfFuel;
    }
    public double getAmountOfCashPaid(){
        return amountOfCashPaid;
    }
}