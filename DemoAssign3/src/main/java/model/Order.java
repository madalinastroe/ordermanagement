package model;

public class Order {
	
	private  int orderID;
	private  String product;
	private  int quantity;
	
	/**
	 * Constructor order
 	 * @param orderID - id order
	 * @param product - nume produs
	 * @param quantity - cantitate
	 */
	public Order(int orderID, String product, int quantity) {
		super();
		this.orderID = orderID;
		this.product = product;
		this.quantity = quantity;
	}
	public Order() {
		// TODO Auto-generated constructor stub
	}
	public  int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public  String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public  int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", product=" + product + ", quantity=" + quantity + "]";
	}
	
	

}
