/*
 * The MIT License
 *
 * Copyright 2021 Ben Evans.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dec64;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

/**
 * A test file for the Basic64 class.
 * 
 * The following test methods are implemented, the rest are disabled for now:
 * testOf_long_byte()
 * @author Andy Turner
 */
public class Basic64Test {
    
    public Basic64Test() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of coefficient method, of class Basic64.
     */
    @Test
    @Disabled
    public void testCoefficient() {
        System.out.println("coefficient");
        long number = 0L;
        long expResult = 0L;
        long result = Basic64.coefficient(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exponent method, of class Basic64.
     */
    @Test
    @Disabled
    public void testExponent() {
        System.out.println("exponent");
        long number = 0L;
        byte expResult = 0;
        byte result = Basic64.exponent(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exponentAsLong method, of class Basic64.
     */
    @Test
    @Disabled
    public void testExponentAsLong() {
        System.out.println("exponentAsLong");
        byte exp = 0;
        long expResult = 0L;
        long result = Basic64.exponentAsLong(exp);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of overflow method, of class Basic64.
     */
    @Test
    @Disabled
    public void testOverflow() {
        System.out.println("overflow");
        long number = 0L;
        boolean expResult = false;
        boolean result = Basic64.overflow(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of digits method, of class Basic64.
     */
    @Test
    @Disabled
    public void testDigits() {
        System.out.println("digits");
        long number = 0L;
        byte expResult = 0;
        byte result = Basic64.digits(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of of method, of class Basic64.
     */
    @Test
    @Disabled
    public void testOf_long_long() {
        System.out.println("of");
        long coeff = 0L;
        long exponent = 0L;
        long expResult = 0L;
        long result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of of method, of class Basic64.
     */
    @Test
    public void testOf_long_byte() {
        System.out.println("of");
        long coeff = 0L;
        byte exponent = 0;
        long expResult = 0L;
        long result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 2
        coeff = 1L;
        exponent = 0;
        expResult = 256L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 3
        coeff = 0L;
        exponent = 1;
        expResult = 1L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 4
        coeff = 1L;
        exponent = 1;
        expResult = 257L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 5
        coeff = 2L;
        exponent = 0;
        expResult = 512L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 6
        coeff = 0L;
        exponent = 2;
        expResult = 2L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 7
        coeff = 1L;
        exponent = 2;
        expResult = 258L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 8
        coeff = 2L;
        exponent = 1;
        expResult = 513L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 9
        coeff = 2L;
        exponent = 2;
        expResult = 514L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 10
        coeff = -1;
        exponent = 0;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 11
        coeff = 0;
        exponent = -1;
        expResult = 255L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 12
        coeff = -1;
        exponent = -1;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 13
        coeff = -1;
        exponent = 1;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 14
        coeff = 1;
        exponent = -1;
        expResult = 511L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 15
        coeff = -2;
        exponent = -2;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 16
        coeff = -2;
        exponent = 1;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 17
        coeff = 1;
        exponent = -2;
        expResult = 510L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test some extremes
        // Test 18
        coeff = Long.MIN_VALUE;
        exponent = 0;
        expResult = 0L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 19
        coeff = Long.MAX_VALUE;
        exponent = 0;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 20
        coeff = Long.MIN_VALUE;
        exponent = 1;
        expResult = 1L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 21
        coeff = Long.MAX_VALUE;
        exponent = 1;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 22
        coeff = Long.MIN_VALUE;
        exponent = -1;
        expResult = 255L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 23
        coeff = Long.MAX_VALUE;
        exponent = -1;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 24
        coeff = Long.MIN_VALUE;
        exponent = 2;
        expResult = 2L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 25
        coeff = Long.MAX_VALUE;
        exponent = 2;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 26
        coeff = Long.MIN_VALUE;
        exponent = -2;
        expResult = 254L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 27
        coeff = Long.MAX_VALUE;
        exponent = -2;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 28
        coeff = 0L;
        exponent = Byte.MIN_VALUE;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 29
        coeff = 0L;
        exponent = Byte.MAX_VALUE;
        expResult = 127L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);        
        // Test 30
        coeff = 1L;
        exponent = Byte.MIN_VALUE;
        expResult = 384L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 31
        coeff = 1L;
        exponent = Byte.MAX_VALUE;
        expResult = 383L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 32
        coeff = -1L;
        exponent = Byte.MIN_VALUE;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 33
        coeff = -1L;
        exponent = Byte.MAX_VALUE;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 34
        coeff = 2L;
        exponent = Byte.MIN_VALUE;
        expResult = 640L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 35
        coeff = 2L;
        exponent = Byte.MAX_VALUE;
        expResult = 639L;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 36
        coeff = -2L;
        exponent = Byte.MIN_VALUE;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
        // Test 37
        coeff = -2L;
        exponent = Byte.MAX_VALUE;
        expResult = Constants64.DEC64_NAN;
        result = Basic64.of(coeff, exponent);
        assertEquals(expResult, result);
        assertTrue(expResult == result);
    }

    /**
     * Test of level method, of class Basic64.
     */
    @Test
    @Disabled
    public void testLevel() {
        System.out.println("level");
        long number = 0L;
        long expResult = 0L;
        long result = Basic64.level(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reduceExponent method, of class Basic64.
     */
    @Test
    @Disabled
    public void testReduceExponent() {
        System.out.println("reduceExponent");
        long number = 0L;
        long expResult = 0L;
        long result = Basic64.reduceExponent(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of canonical method, of class Basic64.
     */
    @Test
    @Disabled
    public void testCanonical() {
        System.out.println("canonical");
        long number = 0L;
        long expResult = 0L;
        long result = Basic64.canonical(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isNaN method, of class Basic64.
     */
    @Test
    @Disabled
    public void testIsNaN() {
        System.out.println("isNaN");
        long number = 0L;
        boolean expResult = false;
        boolean result = Basic64.isNaN(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isInteger method, of class Basic64.
     */
    @Test
    @Disabled
    public void testIsInteger() {
        System.out.println("isInteger");
        long number = 0L;
        boolean expResult = false;
        boolean result = Basic64.isInteger(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isBasic method, of class Basic64.
     */
    @Test
    @Disabled
    public void testIsBasic() {
        System.out.println("isBasic");
        long number = 0L;
        boolean expResult = false;
        boolean result = Basic64.isBasic(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isZero method, of class Basic64.
     */
    @Test
    @Disabled
    public void testIsZero() {
        System.out.println("isZero");
        long number = 0L;
        boolean expResult = false;
        boolean result = Basic64.isZero(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals64 method, of class Basic64.
     */
    @Test
    @Disabled
    public void testEquals64() {
        System.out.println("equals64");
        long a = 0L;
        long b = 0L;
        boolean expResult = false;
        boolean result = Basic64.equals64(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class Basic64.
     */
    @Test
    @Disabled
    public void testAdd() {
        System.out.println("add");
        long a = 0L;
        long b = 0L;
        long expResult = 0L;
        long result = Basic64.add(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of subtract method, of class Basic64.
     */
    @Test
    @Disabled
    public void testSubtract() {
        System.out.println("subtract");
        long a = 0L;
        long b = 0L;
        long expResult = 0L;
        long result = Basic64.subtract(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of multiply method, of class Basic64.
     */
    @Test
    @Disabled
    public void testMultiply() {
        System.out.println("multiply");
        long a = 0L;
        long b = 0L;
        long expResult = 0L;
        long result = Basic64.multiply(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of divide method, of class Basic64.
     */
    @Test
    @Disabled
    public void testDivide() {
        System.out.println("divide");
        long a = 0L;
        long b = 0L;
        long expResult = 0L;
        long result = Basic64.divide(a, b);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of reciprocal method, of class Basic64.
     */
    @Test
    @Disabled
    public void testReciprocal() {
        System.out.println("reciprocal");
        long r = 0L;
        long expResult = 0L;
        long result = Basic64.reciprocal(r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of abs method, of class Basic64.
     */
    @Test
    @Disabled
    public void testAbs() {
        System.out.println("abs");
        long number = 0L;
        long expResult = 0L;
        long result = Basic64.abs(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dec method, of class Basic64.
     */
    @Test
    @Disabled
    public void testDec() {
        System.out.println("dec");
        long minuend = 0L;
        long expResult = 0L;
        long result = Basic64.dec(minuend);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of half method, of class Basic64.
     */
    @Test
    @Disabled
    public void testHalf() {
        System.out.println("half");
        long dividend = 0L;
        long expResult = 0L;
        long result = Basic64.half(dividend);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inc method, of class Basic64.
     */
    @Test
    @Disabled
    public void testInc() {
        System.out.println("inc");
        long augend = 0L;
        long expResult = 0L;
        long result = Basic64.inc(augend);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modulo method, of class Basic64.
     */
    @Test
    @Disabled
    public void testModulo() {
        System.out.println("modulo");
        long dividend = 0L;
        long divisor = 0L;
        long expResult = 0L;
        long result = Basic64.modulo(dividend, divisor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of neg method, of class Basic64.
     */
    @Test
    @Disabled
    public void testNeg() {
        System.out.println("neg");
        long number = 0L;
        long expResult = 0L;
        long result = Basic64.neg(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of normal method, of class Basic64.
     */
    @Test
    @Disabled
    public void testNormal() {
        System.out.println("normal");
        long number = 0L;
        long expResult = 0L;
        long result = Basic64.normal(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of not method, of class Basic64.
     */
    @Test
    @Disabled
    public void testNot() {
        System.out.println("not");
        long value = 0L;
        long expResult = 0L;
        long result = Basic64.not(value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of round method, of class Basic64.
     */
    @Test
    @Disabled
    public void testRound() {
        System.out.println("round");
        long number = 0L;
        long place = 0L;
        long expResult = 0L;
        long result = Basic64.round(number, place);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of signum method, of class Basic64.
     */
    @Test
    @Disabled
    public void testSignum() {
        System.out.println("signum");
        long number = 0L;
        long expResult = 0L;
        long result = Basic64.signum(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of floor method, of class Basic64.
     */
    @Test
    @Disabled
    public void testFloor() {
        System.out.println("floor");
        long dividend = 0L;
        long expResult = 0L;
        long result = Basic64.floor(dividend);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of integer_divide method, of class Basic64.
     */
    @Test
    @Disabled
    public void testInteger_divide() {
        System.out.println("integer_divide");
        long dividend = 0L;
        long divisor = 0L;
        long expResult = 0L;
        long result = Basic64.integer_divide(dividend, divisor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ceiling method, of class Basic64.
     */
    @Test
    @Disabled
    public void testCeiling() {
        System.out.println("ceiling");
        long number = 0L;
        long expResult = 0L;
        long result = Basic64.ceiling(number);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of less method, of class Basic64.
     */
    @Test
    @Disabled
    public void testLess() {
        System.out.println("less");
        long x = 0L;
        long y = 0L;
        boolean expResult = false;
        boolean result = Basic64.less(x, y);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
