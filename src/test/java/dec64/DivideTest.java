package dec64;

import static dec64.Constants64.*;
import static org.junit.jupiter.api.Assertions.*;

import dec64.annotations.DEC64;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DivideTest {
  @DEC64 private static final long nan = DEC64_NAN;
  @DEC64 private static final long nannan = 32896;
  @DEC64 private static final long zero = DEC64_ZERO;
  @DEC64 private static final long zip = 250;
  @DEC64 private static final long one = DEC64_ONE;
  @DEC64 private static final long two = DEC64_TWO;
  @DEC64 private static final long three = dec64_new(3, 0);
  @DEC64 private static final long four = dec64_new(4, 0);
  @DEC64 private static final long five = dec64_new(5, 0);
  @DEC64 private static final long six = dec64_new(6, 0);
  @DEC64 private static final long seven = dec64_new(7, 0);
  @DEC64 private static final long eight = dec64_new(8, 0);
  @DEC64 private static final long nine = dec64_new(9, 0);
  @DEC64 private static final long ten = dec64_new(10, 0);
  @DEC64 private static final long minnum = dec64_new(1, -127);
  @DEC64 private static final long maxnum = dec64_new(36028797018963967L, 127);
  @DEC64 private static final long half = dec64_new(5, -1);
  @DEC64 private static final long negative_nine = dec64_new(-9, 0);
  @DEC64 private static final long pi = dec64_new(31415926535897932L, -16);
  @DEC64 private static final long maxint = dec64_new(36028797018963967L, 0);
  @DEC64 private static final long negative_maxint = dec64_new(-36028797018963968L, 0);
  @DEC64 private static final long one_over_maxint = dec64_new(27755575615628914L, -33);
  @DEC64 private static final long negative_one = dec64_new(-1, 0);
  @DEC64 private static final long negative_pi = dec64_new(-31415926535897932L, -16);
  @DEC64 private static final long epsilon = dec64_new(1, -16);

  public static Stream<Arguments> data() {
    return Stream.of(
        Arguments.of(nannan, DEC64_TWO, nan, "nannan"),
        Arguments.of(nan, DEC64_TWO, nan, "nan"),
        Arguments.of(zero, DEC64_TWO, zero, "zero"),
        Arguments.of(zip, DEC64_TWO, zero, "zip"),
        Arguments.of(one, DEC64_TWO, half, "one"),
        Arguments.of(two, DEC64_TWO, one, "two"),
        Arguments.of(ten, DEC64_TWO, five, "ten"),
        Arguments.of(minnum, DEC64_TWO, minnum, "minnum"),
        Arguments.of(dec64_new(-2, 0), DEC64_TWO, dec64_new(-1, 0), "-2"),
        Arguments.of(dec64_new(-1, 0), DEC64_TWO, dec64_new(-5, -1), "-1"),
        Arguments.of(nan, nan, nan, "nan / nan"),
        Arguments.of(four, two, two, "4 / 2"),
        Arguments.of(six, two, three, "6 / 2"),
        Arguments.of(
            dec64_new(4195835, 0),
            dec64_new(3145727, 0),
            dec64_new(13338204491362410L, -16),
            "4195835 / 3145727"),
        Arguments.of(nan, three, nan, "nan / 3"),
        Arguments.of(nannan, nannan, nan, "nannan / nannan"),
        Arguments.of(nannan, one, nan, "nannan / 1"),
        Arguments.of(zero, nan, zero, "0 / nan"),
        Arguments.of(zero, nannan, zero, "0 / nannan"),
        Arguments.of(zero, zip, zero, "zero / zip"),
        Arguments.of(zip, nan, zero, "zip / nan"),
        Arguments.of(zip, nannan, zero, "zip / nannan"),
        Arguments.of(zip, zero, zero, "zip / zero"),
        Arguments.of(zip, zip, zero, "zip / zip"),
        Arguments.of(zero, one, zero, "0 / 1"),
        Arguments.of(zero, zero, zero, "0 / 0"),
        Arguments.of(one, zero, nan, "1 / 0"),
        Arguments.of(one, negative_one, dec64_new(-10000000000000000L, -16), "1 / -1"),
        Arguments.of(negative_one, one, dec64_new(-10000000000000000L, -16), "-1 / 1"),
        Arguments.of(one, two, dec64_new(5000000000000000L, -16), "1 / 2"),
        Arguments.of(one, three, dec64_new(33333333333333333L, -17), "1 / 3"),
        Arguments.of(two, three, dec64_new(6666666666666667L, -16), "2 / 3"),
        Arguments.of(
            two,
            dec64_new(30000000000000000L, -16),
            dec64_new(6666666666666667L, -16),
            "2 / 3 alias"),
        Arguments.of(
            dec64_new(20000000000000000L, -16),
            three,
            dec64_new(6666666666666667L, -16),
            "2 / 3 alias"),
        Arguments.of(
            dec64_new(20000000000000000L, -16),
            dec64_new(30000000000000000L, -16),
            dec64_new(6666666666666667L, -16),
            "2 / 3 alias"),
        Arguments.of(five, three, dec64_new(16666666666666667L, -16), "5 / 3"),
        Arguments.of(
            five,
            dec64_new(-30000000000000000L, -16),
            dec64_new(-16666666666666667L, -16),
            "5 / -3"),
        Arguments.of(
            dec64_new(-50000000000000000L, -16),
            three,
            dec64_new(-16666666666666667L, -16),
            "-5 / 3"),
        Arguments.of(
            dec64_new(-50000000000000000L, -16),
            dec64_new(-30000000000000000L, -16),
            dec64_new(16666666666666667L, -16),
            "-5 / -3"),
        Arguments.of(six, nan, nan, "6 / nan"),
        Arguments.of(six, three, dec64_new(20000000000000000L, -16), "6 / 3"),
        Arguments.of(zero, nine, zero, "0 / 9"),
        Arguments.of(one, nine, dec64_new(11111111111111111L, -17), "1 / 9"),
        Arguments.of(two, nine, dec64_new(22222222222222222L, -17), "2 / 9"),
        Arguments.of(three, nine, dec64_new(33333333333333333L, -17), "3 / 9"),
        Arguments.of(four, nine, dec64_new(4444444444444444L, -16), "4 / 9"),
        Arguments.of(five, nine, dec64_new(5555555555555556L, -16), "5 / 9"),
        Arguments.of(six, nine, dec64_new(6666666666666667L, -16), "6 / 9"),
        Arguments.of(seven, nine, dec64_new(7777777777777778L, -16), "7 / 9"),
        Arguments.of(eight, nine, dec64_new(8888888888888889L, -16), "8 / 9"),
        Arguments.of(nine, nine, one, "9 / 9"),
        Arguments.of(zero, negative_nine, zero, "0 / -9"),
        Arguments.of(one, negative_nine, dec64_new(-11111111111111111L, -17), "1 / -9"),
        Arguments.of(two, negative_nine, dec64_new(-22222222222222222L, -17), "2 / -9"),
        Arguments.of(three, negative_nine, dec64_new(-33333333333333333L, -17), "3 / -9"),
        Arguments.of(four, negative_nine, dec64_new(-4444444444444444L, -16), "4 / -9"),
        Arguments.of(five, negative_nine, dec64_new(-5555555555555556L, -16), "5 / -9"),
        Arguments.of(six, negative_nine, dec64_new(-6666666666666667L, -16), "6 / -9"),
        Arguments.of(seven, negative_nine, dec64_new(-7777777777777778L, -16), "7 / -9"),
        Arguments.of(eight, negative_nine, dec64_new(-8888888888888889L, -16), "8 / -9"),
        Arguments.of(nine, negative_nine, negative_one, "9 / -9"),
        Arguments.of(pi, negative_pi, dec64_new(-10000000000000000L, -16), "pi / -pi"),
        Arguments.of(negative_pi, pi, dec64_new(-10000000000000000L, -16), "-pi / pi"),
        Arguments.of(negative_pi, negative_pi, dec64_new(10000000000000000L, -16), "-pi / -pi"),
        Arguments.of(dec64_new(-16, 0), ten, dec64_new(-16, -1), "-16 / 10"),
        Arguments.of(maxint, epsilon, dec64_new(36028797018963967L, 16), "maxint / epsilon"),
        Arguments.of(one, maxint, one_over_maxint, "one / maxint"),
        Arguments.of(one, one_over_maxint, maxint, "one / one / maxint"),
        Arguments.of(one, negative_maxint, dec64_new(-27755575615628914L, -33), "one / -maxint"),
        Arguments.of(maxnum, epsilon, nan, "maxnum / epsilon"),
        Arguments.of(maxnum, maxnum, dec64_new(10000000000000000L, -16), "maxnum / maxnum"),
        Arguments.of(dec64_new(10, -1), maxint, one_over_maxint, "one / maxint alias 1"),
        Arguments.of(dec64_new(100, -2), maxint, one_over_maxint, "one / maxint alias 2"),
        Arguments.of(dec64_new(1000, -3), maxint, one_over_maxint, "one / maxint alias 3"),
        Arguments.of(dec64_new(10000, -4), maxint, one_over_maxint, "one / maxint alias 4"),
        Arguments.of(dec64_new(100000, -5), maxint, one_over_maxint, "one / maxint alias 5"),
        Arguments.of(dec64_new(1000000, -6), maxint, one_over_maxint, "one / maxint alias 6"),
        Arguments.of(dec64_new(10000000, -7), maxint, one_over_maxint, "one / maxint alias 7"),
        Arguments.of(dec64_new(100000000, -8), maxint, one_over_maxint, "one / maxint alias 8"),
        Arguments.of(dec64_new(1000000000, -9), maxint, one_over_maxint, "one / maxint alias 9"),
        Arguments.of(
            dec64_new(10000000000L, -10), maxint, one_over_maxint, "one / maxint alias 10"),
        Arguments.of(
            dec64_new(100000000000L, -11), maxint, one_over_maxint, "one / maxint alias 11"),
        Arguments.of(
            dec64_new(1000000000000L, -12), maxint, one_over_maxint, "one / maxint alias 12"),
        Arguments.of(
            dec64_new(10000000000000L, -13), maxint, one_over_maxint, "one / maxint alias 13"),
        Arguments.of(
            dec64_new(100000000000000L, -14), maxint, one_over_maxint, "one / maxint alias 14"),
        Arguments.of(
            dec64_new(1000000000000000L, -15), maxint, one_over_maxint, "one / maxint alias 15"),
        Arguments.of(
            dec64_new(10000000000000000L, -16), maxint, one_over_maxint, "one / maxint alias 16"),
        Arguments.of(minnum, two, minnum, "minnum / 2"),
        Arguments.of(one, 0x1437EEECD800000L, dec64_new(28114572543455208L, -31), "1/17!"),
        Arguments.of(one, 0x52D09F700003L, dec64_new(28114572543455208L, -31), "1/17!"));
  }

  @Disabled
  @ParameterizedTest
  @MethodSource("data")
  public void divide(@DEC64 long first, @DEC64 long second, @DEC64 long expected, String comment) {
    @DEC64 long result = Basic64.divide(first, second);
    System.out.println(
        String.format("first = 0x%x, second = 0x%x, result = 0x%x", first, second, result));
    assertEquals(expected, result, comment);
  }

  private static @DEC64 long dec64_new(@DEC64 long coeff, @DEC64 long exponent) {
    return Basic64.of(coeff, exponent);
  }
}
