package dec64;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author ben
 */
public class SimpleTest {

    @Test
    public void simpleAdd() {
        @DEC64 long minusOne = Math64.DEC64_NEGATIVE_ONE;
        @DEC64 long one = Math64.DEC64_ONE;

        assertEquals("-1 + 1 should equal 0", Math64.DEC64_ZERO, Math64.add(one, minusOne));
    }

    @Test
    public void simpleMult() {
        @DEC64 long ten = Math64.of(10, (byte)0);
        // FIXME Need proper equality method to deal with multiple representations
        assertTrue("0.1 * 1 should equal 1", Math64.equals(Math64.DEC64_ONE, Math64.multiply(ten, Math64.DEC64_POINT_ONE)));
    }

    @Test
    public void simpleEquals() {
        assertTrue("1 should equal 1", Math64.equals(256, 2815));
        assertTrue("1 should equal 1", Math64.equals(2815, 256));
    }
}
