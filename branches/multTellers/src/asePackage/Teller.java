package asePackage;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Teller extends Thread{
	private int id;
	private QueueManager qm;
	private AccountManager accountManager;
	private Queue customerInQueue;
	private Log log;
	private static int tellerGenerationDelay;
	private boolean bankIsClosed = false;
	private static boolean bankIsPaused = false;
	private CountDownLatch countDown;	

	/*
	 *  holds a message about what went wrong if the transaction is not valid
	 */
	private String errorMessage;  

	/**
	 * The constructor for a teller object.
	 * @param accountManager The account manager of the application
	 * @param log The log manager of the application
	 * @param tellerID new teller id
	 * @param countDown the CountDownLatch element for closing the bank
	 */
	public Teller(AccountManager accountManager, Log log, int tellerID,CountDownLatch countDown) {
		super("Teller("+tellerID+")");
		this.countDown = countDown;
		this.qm = QueueManager.getInstance();
		this.accountManager = accountManager;
		this.log = log;
		this.id = tellerID;
		tellerGenerationDelay = 2000;
	}

	/**
	 * Gets the next customer from the queue.
	 */
	public  void getNextCustomer(){
		customerInQueue = qm.removeQueueElement();
		if(customerInQueue != null) {
			log.addLogEventExitQueue(customerInQueue.getQueueNumber(), id, customerInQueue.getCustomer(), LogEvent.EXITQUEUE);
		}
	}

	/**
	 * Makes all the transactions that the customers wants to do if they are valid
	 * @throws InterruptedException 
	 */
	public void doTransactions() throws InterruptedException{
		if (customerInQueue==null) return;
		Customer currentCustomer = customerInQueue.getCustomer();
		ArrayList<Transaction> transactions = customerInQueue.getTransactionList();
		boolean isValidTransaction;
		for (Transaction transaction : transactions){

			//add a log event to note the start of a new transaction
			log.addLogEventStartTrans(customerInQueue.getQueueNumber(), id, currentCustomer, transaction, LogEvent.STARTTRANSACTION);
			//set a small delay before starting the actual transaction
			Thread.sleep(tellerGenerationDelay/2);

			isValidTransaction = false;

			if (transaction.getType().equals(Transaction.DEPOSIT)) 
				isValidTransaction = doDeposit(transaction,currentCustomer);

			if (transaction.getType().equals(Transaction.WITHDRAWAL))
				isValidTransaction = doWithdrawal(transaction,currentCustomer);

			if(transaction.getType().equals(Transaction.OPEN))
				isValidTransaction = openAccount(transaction, currentCustomer);

			if(transaction.getType().equals(Transaction.CLOSE)){
				isValidTransaction = closeAccount(transaction, currentCustomer);
			}

			if(transaction.getType().equals(Transaction.VIEWBALANCE)){
				isValidTransaction = viewBalance(transaction,currentCustomer);
			}
			if(transaction.getType().equals(Transaction.DEPOSITFOREIGNACCOUNT)){
				isValidTransaction = doDepositForeignAccount(transaction,currentCustomer);
			}
			if(transaction.getType().equals(Transaction.TRANSFER)){
				isValidTransaction = doTransfer(transaction,currentCustomer);
			}
			
			//create a log event for transaction result
			generateReport(isValidTransaction,transaction);

			//sleeps a small period of time before starting the next transaction
			Thread.sleep(tellerGenerationDelay/4);			
		}
	}

	/**
	 * Realise a transfer operation
	 */
	private boolean doTransfer(Transaction transaction, Customer currentCustomer) {
		double amount;
		boolean isValidTransaction = 
			isValidTransaction(transaction,currentCustomer);

		//if all the conditions were meet it make the transfer from the old account
		//to the new account for required amount of money
		if (isValidTransaction){
			Account custAccount = transaction.getAccount();
			Account foreignAccount = transaction.getForeignAccount();

			amount=MyUtilities.roundDouble(transaction.getAmount());
			custAccount.withdrawMoney(amount);
			foreignAccount.depositMoney(amount);
		}

		return isValidTransaction;
	}

	/**
	 * Realise a deposit to foreign account transaction 
	 */
	private boolean doDepositForeignAccount(Transaction transaction, Customer currentCustomer) {
		double amount;
		boolean isValidTransaction = 
			isValidTransaction(transaction,currentCustomer);
		//if the conditions are meet the required amount of money are deposit in the required account
		if (isValidTransaction){
			Account foreignAccount = transaction.getForeignAccount();
			amount=MyUtilities.roundDouble(transaction.getAmount());
			foreignAccount.depositMoney(amount);
		}

		return isValidTransaction;
	}

	/**
	 * doesn't do anything special.
	 * @param transaction 
	 * @param currentCustomer
	 * @return true if this transaction is valid
	 */
	private boolean viewBalance(Transaction transaction,
			Customer currentCustomer) {
		return isValidTransaction(transaction, currentCustomer);
	}

	/**
	 * Closed the account required by the customer
	 * @param transaction
	 * @param currentCustomer
	 * @return true if the transaction was done, else in the other case
	 */
	private boolean closeAccount(Transaction transaction, Customer currentCustomer) {
		boolean isValidTransaction = isValidTransaction(transaction, currentCustomer);
		if(isValidTransaction){
			Account account = transaction.getAccount();
			//sets the amount to be withdraw in the transaction so it can be used as
			//info for the log 
			transaction.setAmount(account.getBalance());
			//first withdraws all the money from the account
			if(account.getBalance()>0) 
				account.withdrawMoney(account.getBalance());
			accountManager.deleteAccount(account);
		}
		return isValidTransaction;
	}

	/**
	 * Open a new account for a customer if he meets the requirements
	 * @param transaction
	 * @param currentCustomer
	 * @return true if the transaction was done, else in the other case
	 */
	private boolean openAccount(Transaction transaction, Customer currentCustomer) {
		double amount;
		boolean isValidTransaction = isValidTransaction(transaction,currentCustomer);
		if(isValidTransaction){
			Account account = accountManager.addAccount(currentCustomer);
			transaction.setAccount(account);
			amount=MyUtilities.roundDouble(transaction.getAmount());
			account.depositMoney(amount);

		}
		return isValidTransaction;
	}

	/**
	 * Realise a withdrawel from the customer account
	 * @param transaction
	 * @param currentCustomer
	 * @return true if the transaction was done, else in the other case
	 */
	private boolean doWithdrawal(Transaction transaction, Customer currentCustomer) {
		double amount;
		boolean isValidTransaction = isValidTransaction(transaction,currentCustomer);
		if (isValidTransaction){
			amount=MyUtilities.roundDouble(transaction.getAmount());
			transaction.getAccount().withdrawMoney(amount);
		}
		return isValidTransaction;
	}

	/**
	 * deposits money in the an account. In order to the deposit to be complete 
	 * the transaction must be a valid one (check the comments on the isValidTransaction 
	 * for more information about valid transactions)
	 * 
	 * @param transaction The transaction to be made
	 * @param currentCustomer the customer that wants to make the transaction
	 * @return true if the transaction is done, false if the transactions is not valid.
	 */
	private boolean doDeposit(Transaction transaction, Customer currentCustomer) {
		double amount;
		boolean isValidTransaction = 
			isValidTransaction(transaction,currentCustomer);

		if (isValidTransaction){
			Account account = transaction.getAccount();
			amount=MyUtilities.roundDouble(transaction.getAmount());
			account.depositMoney(amount);
		}

		return isValidTransaction;
	}

	/**
	 * checks if the current transaction is valid (e.g. can be made). Valid transactions are
	 * Deposit / Close / View Balance: only if the customer owns the account used for the transaction
	 * Deposit to foreign account: only if that account is existing
	 * Withdrawal : only if the customer own the account he wants to withdraw money from
	 * and also the money to be withdrawn does not exceed the money in the account
	 * Open : An account can be opened only if the customer does not have more that the maximum 
	 * account allowance.
	 * Transfer: if both accounts exists, at list one is customer's account, and the customer has
	 * enough money in his account to transfer
	 * It also generates an error message in case the transaction is not valid
	 * @param transaction the transaction to be validated
	 * @return true if the transaction is valid, false otherwise
	 */
	private boolean isValidTransaction(Transaction transaction,Customer currentCustomer){
		boolean isValid = true;
		//in case the transaction is withdrawal
		//checks if the account belongs to the customer and has enough money
		if (transaction.getType().equals(Transaction.WITHDRAWAL))
			isValid  = checkValidWidrawal(transaction, currentCustomer);

		//in case the transaction is to open an account checks if the customer has less than 2 
		//accounts.
		if (transaction.getType().equals(Transaction.OPEN)){
			if(currentCustomer.getNumberOfAccounts()== Customer.MAXACCOUNTS){
				isValid = false;
				errorMessage = "Customer has already "+ Customer.MAXACCOUNTS + " accounts";
			}
		}

		//in case the transaction is to deposit money or close the account or 
		// view balance, it checks if the customer owns the account

		if(transaction.getType().equals(Transaction.DEPOSIT) || 
				transaction.getType().equals(Transaction.CLOSE) ||
				transaction.getType().equals(Transaction.VIEWBALANCE))
			isValid = isValidDepCloView(transaction,currentCustomer);

		if(transaction.getType().equals(Transaction.DEPOSITFOREIGNACCOUNT)){

			//in case the account was closed during the bank session we give a 
			//different message
			if(transaction.getForeignAccount().isClosed()==true){
				errorMessage = "Account is closed";
				isValid = false;
			}
		}

		if(transaction.getType().equals(Transaction.TRANSFER))
			isValid = isValidTrasfer(transaction,currentCustomer);

		return isValid;
	}

	/**
	 * Test if the account is open and if the customer is the owner of the account
	 * @param transaction actual transaction
	 * @param currentCustomer 
	 * @return true if the conditions are passed
	 */
	private boolean isValidDepCloView(Transaction transaction,	Customer currentCustomer) {
		boolean isValid = true;
		//in case the account was closed during the bank session we give a 
		//different message
		if(transaction.getAccount().isClosed()==true){
			errorMessage = "Account is closed";
			isValid = false;
		}
		else if(!currentCustomer.hasAccount(transaction.getAccount())){
			errorMessage = "Not owner of the account";
			isValid = false;
		}
		return isValid;
	}

	/**
	 * Test if the customer account and the foreign are open, if the customer is the owner of 
	 * the account from which we get the money and he has enough money in that account
	 * @param transaction actual transaction
	 * @param currentCustomer 
	 * @return true if the conditions are passed
	 */
	private boolean isValidTrasfer(Transaction transaction,	Customer currentCustomer) {
		boolean isValid  = true;
		if(transaction.getAccount().isClosed()==true){
			errorMessage = "Account is closed";
			isValid = false;
		}
		else if(!currentCustomer.hasAccount(transaction.getAccount())){
			errorMessage = "Not owner of the account";
			isValid = false;
		}
		else if(transaction.getAccount().getBalance()< transaction.getAmount() ){
			errorMessage = "There is not enough money";
			isValid = false;				
		}
		else if(transaction.getForeignAccount().isClosed()==true){
			errorMessage = "Foreign account is closed";
			isValid = false;
		}

		return isValid;
	}

	/**
	 * Test if the customer account is open, if the customer is the owner of 
	 * the account from which we get the money, if he has enough money in that account
	 * and he didn't reach his withdraw limit
	 * @param transaction actual transaction
	 * @param currentCustomer 
	 * @return true if the conditions are passed
	 */
	private boolean checkValidWidrawal(Transaction transaction,	Customer currentCustomer) {
		boolean isValid = true;
		//in case the account was closed during the bank session we give a 
		//different message
		if(transaction.getAccount().isClosed()==true){
			errorMessage = "Account is closed";
			isValid = false;
		}
		else if(!currentCustomer.hasAccount(transaction.getAccount())){
			errorMessage = "Not owner of the account";
			isValid = false;
		}
		else if(transaction.getAccount().getBalance()< transaction.getAmount() ){
			errorMessage = "There is not enough money";
			isValid = false;

		}
		else if (log.getCustomerWithdrawalTotal(currentCustomer)+ transaction.getAmount()> 200){
			errorMessage = "Reached total withdrawal limit ";
			isValid = false;
		}
		return isValid;

	}

	/**
	 * generates the log of the current transaction
	 * @param isValidTransaction 
	 * @param transaction
	 */
	private void generateReport(boolean isValidTransaction, Transaction transaction) {
		if(isValidTransaction){
			log.addLogEvent(customerInQueue.getQueueNumber(), id, customerInQueue.getCustomer(), transaction, LogEvent.SUCCESS,errorMessage);
		}
		else{
			log.addLogEvent(customerInQueue.getQueueNumber(), id, customerInQueue.getCustomer(), transaction, LogEvent.FAIL,errorMessage);
		}

	}
	
	/*
	 * The execution method
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		//while the bank is open and the queue is empty runs
		while(!bankIsClosed || !qm.isQueueEmpty() ) {
			try {			
				if (!bankIsPaused){
					//get a new customer from the queue
					getNextCustomer();
					//delay the execution to part down the work in the gui
					Thread.sleep(tellerGenerationDelay/4);
					//do the transactions
					doTransactions();
					//customer leaves the tellers
					customerLeaves();
				}
				Thread.sleep(tellerGenerationDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//substract one position from the list with running threads
		countDown.countDown();
	}

	/*
	 * Get the customer out from the bank and write a log event for that
	 */
	private void customerLeaves() {
		if(customerInQueue != null) {
			System.out.println(customerInQueue.getCustomer());
			log.addLogEventExitQueue(customerInQueue.getQueueNumber(), id, customerInQueue.getCustomer(), LogEvent.EXITBANK);
			customerInQueue.getCustomer().setInsideBank(false);
		}
	}

	//Get and set methods
	public void setBankClosed(boolean bankIsClosed) {
		this.bankIsClosed = bankIsClosed;
	}

	public static void  setTellerGenerationDelay(int tellerGenerationDelay) {
		Teller.tellerGenerationDelay = tellerGenerationDelay;
	}

	public static int getTellerGenerationDelay() {
		return tellerGenerationDelay;
	}

	public static void setBankIsPaused(boolean bankIsPaused) {
		Teller.bankIsPaused = bankIsPaused;
	}
}


