package dec64;

import static dec64.Constants64.*;

/**
 *
 * @author ben
 */
public final class Basic64 {

    // Max and min coefficients - not DEC64 values - should not be neded outside the library
    final static long MAX_PROMOTABLE = 0x7f_ffff_ffff_ffffL; // 36028797018963967L
    final static long MIN_PROMOTABLE = 0xff_ffff_ffff_ffffL;

    private final static long DEC64_EXPONENT_MASK = 0xFFL;
    private final static long DEC64_COEFFICIENT_MASK = 0xffff_ffff_ffff_ff00L;

    private final static long DEC64_COEFFICIENT_OVERFLOW_MASK = 0x7F00_0000_0000_0000L;
    private final static byte MAX_DIGITS = 17;

    private Basic64() {
    }

    /**
     *
     * @param number
     * @return 
     */
    public static long coefficient(@DEC64 long number) {
        return number > 0 ? number >> 8 : -(-number >> 8);
    }

    public static byte exponent(@DEC64 long number) {
        return (byte) (number & DEC64_EXPONENT_MASK);
    }

    public static long exponentAsLong(byte exp) {
        return ((long) exp & DEC64_EXPONENT_MASK);
    }

    public static boolean overflow(long number) {
        return (number & DEC64_COEFFICIENT_OVERFLOW_MASK) != 0;
    }

    public static @DEC64
    long of(long coeff, long exponent) {
        if (exponent > 127 || exponent < -127)
            return DEC64_NAN;
        return of(coeff, (byte) exponent);
    }

    public static @DEC64
    long of(long coeff, byte exponent) {
        if (overflow(coeff))
            return DEC64_NAN;
        return (coeff << 8) | exponentAsLong(exponent);
    }

    public static @DEC64
    long reduceExponent(@DEC64 long number) {
        return of(10 * coefficient(number), (byte) (exponent(number) - 1));
    }

    public static @DEC64
    long canonical(@DEC64 long number) {
        if (isNaN(number))
            return DEC64_NAN;
        byte exp = exponent(number);
        if (exp == 127 || exp == 0)
            return number;

        long out = number;
        long coeff = coefficient(number);
        if (exp > 0) {
            while (exp > 0 && coeff < MAX_PROMOTABLE) {
                out = of(10 * coeff, --exp);
                coeff = coefficient(out);
                
            }
        } else {
            while (exp < 0 && coeff % 10 == 0) {
                out = of(coeff / 10L, ++exp);
                coeff = coefficient(out);
            }
        }

        return out;
    }

    public static boolean isNaN(@DEC64 long number) {
        return (DEC64_EXPONENT_MASK & (long) exponent(number)) == DEC64_NAN;
    }

    public static boolean isInteger(@DEC64 long number) {
        return exponent(number) < 0;
    }

    public static boolean isBasic(@DEC64 long number) {
        return exponent(number) == 0;
    }

    public static boolean isZero(@DEC64 long number) {
        return coefficient(number) == DEC64_ZERO;
    }

    public static boolean equals64(@DEC64 long a, @DEC64 long b) {
        if (isNaN(a) || isNaN(b))
            return false; // NaN != NaN
        byte expa = exponent(a);
        byte expb = exponent(b);
        if (expa == expb) {
            return coefficient(a) == coefficient(b);
        }

        // Slow path - first reduce the smaller exponent
        if (expa > expb) {
            @DEC64 long lastA = a;
            while (!isNaN(a)) {
                lastA = a;
                a = reduceExponent(a);
                if (exponent(a) == expb) {
                    return coefficient(a) == coefficient(b);
                }
            }
        } else {
            @DEC64 long lastB = b;
            while (!isNaN(b)) {
                lastB = b;
                b = reduceExponent(b);
                if (exponent(b) == expa) {
                    return coefficient(a) == coefficient(b);
                }
            }
        }

        return false;
    }

    public static @DEC64
    long add(@DEC64 long a, @DEC64 long b) {
        if (isNaN(a) || isNaN(b))
            return DEC64_NAN;
        byte expa = exponent(a);
        byte expb = exponent(b);
        if (expa == expb) {
            long coeff = coefficient(a) + coefficient(b);
            return of(coeff, expa);
        }

        // Slow path - first reduceExponent the smaller exponent
        if (expa > expb) {
            @DEC64 long lastA = a;
            while (!isNaN(a)) {
                lastA = a;
                a = reduceExponent(a);
                if (exponent(a) == expb) {
                    long coeff = coefficient(a) + coefficient(b);
                    return of(coeff, expb);
                }
            }
            // Have tried & failed to match by reducing a's exponent.
            // Now we must try to inflate b's exponent to match
            a = lastA;
        } else {
            @DEC64 long lastB = b;
            while (!isNaN(b)) {
                lastB = b;
                b = reduceExponent(b);
                if (exponent(b) == expa) {
                    long coeff = coefficient(a) + coefficient(b);
                    return of(coeff, expa);
                }
            }

        }

        return 0;
    }

    public static @DEC64
    long subtract(@DEC64 long a, @DEC64 long b) {
        if (isNaN(a) || isNaN(b))
            return DEC64_NAN;
        if (exponent(a) == exponent(b)) {
            long coeff = coefficient(a) - coefficient(b);
            if (overflow(coeff))
                return DEC64_NAN;
            return of(coeff, exponent(a));
        }

        return 0;
    }

    public static @DEC64
    long multiply(@DEC64 long a, @DEC64 long b) {
        if (isNaN(a) || isNaN(b))
            return DEC64_NAN;
        final long coeff = coefficient(a) * coefficient(b);
        if (overflow(coeff))
            return DEC64_NAN;
        return of(coeff, (byte) (exponent(a) + exponent(b)));
    }

    public static @DEC64
    long divide(@DEC64 long a, @DEC64 long b) {
        if (isNaN(a) || isNaN(b))
            return DEC64_NAN;
        if (coefficient(b) == 0)
            return DEC64_NAN;

        byte expa = exponent(a);
        byte expb = exponent(b);

        if (expa == expb) {
            return divideLevel(a, b);
        }
        long outMult = of(1, exponent(a) - exponent(b));

        return multiply(divideLevel(a, b), outMult);
    }

    /**
     * Divide two numbers that have the same exponent
     * 
     * @param a
     * @param b
     * @return 
     */
    static @DEC64
    long divideLevel(@DEC64 long a, @DEC64 long b) {
        long coeffa = coefficient(a);
        long coeffb = coefficient(b);
        byte exp = 0;

        long ratio = coeffa / coeffb;
        long remainder = coeffa % coeffb;
        while (remainder > 0) {
            if (coeffa * 10 > MAX_PROMOTABLE) {
                break;
            }
            coeffa *= 10;
            exp--;
            ratio = coeffa / coeffb;
            remainder = coeffa % coeffb;
        }

        return of(ratio, exp);
    }

    public static @DEC64
    long divide_translated(@DEC64 long r10, @DEC64 long r11) {
        byte r8 = exponent(r10);
        byte r9 = exponent(r11);

        if (isNaN(r10) || isNaN(r11) || isZero(r11))
            return DEC64_NAN;

//        // while
////divide_measure:
//// We want to get as many bits into the quotient as possible in order to capture
//// enough significance. But if the quotient has more than 64 bits, then there
//// will be a hardware fault. To avoid that, we compare the magnitudes of the
//// dividend coefficient and divisor coefficient, and use that to scale the
//// dividend to give us a good quotient.
//        long r0 = abs(r10);
//        long r1 = abs(r11);
//
//// //    bsr     r0,r0          //r0 is the dividend most significant bit
//// //    bsr     r1,r1          //r1 is the divisor most significant bit
//// Scale up the dividend to be approximately 58 bits longer than the divisor.
//// Scaling uses factors of 10, so we must convert from a bit count to a digit
//// count by multiplication by 77/256 (approximately LN2/LN10).
//        r1 += 58;          //we want approximately 58 bits in the raw quotient
//        r1 -= r0;          //r1 is the number of bits to add to the dividend
//        imul r1,
//        77          //multiply by 77/256 to convert bits to digits
//    shr r1,
//        8           //r1 is the number of digits to scale the dividend
//
//// The largest power of 10 that can be held in an int64 is 1e18.
//
//    cmp r1,
//        18          //prescale the dividend if 10**r1 won't fit
//    jg divide_prescale // Multiply the dividend by the scale factor, and divide that 128 bit result by
//                // the divisor.  Because of the scaling, the quotient is guaranteed to use most
//                // of the 64 bits in r0, and never more. Reduce the final exponent by the number
//                // of digits scaled.
//
//        mov r0, r10 //r0 is the dividend coefficient
//        mov r9, power
//        imul qword ptr[r9][r1 * 8]//r2:r0 is the dividend coefficient * 10**r1
//        idiv r11 //r0 is the quotient
//        sub r8, r1 //r8 is the exponent
//        jmp pack //pack it up
//        pad divide_prescale
//        :
//
//// If the number of scaling digits is larger than 18, then we will have to
//// scale in two steps: first prescaling the dividend to fill a register, and
//// then repeating to fill a second register. This happens when the divisor
//// coefficient is much larger than the dividend coefficient.
//
//    mov r1,
//        58          //we want 58 bits or so in the dividend
//    sub r1, r0 //r1 is the number of additional bits needed
//        imul r1,
//        77          //convert bits to digits
//    shr r1,
//        8           //shift 8 is cheaper than div 256
//    mov r9, power
//        imul r10, qword ptr[r9][r1 * 8]//multiply the dividend by power of ten
//        sub r8, r1 //reduce the exponent
//        jmp divide_measure //try again
        return DEC64_NAN;
    }

    public static boolean less(@DEC64 long comparahend, @DEC64 long comparator) {
        return false;
    }/* comparison */


    public static @DEC64
    long abs(@DEC64 long number) {
        return 0;
    }/* absolution */


    public static @DEC64
    long ceiling(@DEC64 long number) {
        return 0;
    }/* integer */


    public static @DEC64
    long dec(@DEC64 long minuend) {
        minuend = canonical(minuend);
        if (isBasic(minuend)) {
            return minuend - DEC64_ONE;
        }
        byte exp = exponent(minuend);
        if (exp > 0) {
            return minuend;
        }
        return subtract(minuend, DEC64_ONE);
    }/* decrementation */


    public static @DEC64
    long floor(@DEC64 long dividend) {
        return 0;
    }/* integer */


    public static @DEC64
    long half(@DEC64 long dividend) {
        if (isNaN(dividend))
            return DEC64_NAN;
        long coeff = coefficient(dividend);
        byte exp = exponent(dividend);
        if ((coeff & 1L) == 0L) {
            return of(coeff / 2L, exp);
        }
        return of(coeff * 5, --exp);
    }/* quotient */


    public static @DEC64
    long inc(@DEC64 long augend) {
        augend = canonical(augend);
        if (isBasic(augend)) {
            return augend + DEC64_ONE;
        }
        byte exp = exponent(augend);
        if (exp > 0) {
            return augend;
        }

        return add(augend, DEC64_ONE);
    }

    public static long integer_divide(@DEC64 long dividend, @DEC64 long divisor) {
        if (coefficient(divisor) == 0)
            return DEC64_NAN;
        // FIXME
        return 0;
    }/* quotient */


    public static @DEC64
    long modulo(@DEC64 long dividend, @DEC64 long divisor) {
        if (coefficient(divisor) == 0)
            return DEC64_NAN;

        // Modulo. It produces the same result as
        return subtract(dividend, multiply(integer_divide(dividend, divisor), divisor));
    }/* modulation */


    public static @DEC64
    long neg(@DEC64 long number) {
        return of(-coefficient(number), exponent(number));
    }

    ////////////////////////////////////////////////////////
    public static long normal(long number) {
        return 0;
    }/* normalization */


    public static long not(long value) {
        return 0;
    }/* notation */


    public static long round(long number, long place) {
        return 0;
    }/* quantization */


    public static long signum(long number) {
        return 0;
    }/* signature */

}
