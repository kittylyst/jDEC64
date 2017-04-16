package dec64;

import static org.junit.Assert.*;
import org.junit.Test;
import static dec64.Basic64.*;
import static dec64.Constants64.*;
import org.junit.Ignore;

/**
 *
 * @author ben
 */
public class SimpleTest {

    @Test
    public void simpleAdd() {
        @DEC64 long minusOne = DEC64_NEGATIVE_ONE;
        @DEC64 long one = DEC64_ONE;

        assertEquals("-1 + 1 should equal 0", DEC64_ZERO, add(one, minusOne));

        @DEC64 long ten = of(10, (byte) 0);
        @DEC64 long ten2 = of(1, (byte) 1);
        assertTrue("10 + 10 should equal 20", equals64(of(20, (byte) 0), add(ten2, ten)));
        assertTrue("10 + 10 should equal 20", equals64(of(20, (byte) 0), add(ten, ten2)));

        @DEC64 long hundred = of(10, (byte) 1);
        assertTrue("100 + 10 should equal 110", equals64(of(110, (byte) 0), add(hundred, ten)));

    }

    @Test
    public void simpleMult() {
        @DEC64 long ten = of(10, (byte) 0);
        assertTrue("0.1 * 1 should equal 1", equals64(DEC64_ONE, multiply(ten, DEC64_POINT_ONE)));
        @DEC64 long two = of(2, (byte) 0);
        @DEC64 long three = of(30, (byte) -1);
        @DEC64 long four = of(4, (byte) 0);
        @DEC64 long six = of(600, (byte) -2);
        assertTrue("3 * 4 should equal 2 * 6", equals64(multiply(three, four), multiply(two, six)));
        assertTrue("4 * 3 should equal 2 * 6", equals64(multiply(four, three), multiply(two, six)));
        assertTrue("3 * 4 should equal 6 * 2", equals64(multiply(three, four), multiply(six, two)));
        assertTrue("4 * 3 should equal 6 * 2", equals64(multiply(four, three), multiply(six, two)));
    }

    @Test
    public void simpleEquals() {
        // This test case only exists for demonstration purposes for newcomers
        // to working witht the DEC64 format
        assertTrue("1 should equal 1", equals64(256, 2815));
        assertTrue("1 should equal 1", equals64(2815, 256));
        assertTrue("1 should equal 1", equals64(256, 25854));
        assertTrue("1 should equal 1", equals64(25854, 256));
        assertTrue("2 should equal 2", equals64(512, 512253));
        assertTrue("2 should equal 2", equals64(512253, 512));
        assertTrue("2 should equal 2", equals64(512, 5120252));
        assertTrue("2 should equal 2", equals64(5120252, 512));
        assertTrue("2 should equal 2", equals64(512, 51200251));
        assertTrue("2 should equal 2", equals64(51200251, 512));
        assertTrue("2 should equal 2", equals64(512, 512_000_250));
        assertTrue("2 should equal 2", equals64(512_000_250, 512));
    }

    @Test
    public void genEquals() {
        @DEC64 long last = 256;
        byte exp = 0;
        for (int i = 0; i < 16; i++) {
            last *= 10;
            exp--;
            @DEC64 long check = last + exponentAsLong(exp);
            assertTrue("1_" + (-exp) + "(" + last + " + " + exp + ") should equal 1CL but is: " + check, equals64(DEC64_ONE, check));
            assertTrue("1_" + (-exp) + " should equal 1CL but is: " + check, equals64(check, DEC64_ONE));

            @DEC64 long canonical = canonical(check);
            assertTrue(canonical + "(" + last + " + " + exp + ") should equal 1CL (" + DEC64_ONE + ") but is: " + canonical, DEC64_ONE == canonical);
        }
    }

    @Test
    public void testIncDec() {
        assertTrue(isNaN(DEC64_NAN));
        
        assertTrue("0 = (-1)++", equals64(DEC64_ZERO, inc(DEC64_NEGATIVE_ONE)));
        assertTrue("1 = (0)++", equals64(DEC64_ONE, inc(DEC64_ZERO)));
        assertTrue("2 = (1)++", equals64(DEC64_TWO, inc(DEC64_ONE)));
        assertTrue("1 = (2)--", equals64(dec(DEC64_TWO), DEC64_ONE));
        assertTrue("0 = (1)--", equals64(dec(DEC64_ONE), DEC64_ZERO));
        assertTrue("-1 = (0)--", equals64(dec(DEC64_ZERO), DEC64_NEGATIVE_ONE));

        for (byte exp = 1; exp < Byte.MAX_VALUE; exp++) {
            @DEC64 long large = of(MAX_PROMOTABLE, exp);
            assertTrue("inc on large number should return same number", equals64(inc(large), large));
        }
        
        // FIXME Need to write inc() and dec() for floating point vals
         assertTrue("(3.5)++ = 4.5", equals64(inc(of(35, (byte)-1)), of(45, (byte)-1)));
    }

    @Test
    public void simpleFactorial() {
        long current = of(1, 0);
        // FIXME - should do up to FACTORIAL.length 
        for (int i = 2; i < 19; i++) {
            current = multiply(of(i, 0), current);
            assertTrue(i + "! value incorrect", equals64(FACTORIAL[i], current));
        }
        assertEquals(3602879701896396L, MAX_DEC64 / 10L);
    }

    @Test
    public void simpleFormatSTD() {
        String s = FormatMode.STANDARD.format(DEC64_ZERO);
        assertEquals("0", s);
        s = FormatMode.STANDARD.format(DEC64_ONE);
        assertEquals("1", s);
        s = FormatMode.STANDARD.format(DEC64_NEGATIVE_ONE);
        assertEquals("-1", s);
        s = FormatMode.STANDARD.format(DEC64_POINT_ONE);
        assertEquals("0.1", s);
        s = FormatMode.STANDARD.format(DEC64_TWO);
        assertEquals("2", s);
        s = FormatMode.STANDARD.format(DEC64_HALF);
        assertEquals("0.5", s);
        @DEC64 long monkey = of(5, 2);
        s = FormatMode.STANDARD.format(monkey);
        assertEquals("500", s);

        @DEC64 long pie = of(314, (byte) -2);
        s = FormatMode.STANDARD.format(pie);
        assertEquals("3.14", s);

        @DEC64 long milli = of(1, (byte) -3);
        s = FormatMode.STANDARD.format(milli);
        assertEquals("0.001", s);

        @DEC64 long micro = of(1, (byte) -6);
        s = FormatMode.STANDARD.format(micro);
        assertEquals("0.000001", s);
    }

    @Test
    public void complexFormatSTD() {
        @DEC64 long largeOne = 2815;
//        @DEC64 long largeOne = 256_000_000_000_000_241L;
        String s = FormatMode.STANDARD.format(largeOne);
        assertEquals("1.0", s);
    }

}
