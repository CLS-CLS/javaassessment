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
	
	public void addLogEvent(int queueNumber, Customer customer, Transaction transaction, String status) {
		logEventList.add(new LogEvent(queueNumber, customer, transaction, status));
	}
	public void addLogEvent(int queueNumber, Customer customer, String status) {
		logEventList.add(new LogEvent(queueNumber, customer, status));
	}
	public int getProcessedCustomersNumber() {
		int i;
		ArrayList<Integer> queueNumber=new ArrayList<Integer>();
		
		for(i=0;i<logEventList.size();i++) {
			
			if(queueNumber.contains(logEventList.get(i).getQueueNumber())==false)
				queueNumber.add(logEventList.get(i).getQueueNumber());
		}
		return queueNumber.size();
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
		return result;
	}
	
}
