package defaul_packaget;
import org.junit.*;
import static org.junit.Assert.*;

public class Hmmm {
	
	@Test
	public void sumOfNums(){
		MyLolClass tc = new MyLolClass();
		assertEquals(4, tc.sumOfNums(1,3));
		assertFalse("It shouldn't add 3",3 == tc.sumOfNums(1, 1));
		
	}

	

}
