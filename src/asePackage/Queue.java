package asePackage;

import java.util.ArrayList;

/**
 * Queue class it contains all the information about a customer that has a 
 * place in the program queue of customers and also a list with his transactions. 
 * It also has defined methods to add a new customer to the list or to extract him. 
 * The class also contains a method which returns the customer queue number.
 */
public class Queue {
	private Customer queueCustomer;
	private ArrayList<Transaction> transactionList;
	private int queueNumber;
	
	/**
	 * The constructor for the case we don't have an initial set of informations
	 * about the customer and about his transactions.
	 */
	public Queue() {
		this.transactionList=new ArrayList<Transaction>();
	}
	/**
	 * The constructor for the case in which we have all the details about 
	 * the customer and about one transaction.
	 * @param queueCustomer customer object
	 * @param newTransaction a transaction
	 * @param queueNumber the queue number of the customer
	 */
	public Queue(Customer queueCustomer, Transaction newTransaction, int queueNumber) {
		this.transactionList= new ArrayList<Transaction>();
		this.queueCustomer=queueCustomer;
		this.transactionList.add(newTransaction);
		this.queueNumber=queueNumber;
	}
	/**
	 * The constructor for the case in which a list of transactions is supplied
	 * for a single customer from a certain queue position.
	 * @param queueCustomer customer object
	 * @param transactionList a list of transactions for the customer
	 * @param queueNumber the queue number of the customer
	 */
	public Queue(Customer queueCustomer, ArrayList<Transaction> transactionList, int queueNumber) {
		this.queueCustomer=queueCustomer;
		this.transactionList= transactionList;
		this.queueNumber=queueNumber;
	}
	/**
	 * Supplies the information about the current customer from the queue.
	 * @return customer details
	 */
	public Customer getCustomer () {
		return queueCustomer;
	}
	/**
	 * The method supplies all the transactions associated 
	 * with a customer from a certain queue number.
	 * @return transaction list for the customer
	 */
	public ArrayList<Transaction> getTransactionList() {
		return transactionList;
	}
	/**
	 * Provides the customer number from the program queue.
	 * @return queue number of the customer
	 */
	public int getQueueNumber() {
		return queueNumber;
	}
}
