package basic1;


class Adddress {
    private String city;
    private String pincode;
    public Adddress(String city,String pincode) {
        this.setCity(city);
        this.setPincode(pincode);
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPincode() {
        return pincode;
    }
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
final class Employee1{
    private final Adddress address;
    private final String name;
    private final int id;
    public Employee1(Adddress address,String name,int id) {
        this.name=name;
        this.id=id;
        this.address=new Adddress(address.getCity(),address.getPincode());
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public Adddress getAddress() {
        return new Adddress(address.getCity(),address.getPincode());
    }
}
class main1{
    public static void main(String[] args) {
        Adddress ad=new Adddress("Kanigiri", "523530");
        Employee1 emp=new Employee1(ad, "Sriraj", 123);
        ad.setCity("Hyderabad");//modifying the original
        Adddress temp=emp.getAddress();
        temp.setCity("Chennai");//trying with set method lets see its change or not
        System.out.println("Employee Details:");
        System.out.println("Name :"+emp.getName());
        System.out.println("ID: "+emp.getId());
        System.out.println("City :"+emp.getAddress().getCity());
        System.out.println("Pin Code: "+emp.getAddress().getPincode());
    }
}
