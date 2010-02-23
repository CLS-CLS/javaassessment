package asePackage;

import java.util.ArrayList;

/**
 * @author Ioan
 *
 */

public class Log {
	private ArrayList<LogEvent> logEventList;

	public Log() {
		this.logEventList = new ArrayList<LogEvent>();
	}
	public Log(ArrayList<LogEvent> logEventList) {
		this.logEventList = logEventList;
	}
	
	public void addLogEvent(int queueNumber, Customer customer, Transaction transaction, String status, String errorMessage) {
		logEventList.add(new LogEvent(queueNumber, customer, transaction, status, errorMessage));
	}
	public void addLogEvent(int queueNumber, Customer customer, String status) {
		logEventList.add(new LogEvent(queueNumber, customer, status));
	}
	public int getProcessedCustomersNumber() {
		int queueNumber=0;
		
		for(int i=0;i<logEventList.size();i++) {		
			if(logEventList.get(i).getQueueNumber()>queueNumber) {
				queueNumber=logEventList.get(i).getQueueNumber();
			}
		}
		return queueNumber;
	}
	public double getDepositTotal(){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if(logEventList.get(i).getTransactionType()=="deposit")
				total+=logEventList.get(i).getTransactionSum();
		}
		return total;
	}
	public double getWithdrawalTotal(){
		int i;
		double total=0;
		for(i=0;i<logEventList.size();i++) {
			if(logEventList.get(i).getTransactionType()=="withdrawal")
				total+=logEventList.get(i).getTransactionSum();
		}
		return total;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result="";
		int i;
		
		for(i=0;i<logEventList.size();i++) {
			result+=logEventList.get(i)+"\n";
		}
		result+=getStatistics();
		return result;
	}
	private String getStatistics() {
		String result;
		result="\nStatistics\n";
		result+="-------------\n";
		result+="Processed Customers Number : " + getProcessedCustomersNumber() + "\n";
		result+="Total Deposited Money : " + getDepositTotal() + "\n";
		result+="Total Withdrawn Money : " + getWithdrawalTotal() + "\n";
		return result;
	}
}
