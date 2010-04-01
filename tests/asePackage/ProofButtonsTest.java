package asePackage;
/**
 * @author Ioan
 *
 */
import static org.junit.Assert.*;

import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;

public class ProofButtonsTest {
	ProofButtons proof;
	@Before
	public void setUp() throws Exception {
		proof = new ProofButtons();
	}

	/**
	 * A test to present an implementation of an Oracle test unit
	 */
	@Test
	public void testProofButtons() {
		JFrame frame= new JFrame();
		proof.main(null);
		int n = JOptionPane.showConfirmDialog(
                frame, "Did the application atempt to start?",
                "JUnit testProofButtons",
                JOptionPane.YES_NO_OPTION);
		if (n != JOptionPane.YES_OPTION)
			fail("Open Document fail");
	}

	/**
	 * A test to present an implementation of an Oracle test unit
	 */
	@Test
	public void testProofButtons2() {
		JFrame frame= new JFrame();
		proof.main(null);
		int n = JOptionPane.showConfirmDialog(
                frame, "Did the application permit changing from on to off and back?",
                "JUnit testProofButtons",
                JOptionPane.YES_NO_OPTION);
		if (n != JOptionPane.YES_OPTION)
			fail("Open Document fail");
	}
}
