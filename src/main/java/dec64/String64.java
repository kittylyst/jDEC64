package dec64;

/**
 *
 * @author ben
 */
public final class String64 {

    private @DEC64
    long valid;
    private @DEC64
    long number;
    private String data;
    private int[] digits;
    private int length;
    private int nr_digits;
    private int nr_zeros;
    private int places;
    private int separation;
    private Mode mode;
    private char decimal_point;
    private char separator;

    private String64() {
    }

    public enum Mode {

        engineering_mode,
        scientific_mode,
        standard_mode
    };

    public static String64 of() {
        return new String64();
    }

    public static @DEC64
    long toDEC64(final String s) {
        return Math64.DEC64_ZERO;
    }
}
