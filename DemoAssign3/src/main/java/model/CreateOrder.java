package model;

public class CreateOrder {
	
	private  int orderID;
	private  int clientID;
	private  float totalPrice;
	
	/**
	 * constructor create order
	 * @param orderID - id order
	 * @param clientID - id client
	 * @param totalPrice - pret total
	 */
	
	public CreateOrder(int orderID, int clientID, float totalPrice) {
		super();
		this.orderID = orderID;
		this.clientID = clientID;
		this.totalPrice = totalPrice;
	}
	public CreateOrder() {
		// TODO Auto-generated constructor stub
	}
	public  int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public  int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	public  float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public String toString() {
		return "CreateOrder [orderID=" + orderID + ", clientID=" + clientID + ", totalPrice=" + totalPrice + "]";
	}
	
	

}
