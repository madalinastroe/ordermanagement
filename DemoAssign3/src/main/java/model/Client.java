package model;

public class Client {
	private  int clientID;
	private  String name;
	private  String address;
	
	 public Client(String name, String address) {
		 this.clientID = 0;
		 this.address = address;
		 this.name = name;
	 }
	
	 /**
	  * constructor client
	  * @param clientID - id client
	  * @param name - nume client
	  * @param address - adresa client
	  */
	public Client(int clientID, String name, String address) {
		super();
		this.clientID = clientID;
		this.name = name;
		this.address = address;
	}

	public Client() {
		// TODO Auto-generated constructor stub
	}

	public  int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public  String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public  String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Client [clientID=" + clientID + ", name=" + name + ", address=" + address + "]";
	}
	
	
	
}
