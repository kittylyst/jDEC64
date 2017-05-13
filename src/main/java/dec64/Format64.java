package dec64;

import static dec64.Constants64.DEC64_ZERO;

/**
 *
 * @author ben
 */
public final class Format64 {

    private @DEC64
    long valid;
    private @DEC64
    long number;
    private StringBuilder data;
    private int[] digits;
    private int nr_digits;
    private int nr_zeros;
    private int places;
    private int separation;
//    private Mode mode;
    private char decimal_point;
    private char separator;

    private Format64() {
    }

    public static Format64 of() {
        return new Format64();
    }

    public static @DEC64
    long toDEC64(final String s) {
        return DEC64_ZERO;
    }

}
