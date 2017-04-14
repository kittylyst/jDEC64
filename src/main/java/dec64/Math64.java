package dec64;

import static dec64.Basic64.*;
import static dec64.Constants64.*;

/**
 *
 * @author ben
 */
public class Math64 {

    private Math64() {
    }

    public static @DEC64
    long asin64(@DEC64 long slope) {
        if (equals64(slope, DEC64_ONE)) {
            return DEC64_HALF_PI;
        }
        if (equals64(slope, DEC64_NEGATIVE_ONE)) {
            return DEC64_NHALF_PI;
        }
        if (isNaN(slope) || less(DEC64_ONE, abs(slope))) {
            return DEC64_NAN;
        }
        @DEC64 long bottom = DEC64_TWO;
        @DEC64 long factor = slope;
        @DEC64 long x2 = multiply(slope, slope);
        @DEC64 long result = factor;
        while (true) {
            factor = divide(multiply(multiply(dec(bottom), x2), factor), bottom);
            @DEC64 long progress = add( result, divide(factor, inc(bottom)) );
            if (result == progress) {
                break;
            }
            result = progress;
            bottom = add(bottom, DEC64_TWO);
        }
        return result;
    }

}
