package basic1;
abstract class Product {
private String name;
private double price;
Product(String name,double price){
	this.name=name;
	this.price=price;
}
       public String getName() {
	return name;
}
       	public void setName(String name) {
	this.name = name;
}
       	public double getPrice() {
	return price;
}
       	public void setPrice(double price) {
	if(price>0) {
		this.price = price;
	}
}
       	abstract void displaydetails();
}


class electronics extends Product {
	private String brand;
		electronics(String name, double price, String brand) {
			super(name, price);
			this.brand=brand;
	}
		public String getbrand() {
			return brand;
		}
	
	void displaydetails() {
		System.out.println("Product: "+getName());
		System.out.println("Brand: "+getbrand());
		System.out.println("Price: "+getPrice());
	}
	
}
class clothing extends Product{
	private String size;
	
			clothing(String name, double price, String size) {
					super(name, price);
					this.size=size;
	}
			public String getsize() {
				return size;
			}
	void displaydetails() {
		System.out.println("Product: "+getName());
		System.out.println("Size: "+getsize());
		System.out.println("Price: "+getPrice());
	}
	public static void main(String[] args) {
		Product p1=new electronics("Mobile", 20000, "Iqoo");
		Product p2=new clothing("Shirt", 2000, "Twills");
		p1.displaydetails();
		p2.displaydetails();
	}
}

