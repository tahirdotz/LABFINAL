import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Graphics extends JFrame {
    private final JTextField licenseInput;
    private final JTextField amountOfFuelInput;
    private final JTextField amountOfCashPaidInput;
    private final JComboBox<String> gasTypeCombo;
    private final Controller controller;

    public Graphics(Controller controller) {

        this.controller = controller;

        setTitle("Gas Refill Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel licenseLabel = new JLabel("Enter License Number:");
        licenseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        licenseInput = new JTextField(15);

        JLabel fuelAmountLabel = new JLabel("Enter Fuel Amount (Litres):");
        fuelAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountOfFuelInput = new JTextField(15);

        JLabel cashAmountLabel = new JLabel("Enter Cash Amount (Taka):");
        fuelAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        amountOfCashPaidInput = new JTextField(15);

        JLabel gasTypeLabel = new JLabel("Select Gas Type:");
        gasTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gasTypeCombo = new JComboBox<>(new String[]{"Petrol", "Octane", "Diesel"});

        JLabel vehicleTypeLabel = new JLabel("Enter Transport Category:");
        vehicleTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JComboBox<String> vehicleTypeCombo = new JComboBox<>(new String[]{"Private", "Public"});

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try{
                        validateLicenseInput();
                        validateFuelInput();
                        int index2 = vehicleTypeCombo.getSelectedIndex();
                        controller.writeDataToFile(index2);
                        controller.getBrta().printDigitalReceipt();
                    }
                    catch (InvalidLicenseNumberException | SuspectedFoulPlayException | IOException ex){
                        ex.printStackTrace();
                    }


                    /**************Test Code
                     Used to test whether input reached the Controller and BRTA instance properly
                    //controller.displayLicenseNumber();
                    //JOptionPane.showMessageDialog(Graphics.this, "You entered the license number " + licenseNumber);
                     ***********************/
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(licenseLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(licenseInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(fuelAmountLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(amountOfFuelInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(cashAmountLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(amountOfCashPaidInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(gasTypeLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(gasTypeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(vehicleTypeLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(vehicleTypeCombo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;

        mainPanel.add(submitButton, gbc);
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void validateLicenseInput() throws InvalidLicenseNumberException{
        String pattern = "^[A-Z]{5}\\sMETRO\\s[A-Z]{2,3}-\\d{4}$";

        String licenseNumber = licenseInput.getText();

        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(licenseNumber);

        if(!(matcher.matches())){
            JOptionPane.showMessageDialog(Graphics.this,"Invalid License Number");
            throw new InvalidLicenseNumberException("Invalid License Number");
        }
        else{
            controller.setLicenseNumber(licenseNumber);
            controller.getBrta().readDataFromFile();
            controller.setNameOfOwner(controller.getBrta().getName());
            controller.setPhoneNumber(controller.getBrta().getPhoneNumber());
        }
    }

    private void validateFuelInput() throws SuspectedFoulPlayException{

        double amountOfFuel = Double.parseDouble(amountOfFuelInput.getText());
        double amountOfCashPaid = Double.parseDouble(amountOfCashPaidInput.getText());
        int index = gasTypeCombo.getSelectedIndex();

        double amountOwed;
        switch (index) {
            case 0: // Petrol
                amountOwed = BRTA.getPetrolPrice();
                break;
            case 1: // Octane
                amountOwed = BRTA.getOctanePrice();
                break;
            case 2: // Diesel
                amountOwed = BRTA.getDieselPrice();
                break;
            default:
                throw new IllegalArgumentException("Invalid fuel type selected");
        }

        checkPaymentAndSetData(amountOfCashPaid, amountOwed, amountOfFuel);
    }

    private void checkPaymentAndSetData(double amountOfCashPaid, double amountOwed, double amountOfFuel) throws SuspectedFoulPlayException {
        if (amountOfCashPaid < amountOwed) {
            JOptionPane.showMessageDialog(null, "Message sent to : " + controller.getBrta().getPhoneNumber() +
                    "\nAmount of Cash Paid : " + amountOfCashPaid + "\nAmount Left to Pay : " + amountOwed);
            throw new SuspectedFoulPlayException("Amount of cash remaining to be paid : " + (amountOwed - amountOfCashPaid));
        } else {
            controller.setAmountOfFuel(amountOfFuel);
            controller.setAmountOfCashPaid(amountOfCashPaid);
        }
    }

}