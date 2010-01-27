package asePackage;

public class Customer {
	
	
	//comment sdfsdfsdfsdfdfsd
	private int customerId;
	private String customerFullName;

	public Customer(int customerId, String customerFullName) {
		this.customerId = customerId;
		this.customerFullName = customerFullName;
	}

	public String getFullName() {
		return customerFullName;
	}

	public int getId() {
		return customerId;
	}

}
