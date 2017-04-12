package dec64;

/**
 *
 * @author ben
 */
public class String64 {

    public enum Mode {

        engineering_mode,
        scientific_mode,
        standard_mode
    };

    public static class dec64_string_state {
        /*
         For internal use only.
         */

        @DEC64
        long valid;
        @DEC64
        long number;
        String data;
        int[] digits;
        int length;
        int nr_digits;
        int nr_zeros;
        int places;
        int separation;
        Mode mode;
        char decimal_point;
        char separator;
    }

}
