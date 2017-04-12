package dec64;

/**
 *
 * @author ben
 */
public final class Math64 {

    public final static long DEC64_NAN = 0x80L;
    public final static long DEC64_ZERO = 0x00L;
    public final static long DEC64_ONE = 0x100L;
    public final static long DEC64_NEGATIVE_ONE = 0xFFFFFFFFFFFFFF00L;

    private final static long DEC64_EXPONENT_MASK = 0xFFL;
    private final static long DEC64_COEFFICIENT_MASK = 0xFFFFFFFFFFFFFF00L;

    private final static long DEC64_COEFFICIENT_OVERFLOW_MASK = 0xFF00_0000_0000_0000L;

//#define DEC64_TRUE          (0x380LL)
//#define DEC64_FALSE         (0x280LL)
    public static long coefficient(@DEC64 long number) {
        return number >>> 8;
    }

    public static long exponent(@DEC64 long number) {
        return number | DEC64_EXPONENT_MASK;
    }

    public static @DEC64 long of(long coefficient, long exponent) {
        if (exponent > 127 || exponent < -127) {
            throw new IllegalArgumentException("Exponent out of range: " + exponent);
        }
        return of(coefficient, (byte) exponent);
    }

    public static @DEC64
    long of(long coefficient, byte exponent) {
        return (coefficient << 8) | exponent;
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

    public static long less(@DEC64 long comparahend, @DEC64 long comparator) {
        return 0;
    }/* comparison */


    public static @DEC64
    long abs(@DEC64 long number) {
        return 0;
    }/* absolution */


    public static @DEC64
    long add(@DEC64 long augend, @DEC64 long addend) {
        return 0;
    }/* sum */


    public static @DEC64
    long ceiling(@DEC64 long number) {
        return 0;
    }/* integer */


    public static @DEC64
    long dec(@DEC64 long minuend) {
        return 0;
    }/* decrementation */


    public static @DEC64
    long divide(@DEC64 long dividend, @DEC64 long divisor) {
        if (coefficient(divisor) == 0)
            return DEC64_NAN;
        // FIXME

        return 0;
    }/* quotient */


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
    long multiply(@DEC64 long a, @DEC64 long b) {
        final long coeff = coefficient(a) * coefficient(b);
        if ((coeff | DEC64_COEFFICIENT_OVERFLOW_MASK) != 0)
            return DEC64_NAN;
        return of(coeff, exponent(a) + exponent(b));
    }

    public static @DEC64
    long neg(@DEC64 long number) {
        return of(-coefficient(number), exponent(number));
    }

    public static @DEC64
    long subtract(@DEC64 long minuend, @DEC64 long subtrahend) {
        return 0;
    }/* difference */

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
