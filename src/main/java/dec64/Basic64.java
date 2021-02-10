package dec64;

import static dec64.Constants64.*;
import static java.lang.Math.min;

import dec64.annotations.DEC64;

/** @author kittylyst */
public final class Basic64 {

  // Max and min coefficients - not DEC64 values - should not be needed outside the library
  static final long DEC64_MAX_COEFFICIENT = 0x7f_ffff_ffff_ffffL; // 36_028_797_018_963_967L
  static final long DEC64_MIN_COEFFICIENT = -36_028_797_018_963_968L; // -0x80_0000_0000_0000L

  private static final long DEC64_EXPONENT_MASK = 0xFFL;
  private static final long DEC64_COEFFICIENT_MASK = 0xffff_ffff_ffff_ff00L;

  private static final long DEC64_COEFFICIENT_OVERFLOW_MASK = 0x7F00_0000_0000_0000L;

  private static final byte MAX_DIGITS = 17;

  /** This value should not be used as an exponent. */
  private static final byte ILLEGAL_EXPONENT = Byte.MIN_VALUE;

  private Basic64() {}

  /**
   * Returns the coefficient of a DEC64 number as a long. The value will be 56 bits long and
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

  public static long exponentAsLong(byte exp) {
    return ((long) exp & DEC64_EXPONENT_MASK);
  }

  public static boolean overflow(long number) {
    return (number & DEC64_COEFFICIENT_OVERFLOW_MASK) != 0;
  }

  public static byte digits(@DEC64 long number) {
    if (isNaN(number)) {
      return -1;
    }
    if (isZero(number)) {
      return 0;
    }
    byte out = 0;
    long coeff = coefficient(abs(number));
    while (coeff > 0) {
      coeff = coeff / 10L;
      out++;
    }
    return out;
  }

  public static @DEC64 long of(long coeff, long exponent) {
    if (exponent > 127 || exponent < -127) {
      return DEC64_NAN;
    }
    return of(coeff, (byte) exponent);
  }

  public static @DEC64 long of(long coeff, byte exponent) {
    if (overflow(coeff)) {
      return DEC64_NAN;
    }
    return (coeff << 8) | exponentAsLong(exponent);
  }

  public static @DEC64 long level(@DEC64 long number) {
    return number & DEC64_COEFFICIENT_MASK;
  }

  public static @DEC64 long reduceExponent(@DEC64 long number) {
    return of(10 * coefficient(number), (byte) (exponent(number) - 1));
  }

  public static @DEC64 long canonical(@DEC64 long number) {
    if (isNaN(number)) {
      return DEC64_NAN;
    }
    byte exp = exponent(number);
    if (exp == 127 || exp == 0) {
      return number;
    }

    long out = number;
    long coeff = coefficient(number);
    if (exp > 0) {
      while (exp > 0 && coeff < DEC64_MAX_COEFFICIENT) {
        out = of(10 * coeff, --exp);
        coeff = coefficient(out);
      }
    } else {
      while (exp < 0 && coeff % 10 == 0) {
        out = of(coeff / 10L, ++exp);
        coeff = coefficient(out);
      }
    }

    return out;
  }

  public static boolean isNaN(@DEC64 long number) {
    return (DEC64_EXPONENT_MASK & (long) exponent(number)) == DEC64_NAN;
  }

  public static boolean isInteger(@DEC64 long number) {
    return exponent(number) < 0;
  }

  public static boolean isBasic(@DEC64 long number) {
    return exponent(number) == 0;
  }

  public static boolean isZero(@DEC64 long number) {
    return coefficient(number) == DEC64_ZERO;
  }

  public static boolean equals64(@DEC64 long a, @DEC64 long b) {
    if (isNaN(a) || isNaN(b)) {
      return false; // NaN != NaN
    }
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

  public static @DEC64 long add(@DEC64 long a, @DEC64 long b) {
    if (isNaN(a) || isNaN(b)) {
      return DEC64_NAN;
    }
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

  public static @DEC64 long subtract(@DEC64 long a, @DEC64 long b) {
    if (isNaN(a) || isNaN(b)) {
      return DEC64_NAN;
    }
    a = canonical(a);
    b = canonical(b);
    byte expa = exponent(a);
    byte expb = exponent(b);
    if (expa == expb) {
      long coeff = coefficient(a) - coefficient(b);
      if (overflow(coeff)) {
        return DEC64_NAN;
      }
      return of(coeff, expa);
    }
    if (expa > expb) {
      long coeffa = coefficient(a);
      for (int i = 0; i < (expa - expb); i++) {
        // FIXME Implement overflow case
        coeffa *= 10;
      }
      return of(coeffa - coefficient(b), expb);
    } else {
      long coeffb = coefficient(b);
      for (int i = 0; i < (expb - expa); i++) {
        // FIXME Implement overflow case
        coeffb *= 10;
      }
      return of(coeffb - coefficient(a), expa);
    }
  }

  public static @DEC64 long multiply(@DEC64 long a, @DEC64 long b) {
    if (isNaN(a) || isNaN(b)) {
      return DEC64_NAN;
    }
    final long coeff = coefficient(a) * coefficient(b);
    if (overflow(coeff)) {
      return DEC64_NAN;
    }
    return of(coeff, (byte) (exponent(a) + exponent(b)));
  }

  /**
   * @param a
   * @param b
   * @return
   */
  public static @DEC64 long divide(@DEC64 long a, @DEC64 long b) {
    if (isNaN(a) || isNaN(b)) {
      return DEC64_NAN;
    }
    if (coefficient(b) == 0) {
      return DEC64_NAN;
    }

    @DEC64 long recip = reciprocal(of(coefficient(b), 0));

    return multiply(of(coefficient(a), exponent(a) - exponent(b)), recip);
  }

  public static @DEC64 long reciprocal(@DEC64 long r) {
    if (isNaN(r) || coefficient(r) == 0) {
      return DEC64_NAN;
    }

    byte digits = digits(r);
    if (digits < 1) {
      return DEC64_NAN;
    }

    byte exp = exponent(r);
    long coeff = coefficient(r);
    long numerator = 1L;
    for (byte b = 0; b < digits - 1; b++) {
      numerator *= 10;
    }

    long ratio = numerator / coeff;
    long remainder = numerator % coeff;
    long outCoeff = ratio;

    MAIN:
    while (remainder > 0) {
      while (remainder < coeff) {
        if (outCoeff * 10 > DEC64_MAX_COEFFICIENT) {
          break MAIN;
        }
        outCoeff *= 10;
        remainder *= 10;
        exp++; // ????
      }
      ratio = remainder / coeff;
      remainder = remainder % coeff;

      outCoeff += ratio;
    }

    return of(outCoeff, -(exp + digits - 1));
  }

  public static @DEC64 long abs(@DEC64 long number) {
    if (isNaN(number)) {
      return DEC64_NAN;
    }
    @DEC64 long coeff = coefficient(number);
    if (coeff >= 0) {
      return number;
    }
    if (coeff > DEC64_MIN_COEFFICIENT) {
      return of(-coeff, exponent(number));
    }
    return DEC64_NAN;
  }

  public static @DEC64 long dec(@DEC64 long minuend) {
    minuend = canonical(minuend);
    if (isBasic(minuend)) {
      return minuend - DEC64_ONE;
    }
    byte exp = exponent(minuend);
    if (exp > 0) {
      return minuend;
    }
    return subtract(minuend, DEC64_ONE);
  }

  /* decrementation */
  public static @DEC64 long half(@DEC64 long dividend) {
    if (isNaN(dividend)) {
      return DEC64_NAN;
    }
    long coeff = coefficient(dividend);
    byte exp = exponent(dividend);
    // FIXME If coeff is large, this might not work
    if ((coeff & 1L) == 0L) {
      return of(coeff / 2L, exp);
    }
    return of(coeff * 5, --exp);
  }

  /* quotient */
  public static @DEC64 long inc(@DEC64 long augend) {
    augend = canonical(augend);
    if (isBasic(augend)) {
      return augend + DEC64_ONE;
    }
    byte exp = exponent(augend);
    if (exp > 0) {
      return augend;
    }

    return add(augend, DEC64_ONE);
  }

  public static @DEC64 long modulo(@DEC64 long dividend, @DEC64 long divisor) {
    if (coefficient(divisor) == 0) {
      return DEC64_NAN;
    }

    // Modulo. It produces the same result as
    return subtract(dividend, multiply(integer_divide(dividend, divisor), divisor));
  }

  /* modulation */
  public static @DEC64 long neg(@DEC64 long number) {
    if (isNaN(number)) {
      return DEC64_NAN;
    }
    @DEC64 long coeff = coefficient(number);
    if (coeff > DEC64_MIN_COEFFICIENT) {
      return of(-coeff, exponent(number));
    }
    return DEC64_NAN;
  }

  ////////////////////////////////////////////////////////
  public static long normal(long number) {
    return DEC64_NAN;
  }

  /* normalization */
  public static long not(long value) {
    return DEC64_NAN;
  }

  /* notation */
  public static long round(long number, long place) {
    return 0;
  }

  /* quantization */
  public static long signum(long number) {
    return DEC64_NAN;
  }

  /* signature */
  public static @DEC64 long ceiling(@DEC64 long number) {
    return largestInt(number, 1);
  }

  public static @DEC64 long floor(@DEC64 long number) {
    return largestInt(number, -1);
  }

  /**
   * Produces the largest integer that is less than or equal to 'number' (roundDir == -1) or greater
   * than or equal to 'number' (roundDir == 1). In the result, the exponent will be greater than or
   * equal to zero unless it is nan. Numbers with positive exponents will not be modified, even if
   * the numbers are outside of the safe integer range.
   *
   * @param number Dec64 number
   * @param roundDir rounding direction
   * @return rounded number
   */
  private static @DEC64 long largestInt(@DEC64 long number, int roundDir) {
    if (isNaN(number)) {
      return DEC64_NAN;
    }
    int e = exponent(number);
    @DEC64 long x = number >> 8;
    if (x == 0) {
      return 0;
    }
    if (e >= 0) {
      return number;
    }

    e = -e;
    long rem;
    if (e < 17) {
      double p = Math.pow(10, e);
      long scaled = (long) (x / p);
      rem = (long) (x - scaled * p);
      if (rem == 0) {
        return scaled << 8;
      }
      x = scaled;
    } else {
      // deal with a micro number
      rem = x;
      x = 0;
    }
    int multiplier = ((rem ^ roundDir) >= 0) ? 1 : 0;
    int delta = multiplier * roundDir;
    return (x + delta) << 8;
  }

  public static @DEC64 long integer_divide(@DEC64 long dividend, @DEC64 long divisor) {
    if (coefficient(divisor) == 0) {
      return DEC64_NAN;
    }
    // FIXME
    return 0;
  }

  /* quotient */
  /**
   * Compare two dec64 numbers. If the first is less than the second, return true, otherwise return
   * false. Any nan value is greater than any number value.
   *
   * @param x left hand number
   * @param y right hand number
   * @return boolean
   */
  public static boolean less(@DEC64 long x, @DEC64 long y) {
    byte ex = exponent(x);
    byte ey = exponent(y);

    // If the exponents are the same, then do a simple compare.
    if (ex == ey) {
      return ex != ILLEGAL_EXPONENT && (coefficient(x) < coefficient(y));
    }

    if (isNaN(ex) || isNaN(ey)) {
      return false;
    }

    x = x >> 8;
    y = y >> 8;

    int ediff = ex - ey;
    if (ediff > 0) {
      // make them conform before compare
      long xScaled = scale(x, ediff);

      @DEC64 long x_high = xScaled >> 64;
      // in the case of overflow check the sign of higher 64-bit half;
      // otherwise compare numbers with equalized exponents
      return (x_high == xScaled) ? xScaled < y : x_high < 0;
    } else {
      long yScaled = scale(y, -ediff);

      @DEC64 long y_high = yScaled >> 64;
      return (y_high == yScaled) ? x < yScaled : y_high >= 0;
    }
  }

  // Multiply coefficient by 10^(first exponent - second exponent)
  private static long scale(long coeff, int ediff) {
    // maximum coefficient is 36028797018963967. 10^18 is more
    int exp = min(ediff, 18);
    return (long) (coeff * Math.pow(10, exp));
  }
}
