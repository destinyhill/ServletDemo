package httpSession;

public class Product {
	private int id;
	private String name;
	private String description;
	private float price;
	
	public  Product(int id,String name,String description,float price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public String getName() {
		return this.name;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}

}
