package asePackage;
/**
 * @author Ioan
 *
 */
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value={
		AccountManagerTest.class,
		AccountTest.class,			CustomerTest.class,
		LogEventTest.class,
		LogTest.class,
		MyUtilitiesTest.class,
		QueueManagerTest.class,
		QueueTest.class,
		TellerTest.class,
		ProofButtonsTest.class
})
	
public class AllTests{
}
	
