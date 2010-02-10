package asePackage;

import java.util.ArrayList;

/**
 * @author CrYeo™
 *
 */
public class Queue {
	private Customer queueCustomer;
	private ArrayList<Transaction> transactionList;
	private int queueNumber;
	
	public Queue() {
		//this.queueCustomer=new Customer();
		//please create the posibility to insert an empty customer
		this.transactionList=new ArrayList<Transaction>();
	}
	public Queue(Customer queueCustomer, Transaction newTransaction, int queueNumber) {
		this.queueCustomer=queueCustomer;
		this.transactionList= new ArrayList<Transaction>();
		this.transactionList.add(newTransaction);
		this.queueNumber=queueNumber;
	}
	public Queue(Customer queueCustomer, ArrayList<Transaction> transactionList, int queueNumber) {
		this.queueCustomer=queueCustomer;
		this.transactionList= transactionList;
		this.queueNumber=queueNumber;
	}
	public Customer getCustomer () {
		return queueCustomer;
	}
	public ArrayList<Transaction> getTransactionList() {
		return transactionList;
	}
	public int getQueueNumber() {
		return queueNumber;
	}
}
