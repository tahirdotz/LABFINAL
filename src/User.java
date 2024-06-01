public class User {
    private String nameOfOwner;
    private String licenseNumber;
    private String cellNumber;

    public User(String nameOfOwner, String licenseNumber, String cellNumber){
        this.nameOfOwner = nameOfOwner;
        this.licenseNumber=licenseNumber;
        this.cellNumber = cellNumber;
    }

    public String getLicenseNumber(){
        return licenseNumber;
    }

    public String getCellNumber(){
        return cellNumber;
    }

    public String getName(){
        return nameOfOwner;
    }
}