package model;

public class Product {
	
	private  int productID;
	private  String name;
	private  int quantity;
	private  float price;
	
	public Product()
	{
		
	}
	
	/**
	 * constructor clasa product
	 * @param productID - id produs
	 * @param name - nume produs
	 * @param quantity - cantitate produs
	 * @param d - pret produs
	 */
	public Product(int productID, String name, int quantity, float d) {
		super();
		this.productID = productID;
		this.name = name;
		this.quantity = quantity;
		this.price = d;
	}

	public  int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public  String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public  int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public  float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", name=" + name + ", quantity=" + quantity + ", price=" + price
				+ "]";
	}
	
	
	
	

}
