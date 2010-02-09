package asePackage;

public class Customer {
	
	
	//comment sdfsdfsd
	private double customerId;
	private String customerFullName;

	public Customer(int customerId, String customerFullName) {
		this.customerId = customerId;
		this.customerFullName = customerFullName;
	}

	public String getFullName() {
		return customerFullName;
	}

	public double getId() {
		return customerId;
	}

}
