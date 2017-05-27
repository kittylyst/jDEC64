package dec64;

import static dec64.Basic64.of;
import dec64.annotations.DEC64;

/**
 *
 * @author ben
 */
public class TestConstants {

    public static final @DEC64 long THREE = of(30, (byte) -1);
    public static final @DEC64 long FOUR = of(4, (byte) 0);
    public static final @DEC64 long FIVE = of(5000, (byte) -3);
    public static final @DEC64 long SIX = of(600, (byte) -2);
    public static final @DEC64 long SEVEN = of(7, (byte) 0);
    public static final @DEC64 long EIGHT = of(8000, (byte) -3);
    public static final @DEC64 long TEN = of(10, (byte) 0);

}
