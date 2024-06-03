import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphics extends JFrame {
    private JTextField licenseField, fuelAmountField;
    private JComboBox<String> gasTypeCombo, carTypeCombo;
    private BRTA brta;
    private CarbonEmission carbonEmission;

    public Graphics(BRTA brta) {
        super("Gas Refill Management System");
        this.brta = brta;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel licenseLabel = new JLabel("Enter License Number:");
        licenseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        licenseField = new JTextField(15);

        JLabel fuelAmountLabel = new JLabel("Enter Fuel Amount (Litres):");
        fuelAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fuelAmountField = new JTextField(15);

        JLabel gasTypeLabel = new JLabel("Select Gas Type:");
        gasTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gasTypeCombo = new JComboBox<>(new String[]{"Petrol", "Octane", "Diesel"});

        JLabel carTypeLabel = new JLabel("Enter Car Category:");
        carTypeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        carTypeCombo = new JComboBox<>(new String[]{"Private", "Public"});

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGasdetails();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(licenseLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(licenseField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(fuelAmountLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(fuelAmountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(gasTypeLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(gasTypeCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(carTypeLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(carTypeCombo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(submitButton, gbc);
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void saveGasdetails() {
        String licenseNumber = licenseField.getText();
        String fuelAmount = fuelAmountField.getText();
        String gasType = (String) gasTypeCombo.getSelectedItem();
        String carType = (String) carTypeCombo.getSelectedItem();

        if (carType.equals("Private")) {
            PrivateCar privateCar = new PrivateCar(licenseNumber, fuelAmount, carType, gasType,brta);
            if (privateCar.checkLicense(licenseNumber)) {
                JOptionPane.showMessageDialog(this, "Message Sent", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No license Found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (carType.equals("Public")) {
            PublicTransport publicTransport = new PublicTransport(licenseNumber, fuelAmount, carType, gasType, brta,carbonEmission);
            if (publicTransport.checkLicense(licenseNumber)) {
                JOptionPane.showMessageDialog(this, "Message Sent", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No license Found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
