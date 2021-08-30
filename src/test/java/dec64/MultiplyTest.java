package dec64;

import static dec64.Constants64.DEC64_NAN;
import static dec64.Constants64.DEC64_ONE;
import static dec64.Constants64.DEC64_TWO;
import static dec64.Constants64.DEC64_ZERO;
import static org.junit.jupiter.api.Assertions.*;

import dec64.annotations.DEC64;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MultiplyTest {
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
        Arguments.of(nan, nan, nan, "nan * nan"),
        Arguments.of(nan, zero, zero, "nan * zero"),
        Arguments.of(nannan, nannan, nan, "nannan * nannan"),
        Arguments.of(nannan, one, nan, "nannan * 1"),
        Arguments.of(zero, nan, zero, "0 * nan"),
        Arguments.of(zero, nannan, zero, "0 * nannan"),
        Arguments.of(zero, zip, zero, "zero * zip"),
        Arguments.of(zero, maxnum, zero, "zero * maxnum"),
        Arguments.of(zip, zero, zero, "zip * zero"),
        Arguments.of(zip, zip, zero, "zip * zip"),
        Arguments.of(minnum, half, minnum, "minnum * half"),
        Arguments.of(minnum, minnum, zero, "minnum * minnum"),
        Arguments.of(epsilon, epsilon, dec64_new(1, -32), "epsilon * epsilon"),
        Arguments.of(one, nannan, nan, "1 * nannan"),
        Arguments.of(negative_one, one, negative_one, "-1 * 1"),
        Arguments.of(negative_one, negative_one, one, "-1 * -1"),
        Arguments.of(two, five, ten, "2 * 5"),
        Arguments.of(two, maxnum, nan, "2 * maxnum"),
        Arguments.of(
            two,
            dec64_new(36028797018963967L, 126),
            dec64_new(7205759403792793L, 127),
            "2 * a big one"),
        Arguments.of(three, two, six, "3 * 2"),
        Arguments.of(ten, dec64_new(36028797018963967L, 126), maxnum, "10 * a big one"),
        Arguments.of(ten, dec64_new(1, 127), dec64_new(10, 127), "10 * 1e127"),
        Arguments.of(dec64_new(1, 2), dec64_new(1, 127), dec64_new(100, 127), "1e2 * 1e127"),
        Arguments.of(
            dec64_new(1, 12), dec64_new(1, 127), dec64_new(1000000000000L, 127), "1e2 * 1e127"),
        Arguments.of(
            dec64_new(1, 12), dec64_new(1, 127), dec64_new(1000000000000L, 127), "1e12 * 1e127"),
        Arguments.of(
            dec64_new(3, 16),
            dec64_new(1, 127),
            dec64_new(30000000000000000L, 127),
            "3e16 * 1e127"),
        Arguments.of(dec64_new(3, 17), dec64_new(1, 127), nan, "3e16 * 1e127"),
        Arguments.of(
            dec64_new(-3, 16),
            dec64_new(1, 127),
            dec64_new(-30000000000000000L, 127),
            "3e16 * 1e127"),
        Arguments.of(dec64_new(-3, 17), dec64_new(1, 127), nan, "3e16 * 1e127"),
        Arguments.of(
            dec64_new(9999999999999999L, 0),
            ten,
            dec64_new(9999999999999999L, 1),
            "9999999999999999 * 10"),
        Arguments.of(maxint, zero, zero, "maxint * zero"),
        Arguments.of(maxint, epsilon, dec64_new(36028797018963967L, -16), "maxint * epsilon"),
        Arguments.of(maxint, maxint, dec64_new(12980742146337068L, 17), "maxint * maxint"),
        Arguments.of(maxint, one_over_maxint, one, "maxint * 1 / maxint"),
        Arguments.of(negative_maxint, nan, nan, "-maxint * nan"),
        Arguments.of(
            negative_maxint, maxint, dec64_new(-12980742146337069L, 17), "-maxint * maxint"),
        Arguments.of(maxnum, maxnum, nan, "maxnum * maxnum"),
        Arguments.of(maxnum, minnum, maxint, "maxnum * minnum"));
  }

  @Disabled
  @ParameterizedTest
  @MethodSource("data")
  public void multiply(
      @DEC64 long first, @DEC64 long second, @DEC64 long expected, String comment) {
    @DEC64 long result = Basic64.multiply(first, second);
    System.out.println(
        String.format("first = 0x%x, second = 0x%x, result = 0x%x", first, second, result));
    assertEquals(expected, result, comment);
  }

  private static @DEC64 long dec64_new(@DEC64 long coeff, @DEC64 long exponent) {
    return Basic64.of(coeff, exponent);
  }
}
