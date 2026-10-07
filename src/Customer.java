public class Customer {

    private String name;
    private String province;

    // Constructor
    public Customer(String name, String province) {
        this.name = name;
        this.province = province;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for province
    public String getProvince() {
        return province;
    }

    // Setter for province
    public void setProvince(String province) {
        this.province = province;
    }
}