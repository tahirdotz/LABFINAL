import java.text.SimpleDateFormat;
import java.util.Date;

public class PrivateCarSaveDetails {
    private String licenseNumber;
    private String dateAndTime;
    //private String transactionID;
    private String fuelAmount;
    private String totalMoney;




    public PrivateCarSaveDetails(String licenseNumber, String fuelAmount, String totalMoney, String contactNumber) {
        this.licenseNumber = licenseNumber;
        //this.transactionID = transactionID;
        this.fuelAmount = fuelAmount;
        this.totalMoney = totalMoney;

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.dateAndTime = dateFormat.format(currentDate);
    }

    @Override
    public String toString() {
        return "PrivateCarSaveDetails{" +
                "licenseNumber='" + licenseNumber + '\'' +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", fuelAmount='" + fuelAmount + '\'' +
                ", totalMoney='" + totalMoney + '\'' +
                '}';
    }

}

