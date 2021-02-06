package dec64;

import static dec64.Basic64.of;

import dec64.annotations.DEC64;

/** @author ben */
public class TestConstants {

  public static final @DEC64 long MINIMUM = of(1, (byte) -127);
  /** the smallest number addable to 1 */
  public static final @DEC64 long EPSILON = of(1, (byte) -16);

  public static final @DEC64 long NEGATIVE_EPSILON = of(-1, (byte) -16);
  public static final @DEC64 long TWO_EPSILON = of(2, (byte) -16);
  /** 0.01 */
  public static final @DEC64 long CENT = of(1, (byte) -2);

  public static final @DEC64 long ALMOST_ONE = of(9999999999999999L, (byte) -16);
  public static final @DEC64 long THREE = of(30, (byte) -1);
  public static final @DEC64 long FOUR = of(4, (byte) 0);
  public static final @DEC64 long FIVE = of(5000, (byte) -3);
  public static final @DEC64 long SIX = of(600, (byte) -2);
  public static final @DEC64 long SEVEN = of(7, (byte) 0);
  public static final @DEC64 long EIGHT = of(8000, (byte) -3);
  public static final @DEC64 long TEN = of(10, (byte) 0);
  public static final @DEC64 long MAXIMUM = of(36028797018963967L, (byte) 127);
}
