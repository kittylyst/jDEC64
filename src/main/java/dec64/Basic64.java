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

  // numbers are not allowed to use this exponent
  private static final byte ILLEGAL_EXPO = -128;

  private static long power[] = {
    1,
    10,
    100,
    1_000,
    10_000,
    100_000,
    1_000_000,
    10_000_000,
    100_000_000,
    1_000_000_000,
    10_000_000_000L,
    100_000_000_000L,
    1_000_000_000_000L,
    10_000_000_000_000L,
    100_000_000_000_000L,
    1_000_000_000_000_000L,
    10000000000000000L,
    100000000000000000L,
    1000000000000000000L,
    //          (int64)10000000000000000000ULL,
    0
  };

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
    if (exponent > 127 || exponent < -127) return DEC64_NAN;
    return of(coeff, (byte) exponent);
  }

  public static @DEC64 long of(long coeff, byte exponent) {
    if (overflow(coeff)) return DEC64_NAN;
    return (coeff << 8) | exponentAsLong(exponent);
  }

  public static @DEC64 long level(@DEC64 long number) {
    return number & DEC64_COEFFICIENT_MASK;
  }

  public static @DEC64 long reduceExponent(@DEC64 long number) {
    return of(10 * coefficient(number), (byte) (exponent(number) - 1));
  }

  public static @DEC64 long canonical(@DEC64 long number) {
    if (isNaN(number)) return DEC64_NAN;
    byte exp = exponent(number);
    if (exp == 127 || exp == 0) return number;

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
    if (isNaN(a) || isNaN(b)) return false; // NaN != NaN
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
    if (isNaN(a) || isNaN(b)) return DEC64_NAN;
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
    if (isNaN(a) || isNaN(b)) return DEC64_NAN;
    a = canonical(a);
    b = canonical(b);
    byte expa = exponent(a);
    byte expb = exponent(b);
    if (expa == expb) {
      long coeff = coefficient(a) - coefficient(b);
      if (overflow(coeff)) return DEC64_NAN;
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
    if (isNaN(a) || isNaN(b)) return DEC64_NAN;
    final long coeff = coefficient(a) * coefficient(b);
    if (overflow(coeff)) return DEC64_NAN;
    return of(coeff, (byte) (exponent(a) + exponent(b)));
  }

  public static @DEC64 long divide_naive(@DEC64 long a, @DEC64 long b) {
    if (isNaN(a) || isNaN(b)) return DEC64_NAN;
    if (coefficient(b) == 0) return DEC64_NAN;

    @DEC64 long recip = reciprocal(of(coefficient(b), 0));

    return multiply(of(coefficient(a), exponent(a) - exponent(b)), recip);
  }

  /*
  static int __dec64_divide(dec64 x, dec64 y, int64* q, int* qexp)
  {
      static const unsigned char fasttab[][2] = {
          {1, 0}, {5, 1}, {0, 0}, {25, 2}, {2, 1}, {0, 0}, {0, 0}, {125, 3}, {0, 0}, {1, 1},
          {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {5, 2},
          {0, 0}, {0, 0}, {0, 0}, {0, 0}, {4, 2}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0},
          {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {25, 3},
          {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {2, 2},
      };
      // (x: dec64, y: dec64) returns quotient: dec64
      // Divide a dec64 number by another.
      // Begin unpacking the components.
      int ex = (signed char)x;
      int ey = (signed char)y;
      x >>= 8;
      y >>= 8;
      if (x == 0 && ex != -128) {
          *q = 0; *qexp = 0;
          return 0; // 0/y ~ 0, even if y == 0 or y == nan
      }
      if ((ex == -128) | (ey == -128) | (y == 0)) {
          *q = 0; *qexp = -128;
          return -1;
      }

      // if both x and y are even then we can simplify the ratio lossless
      int b0 = __builtin_ctzll(x);
      int b1 = __builtin_ctzll(y);
      b0 = min(b0, b1);
      x >>= b0;
      y >>= b0;

      uint64 abs_y = (uint64)abs(y);
      int scale = 0;
      if (abs_y <= 50 && (scale=fasttab[abs_y-1][0]) != 0) {
          // fast division by some popular small constants
          // x/2 ~ (x*5)/10, x/5 ~ (x*2)/10, ...
          // and division by a power of 10 is just shift of the exponent
          *q = x*(y < 0 ? -scale : scale);
          *qexp = ex - ey - fasttab[abs_y-1][1];
          return 1;
      }

      // We want to get as many bits into the quotient as possible in order to capture
      // enough significance. But if the quotient has more than 64 bits, then there
      // will be a hardware fault. To avoid that, we compare the magnitudes of the
      // dividend coefficient and divisor coefficient, and use that to scale the
      // dividend to give us a good quotient.
      int log2_y = 63 - __builtin_clzll(abs_y);
      int log10_prescale = 0;

      for(;;) {
          uint64 abs_x = (uint64)abs(x);
          int log2_x = 63 - __builtin_clzll(abs_x);

          // Scale up the dividend to be approximately 58 bits longer than the divisor.
          // Scaling uses factors of 10, so we must convert from a bit count to a digit
          // count by multiplication by 77/256 (approximately LN2/LN10).
          log10_prescale = (log2_y + 58 - log2_x)*77 >> 8;
          if (log10_prescale <= 18) break;

          // If the number of scaling digits is larger than 18, then we will have to
          // scale in two steps: first prescaling the dividend to fill a register, and
          // then repeating to fill a second register. This happens when the divisor
          // coefficient is much larger than the dividend coefficient.

          // we want 58 bits or so in the dividend
          log10_prescale = (58 - log2_x)*77 >> 8;
          x *= power[log10_prescale];
          ex -= log10_prescale;
      }

      // Multiply the dividend by the scale factor, and divide that 128 bit result by
      // the divisor. Because of the scaling, the quotient is guaranteed to use most
      // of the 64 bits in r0, and never more. Reduce the final exponent by the number
      // of digits scaled.
      *q = (int64)((__int128)x*power[log10_prescale]/y);
      *qexp = ex - ey - log10_prescale;
      return 1;
  }
  */

  public static @DEC64 long divide(@DEC64 long x, @DEC64 long y) {
    final byte[][] fasttab = {
      {1, 0}, {5, 1}, {0, 0}, {25, 2}, {2, 1}, {0, 0}, {0, 0}, {125, 3}, {0, 0}, {1, 1},
      {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {5, 2},
      {0, 0}, {0, 0}, {0, 0}, {0, 0}, {4, 2}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0},
      {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {25, 3},
      {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {0, 0}, {2, 2},
    };
    @DEC64 long q;
    byte qexp;
    int status;

    // (x: dec64, y: dec64) returns quotient: dec64
    // Divide a dec64 number by another.
    // Begin unpacking the components.
    int ex = (byte) x;
    int ey = (byte) y;
    x >>= 8;
    y >>= 8;
    if (x == 0 && ex != -128) {
      q = 0;
      qexp = 0;
      status = 0; // 0/y ~ 0, even if y == 0 or y == nan
      return status == 0 ? 0 : status < 0 ? DEC64_NULL : of(q, qexp);
    }
    if ((ex == -128) | (ey == -128) | (y == 0)) {
      q = 0;
      qexp = -128;
      status = -1;
      return status == 0 ? 0 : status < 0 ? DEC64_NULL : of(q, qexp);
    }

    // if both x and y are even then we can simplify the ratio lossless
    int b0 = Long.numberOfLeadingZeros(x); // __builtin_ctzll(x);
    int b1 = Long.numberOfLeadingZeros(y); // __builtin_ctzll(y);
    b0 = min(b0, b1);
    x >>= b0;
    y >>= b0;

    // FIXME UNSIGNED - should be uint64
    long abs_y = (long) abs(y);
    int scale = 0;
    if (abs_y <= 50 && (scale = fasttab[(int) abs_y - 1][0]) != 0) {
      // fast division by some popular small constants
      // x/2 ~ (x*5)/10, x/5 ~ (x*2)/10, ...
      // and division by a power of 10 is just shift of the exponent
      q = x * (y < 0 ? -scale : scale);
      qexp = (byte) (ex - ey - fasttab[(int) abs_y - 1][1]);
      status = 1;
      return status == 0 ? 0 : status < 0 ? DEC64_NULL : of(q, qexp);
    }

    // We want to get as many bits into the quotient as possible in order to capture
    // enough significance. But if the quotient has more than 64 bits, then there
    // will be a hardware fault. To avoid that, we compare the magnitudes of the
    // dividend coefficient and divisor coefficient, and use that to scale the
    // dividend to give us a good quotient.
    int log2_y = 63 - Long.numberOfLeadingZeros(abs_y); // __builtin_clzll(abs_y);
    int log10_prescale = 0;

    for (; ; ) {
      // FIXME UNSIGNED - should be uint64
      long abs_x = (long) abs(x);
      int log2_x = 63 - Long.numberOfLeadingZeros(abs_x); // __builtin_clzll(abs_x);

      // Scale up the dividend to be approximately 58 bits longer than the divisor.
      // Scaling uses factors of 10, so we must convert from a bit count to a digit
      // count by multiplication by 77/256 (approximately LN2/LN10).
      log10_prescale = (log2_y + 58 - log2_x) * 77 >> 8;
      if (log10_prescale <= 18) break;

      // If the number of scaling digits is larger than 18, then we will have to
      // scale in two steps: first prescaling the dividend to fill a register, and
      // then repeating to fill a second register. This happens when the divisor
      // coefficient is much larger than the dividend coefficient.

      // we want 58 bits or so in the dividend
      log10_prescale = (58 - log2_x) * 77 >> 8;
      x *= power[log10_prescale];
      ex -= log10_prescale;
    }

    // Multiply the dividend by the scale factor, and divide that 128 bit result by
    // the divisor. Because of the scaling, the quotient is guaranteed to use most
    // of the 64 bits in r0, and never more. Reduce the final exponent by the number
    // of digits scaled.
    // FIXME 128-bit multiplication
    q = (long) (x * power[log10_prescale] / y); // (__int128)
    qexp = (byte) (ex - ey - log10_prescale);

    status = 1;
    return status == 0 ? 0 : status < 0 ? DEC64_NULL : of(q, qexp);
  }

  public static @DEC64 long reciprocal(@DEC64 long r) {
    if (isNaN(r) || coefficient(r) == 0) return DEC64_NAN;

    byte digits = digits(r);
    if (digits < 1) return DEC64_NAN;

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
    if (isNaN(number)) return DEC64_NAN;
    @DEC64 long coeff = coefficient(number);
    if (coeff >= 0) return number;
    if (coeff > DEC64_MIN_COEFFICIENT) return of(-coeff, exponent(number));
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
  } /* decrementation */

  public static @DEC64 long half(@DEC64 long dividend) {
    if (isNaN(dividend)) return DEC64_NAN;
    long coeff = coefficient(dividend);
    byte exp = exponent(dividend);
    // FIXME If coeff is large, this might not work
    if ((coeff & 1L) == 0L) {
      return of(coeff / 2L, exp);
    }
    return of(coeff * 5, --exp);
  } /* quotient */

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
    if (coefficient(divisor) == 0) return DEC64_NAN;

    // Modulo. It produces the same result as
    return subtract(dividend, multiply(integer_divide(dividend, divisor), divisor));
  } /* modulation */

  public static @DEC64 long neg(@DEC64 long number) {
    if (isNaN(number)) return DEC64_NAN;
    @DEC64 long coeff = coefficient(number);
    if (coeff > DEC64_MIN_COEFFICIENT) return of(-coeff, exponent(number));
    return DEC64_NAN;
  }

  ////////////////////////////////////////////////////////
  public static long normal(long number) {
    return DEC64_NAN;
  } /* normalization */

  public static long not(long value) {
    return DEC64_NAN;
  } /* notation */

  public static long round(long number, long place) {
    return 0;
  } /* quantization */

  public static long signum(long number) {
    return DEC64_NAN;
  } /* signature */

  public static @DEC64 long floor(@DEC64 long dividend) {
    return DEC64_NAN;
  } /* integer */

  public static @DEC64 long integer_divide(@DEC64 long dividend, @DEC64 long divisor) {
    if (coefficient(divisor) == 0) return DEC64_NAN;
    // FIXME
    return 0;
  } /* quotient */

  public static @DEC64 long ceiling(@DEC64 long number) {
    return DEC64_NAN;
  } /* integer */

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
      return ex != ILLEGAL_EXPO && (coefficient(x) < coefficient(y));
    }

    if (ex == ILLEGAL_EXPO || ey == ILLEGAL_EXPO) {
      return false;
    }

    x = x >> 8;
    y = y >> 8;

    int ediff = ex - ey;
    if (ediff > 0) {
      // make them conform before compare
      long x_scaled = scale(x, ediff);

      @DEC64 long x_high = x_scaled >> 64;
      // in the case of overflow check the sign of higher 64-bit half;
      // otherwise compare numbers with equalized exponents
      return (x_high == x_scaled) ? x_scaled < y : x_high < 0;
    } else {
      long y_scaled = scale(y, -ediff);

      @DEC64 long y_high = y_scaled >> 64;
      return (y_high == y_scaled) ? x < y_scaled : y_high >= 0;
    }
  }

  // Multiply coefficient by 10^(first exponent - second exponent)
  private static long scale(long coeff, int ediff) {
    // maximum coefficient is 36028797018963967. 10^18 is more
    int exp = min(ediff, 18);
    return (long) (coeff * Math.pow(10, exp));
  }
}
