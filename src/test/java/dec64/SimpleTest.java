package dec64;

import dec64.annotations.DEC64;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static dec64.Basic64.*;
import static dec64.Constants64.*;
import static dec64.FormatMode.STANDARD;
import static dec64.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kittylyst
 */
public class SimpleTest {

    @Test
    public void simpleNew() {
        assertFalse(isNaN(of(100L, 129L)), "Should be able to increase coefficient");
    }
    
    @Test
    public void simpleAdd() {
        assertEquals(DEC64_ZERO, add(DEC64_ONE, DEC64_NEGATIVE_ONE), "-1 + 1 should equal 0");

        @DEC64 long ten = of(10, (byte) 0);
        @DEC64 long ten2 = of(1, (byte) 1);
        assertTrue(equals64(of(20, (byte) 0), add(ten2, ten)), "10 + 10 should equal 20");
        assertTrue(equals64(of(20, (byte) 0), add(ten, ten2)), "10 + 10 should equal 20");

        @DEC64 long hundred = of(10, (byte) 1);
        assertTrue(equals64(of(110, (byte) 0), add(hundred, ten)), "100 + 10 should equal 110");
    }

    @Test
    public void confirm_simple_digit_behaviour() {
        assertEquals(1, digits(DEC64_ONE), "1 has 1 digit");
        assertEquals(1, digits(DEC64_TWO), "2 has 1 digit");
        assertEquals(2, digits(of(23L, 0)), "23 has 2 digits");
        assertEquals(4, digits(of(1337L, 0)), "1337 has 4 digits");
        assertEquals(1, digits(DEC64_NEGATIVE_ONE), "-1 has 1 digit");
        assertEquals(1, digits(DEC64_HALF), "0.5 has 1 digit");
        assertEquals(1, digits(DEC64_POINT_ONE), "0.1 has 1 digit");
        assertEquals(15, digits(DEC64_THIRD), "1 / 3 has 15 digits");
    }

    @Test
    public void simpleSub() {
        assertTrue(equals64(Basic64.of(9L, (byte) -1), subtract(DEC64_ONE, DEC64_POINT_ONE)), "1 - 0.1 should equal 0.9");
        assertTrue(equals64(Basic64.of(69965L, (byte) -2), subtract(Basic64.of(700, 0), Basic64.of(35L, (byte) -2))), "700 - 0.35 should equal 699.65");
    }

    @Test
    public void simpleMult() {
        assertTrue(equals64(DEC64_ONE, multiply(TEN, DEC64_POINT_ONE)), "0.1 * 1 should equal 1");
        assertTrue(equals64(multiply(THREE, FOUR), multiply(DEC64_TWO, SIX)), "3 * 4 should equal 2 * 6");
        assertTrue(equals64(multiply(FOUR, THREE), multiply(DEC64_TWO, SIX)), "4 * 3 should equal 2 * 6");
        assertTrue(equals64(multiply(THREE, FOUR), multiply(SIX, DEC64_TWO)), "3 * 4 should equal 6 * 2");
        assertTrue(equals64(multiply(FOUR, THREE), multiply(SIX, DEC64_TWO)), "4 * 3 should equal 6 * 2");
    }

    @Test
    public void simple_reciprocal() {
        @DEC64 long half = reciprocal(DEC64_TWO);
        assertTrue(equals64(half, DEC64_HALF), "1 / 2 should equal 0.5 instead of "+ FormatMode.STANDARD.format(half));
    }
    
    @Test
    public void simpleDivide() {
        assertTrue(equals64(divide(FOUR, DEC64_TWO), DEC64_TWO), "4 / 2 should equal 2");
        assertTrue(equals64(divide(TEN, DEC64_TWO), FIVE), "10 / 2 should equal 5");
        @DEC64 long three_p_five = of(35, (byte) -1);
        assertTrue(equals64(divide(SEVEN, DEC64_TWO), three_p_five), "7 / 2 should equal 3.5");
        @DEC64 long zero_p_one_two_five = divide(DEC64_ONE, EIGHT);
        assertTrue(equals64(of(125, (byte) -3), zero_p_one_two_five), "1 / 8 should equal 0.125 instead of "+ FormatMode.STANDARD.format(zero_p_one_two_five));
        assertTrue(equals64(divide(three_p_five, DEC64_TWO), of(175, (byte) -2)), "3.5 / 2 should equal 1.75");
        assertTrue(equals64(divide(of(15, (byte) -1), DEC64_HALF), THREE), "1.5 / 0.5 should equal 3");
        @DEC64 long third = divide(DEC64_ONE, THREE);
        // FIXME Differs in the last decimal place...
//        assertTrue("1 / 3 should equal 0.333333333333333 not " + FormatMode.STANDARD.format(third), equals64(third, DEC64_THIRD));
    }

    @Test
    @Disabled
    public void complex_divide() {
        // 5.191 / 3.0955
        @DEC64 long fiveAndABit = of(5_191L, (byte) -3);
        @DEC64 long threeAndABit = of(3_095_5L, (byte) -4);
        @DEC64 long expected = of(1_676_950_411_88L, (byte) -11);

        @DEC64 long actual = divide(fiveAndABit, threeAndABit);

        String outMsg = "5.191 / 3.0955 was " + STANDARD.format(actual) + " (" + actual + ") instead of " + STANDARD.format(expected);
        assertTrue(equals64(expected, actual), outMsg);

//        // 5.191176470 / 3.0955882352 == 1.676959619952494        
//        fiveAndABit = of(5_191_176_470L, (byte) -9);
//        threeAndABit = of(3_095_588_235_2L, (byte) -10);
//        expected = of(1_676_95L, (byte) -5);
//        actual = divide(fiveAndABit, threeAndABit);
//
//        outMsg = "5.191176470 / 3.0955882352 was " + STANDARD.format(actual) + " (" + actual + ") instead of " + STANDARD.format(expected);
//        assertTrue(outMsg, equals64(expected, actual));
        // 5.191176470588235 / 3.0955882352941175 == 1.676959619952494        
        fiveAndABit = of(5_191_176_470_588_235L, (byte) -15);
        threeAndABit = of(3_095_588_235_294_117_5L, (byte) -16);
        expected = of(1_676_959_619_952_494L, (byte) -15);
        // FIXME
        // FIXME The bug here is that 10 * coeff(fiveAndABit) > DEC64_MAX_COEFFICIENT so we get 0
        actual = divide(fiveAndABit, threeAndABit);

        outMsg = "5.191176470588235 / 3.0955882352941175 was " + STANDARD.format(actual) + " (" + actual + ") instead of " + STANDARD.format(expected);
        assertTrue(equals64(expected, actual), outMsg);
    }

    @Test
    public void simpleEquals() {
        // This test case only exists for demonstration purposes for newcomers
        // to working witht the DEC64 format
        assertTrue(equals64(256, 2815), "1 should equal 1");
        assertTrue(equals64(2815, 256), "1 should equal 1");
        assertTrue(equals64(256, 25854), "1 should equal 1");
        assertTrue(equals64(25854, 256), "1 should equal 1");
        assertTrue(equals64(512, 512253), "2 should equal 2");
        assertTrue(equals64(512253, 512), "2 should equal 2");
        assertTrue(equals64(512, 5120252), "2 should equal 2");
        assertTrue(equals64(5120252, 512), "2 should equal 2");
        assertTrue(equals64(512, 51200251), "2 should equal 2");
        assertTrue(equals64(51200251, 512), "2 should equal 2");
        assertTrue(equals64(512, 512_000_250), "2 should equal 2");
        assertTrue(equals64(512_000_250, 512), "2 should equal 2");
    }

    @Test
    public void genEquals() {
        @DEC64 long last = 256;
        byte exp = 0;
        for (int i = 0; i < 16; i++) {
            last *= 10;
            exp--;
            @DEC64 long check = last + exponentAsLong(exp);
            assertTrue(equals64(DEC64_ONE, check), "1_" + (-exp) + "(" + last + " + " + exp + ") should equal 1CL but is: " + check);
            assertTrue( equals64(check, DEC64_ONE), "1_" + (-exp) + " should equal 1CL but is: " + check);

            @DEC64 long canonical = canonical(check);
            assertTrue(DEC64_ONE == canonical, canonical + "(" + last + " + " + exp + ") should equal 1CL (" + DEC64_ONE + ") but is: " + canonical);
        }
    }

    @Test
    public void testIncDec() {
        assertTrue(isNaN(DEC64_NAN));

        assertTrue(equals64(DEC64_ZERO, inc(DEC64_NEGATIVE_ONE)), "0 = (-1)++");
        assertTrue(equals64(DEC64_ONE, inc(DEC64_ZERO)), "1 = (0)++");
        assertTrue(equals64(DEC64_TWO, inc(DEC64_ONE)), "2 = (1)++");
        assertTrue(equals64(dec(DEC64_TWO), DEC64_ONE), "1 = (2)--");
        assertTrue(equals64(dec(DEC64_ONE), DEC64_ZERO), "0 = (1)--");
        assertTrue(equals64(dec(DEC64_ZERO), DEC64_NEGATIVE_ONE), "-1 = (0)--");

        for (byte exp = 1; exp < Byte.MAX_VALUE; exp++) {
            @DEC64 long large = of(DEC64_MAX_COEFFICIENT, exp);
            assertTrue(equals64(inc(large), large), "inc on large number should return same number");
        }

        assertTrue(equals64(inc(of(35, (byte) -1)), of(45, (byte) -1)), "(3.5)++ = 4.5");
    }

    @Test
    public void simpleFactorial() {
        long current = of(1, 0);
        // FIXME - should do up to FACTORIAL.length 
        for (int i = 2; i < 19; i++) {
            current = multiply(of(i, 0), current);
            assertTrue(equals64(FACTORIAL[i], current), i + "! value incorrect");
        }
        assertEquals(3602879701896396L, DEC64_MAX_COEFFICIENT / 10L);
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
        String s = FormatMode.STANDARD.format(largeOne);
        assertEquals("1.0", s);
        @DEC64 long largerOne = 256_000_000_000_000_241L;
        s = FormatMode.STANDARD.format(largerOne);
        // FIXME
//        assertEquals("1.0", s);
    }

    @Test
    public void moduloTest() {
        assertTrue(equals64(modulo(FOUR, SEVEN), FOUR), "4 % 7 should equal 4");
//        assertTrue("7 % 2 should equal 1", equals64(modulo(SEVEN, DEC64_TWO), DEC64_ONE));
    }

    @Test
    public void simpleAbsolute() {
        assertTrue(equals64(abs(DEC64_ONE), DEC64_ONE), "abs value of 1 should equal 1");
        assertTrue(equals64(abs(DEC64_NEGATIVE_ONE), DEC64_ONE), "abs value of -1 should equal 1");
    }

    @Test
    public void negativeNumber() {
        assertTrue(equals64(neg(DEC64_NEGATIVE_ONE), DEC64_ONE), "neg value of -1 should equal 1");
//		assertTrue("neg value of 1 should equal -1", equals64(neg(DEC64_ONE), DEC64_NEGATIVE_ONE));
    }
    
    @Test
    public void test_Less() {        
        assertTrue(less(TestConstants.MINIMUM, TestConstants.MAXIMUM), "minimum should be less than maximum");
        assertTrue(less(Constants64.DEC64_ZERO, Constants64.DEC64_ONE), "0 should be less than 1");
        assertTrue(less(TestConstants.ALMOST_ONE, Constants64.DEC64_ONE), "0.9... should be less than 1");
        assertTrue(less(TestConstants.FOUR, TestConstants.SEVEN), "4 should be less than 7");
        assertTrue(less(Constants64.DEC64_ZERO, TestConstants.MINIMUM), "0 < minimum");
        assertTrue(less(TestConstants.CENT, Constants64.DEC64_HALF), "0.01 < 0.5");
        assertTrue(less(TestConstants.THREE, Constants64.DEC64_PI), "3 should be less than PI");
        
        assertFalse(less(TestConstants.MAXIMUM, TestConstants.MINIMUM), "maximum should not be less than minimum");
        assertFalse(less(Constants64.DEC64_ONE, Constants64.DEC64_NEGATIVE_ONE), "1 should not be less than -1");
        assertFalse(less(TestConstants.THREE, TestConstants.THREE), "3 should not be less than 3");
        assertFalse(less(TestConstants.EPSILON, TestConstants.MINIMUM), "epsilon should not be smaller than minimum");
        assertFalse(less(Constants64.DEC64_TWO, TestConstants.TWO_EPSILON), "2 should not be less than 2e-16");
        assertFalse(less(TestConstants.FOUR, Constants64.DEC64_PI), "4 should not be less than PI");
        assertFalse(less(TestConstants.FOUR, of(20, (byte) -128)), "illegal exponent should return false");
        assertFalse(less(TestConstants.FOUR, Constants64.DEC64_NAN), "Any nan value is greater than any number value");
    }
}
