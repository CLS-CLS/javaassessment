package asePackage;

public class QueueController {

	public QueueController(QueueGui queueGui, Bank bank) {
		bank.getQm().addObserver(queueGui);
	}
}