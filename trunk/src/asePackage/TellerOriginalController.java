package asePackage;

public class TellerOriginalController {

	public TellerOriginalController(TellerOriginalGui tellerGui, Bank bank) {
		bank.getTeller(tellerGui.getTellerID()).addObserver(tellerGui);
	}
}
