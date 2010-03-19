package asePackage;

public class GuiAdapter extends GUI {
	AlternativeGUI altG;

	public GuiAdapter(AlternativeGUI alternativeGUI) {
		super();
		setVisible(false);
		altG = new AlternativeGUI();
	}

	@Override
	public void setText(String report) {
		double withdrawMoney = getWithdrawMoney(report);
		double depositMoney = getDepositMoney(report);
		int totCusts = getTotalCusts(report);
		altG.setCusts(totCusts);
		altG.setWithdrawn(withdrawMoney);
		altG.setDeposited(depositMoney);
	}

	/*
	 *The following methods take as parameter the report of the          
	 * bank and each one searches in the report to find the 
	 * appropriate information 
	 */
	private int getTotalCusts(String report) {
		String indexString = "Number of Processed Customers : ";
		int index = report.indexOf(indexString);
		index = index + indexString.length();
		int endIndex = report.indexOf("\n",index);
		System.err.println(report.substring(index, endIndex));
		return Integer.parseInt(
				report.substring(index, endIndex));
	}

	private double getDepositMoney(String report) {
		String indexString = "Total Deposited Money : £";
		int index = report.indexOf(indexString);
		index = index + indexString.length();
		int endIndex = report.indexOf("\n",index);
		System.err.println(report.substring(index, endIndex));
		return Double.parseDouble(
				report.substring(index, endIndex));
	}

	private double getWithdrawMoney(String report) {
		String indexString = "Total Withdrawn Money : £";
		int index = report.indexOf(indexString);
		index = index + indexString.length();
		int endIndex = report.indexOf("\n",index);
		System.err.println(report.substring(index, endIndex));
		return Double.parseDouble(
				report.substring(index, endIndex));
	}

}
