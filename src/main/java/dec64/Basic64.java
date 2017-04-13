package dec64;

/**
 *
 * @author ben
 */
public final class Basic64 {

    public final static long DEC64_NAN = 0x80L;
    public final static long DEC64_ZERO = 0x00L;
    public final static long DEC64_ONE = 0x100L;

    public final static long DEC64_NEGATIVE_ONE = 0xFFFFFFFFFFFFFF00L;
    public final static long DEC64_POINT_ONE = 0x1FFL;

    private final static long DEC64_EXPONENT_MASK = 0xFFL;
    private final static long DEC64_COEFFICIENT_MASK = 0xFFFFFFFFFFFFFF00L;

    private final static long DEC64_COEFFICIENT_OVERFLOW_MASK = 0x7F00_0000_0000_0000L;

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

    public static boolean overflow(long number) {
        return (number & DEC64_COEFFICIENT_OVERFLOW_MASK) != 0;
    }

    public static @DEC64
    long of(long coeff, long exponent) {
        if (exponent > 127 || exponent < -127) {
            if (overflow(coeff))
                return salvageBoth(coeff, exponent);
            else
                return salvageExp(coeff, exponent);
        }
        return of(coeff, (byte) exponent);
    }

    public static @DEC64
    long of(long coeff, byte exponent) {
        if (overflow(coeff))
            return salvageCoeff(coeff, exponent);
        return (coeff << 8) | (DEC64_EXPONENT_MASK & (long) exponent);
    }

    public static @DEC64
    long reduceExponent(@DEC64 long number) {
        return of(10 * coefficient(number), (byte) (exponent(number) - 1));
    }

    public static boolean isNaN(@DEC64 long number) {
        return exponent(number) == DEC64_NAN;
    }

    public static boolean isInteger(@DEC64 long number) {
        return exponent(number) < 0;
    }

    public static boolean isZero(@DEC64 long number) {
        return coefficient(number) == DEC64_ZERO;
    }

    static long salvageCoeff(long coeff, byte expa) {
        return DEC64_NAN;
    }

    static long salvageExp(long coeff, long expa) {
        if (expa > 127) {
            long tmpc = coeff;
            long places = expa - 127;
            for (int i = 0; i < places; i++) {
                tmpc *= 10;
                if (overflow(tmpc << 8))
                    return DEC64_NAN;
            }
            return of(tmpc, 127);
        }

        return DEC64_NAN;
    }

    static long salvageBoth(long coeff, long expa) {
        // FIXME Is this worth it?
        return DEC64_NAN;
    }

    public static boolean equals64(@DEC64 long a, @DEC64 long b) {
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
            return divideEven(a, b);
        }
        long outMult = of(1, exponent(a) - exponent(b));

        return multiply(divideEven(a, b), outMult);
    }

    static @DEC64
    long divideEven(@DEC64 long a, @DEC64 long b) {
        final long exp = exponent(a);
        long coeffa = coefficient(a);
        long coeffb = coefficient(b);

        return DEC64_NAN;
    }

    public static long less(@DEC64 long comparahend, @DEC64 long comparator) {
        return 0;
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
        return 0;
    }/* decrementation */


    public static @DEC64
    long floor(@DEC64 long dividend) {
        return 0;
    }/* integer */


    public static @DEC64
    long half(@DEC64 long dividend) {
        return 0;
    }/* quotient */


    public static @DEC64
    long inc(@DEC64 long augend) {
        return 0;
    }/* incrementation */

//    public static long
//
//    int (long number) {
//    }/* integer */

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
        // FIXME
        return 0;
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
