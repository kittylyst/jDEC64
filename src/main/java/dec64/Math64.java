package dec64;

import dec64.annotations.DEC64;
import static dec64.Basic64.*;
import static dec64.Constants64.*;

/**
 *
 * @author kittylyst
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
            @DEC64 long progress = add(result, divide(factor, inc(bottom)));
            if (result == progress) {
                break;
            }
            result = progress;
            bottom = add(bottom, DEC64_TWO);
        }
        return result;
    }

    public static @DEC64
    long sin64(@DEC64 long radians) {

        while (less(DEC64_PI, radians)) {
            radians = subtract(radians, DEC64_PI);
            radians = subtract(radians, DEC64_PI);
        }
        while (less(radians, DEC64_NPI)) {
            radians = add(radians, DEC64_PI);
            radians = add(radians, DEC64_PI);
        }
        boolean neg = true;
        if (radians < 0) {
            radians = neg(radians);
            neg = false;
        }
        if (less(DEC64_HALF_PI, radians)) {
            radians = subtract(DEC64_PI, radians);
        }
        @DEC64 long result;
        if (radians == DEC64_HALF_PI) {
            result = DEC64_ONE;
        } else {
            @DEC64 long x2 = multiply(radians, radians);
            @DEC64 long order = DEC64_ONE;
            @DEC64 long term = radians;
            result = term;
            while (true) {
                term = multiply(term, x2);
                order = inc(order);
                term = divide(term, order);
                order = inc(order);
                term = divide(term, order);
                @DEC64 long progress = subtract(result, term);

                term = multiply(term, x2);
                order = inc(order);
                term = divide(term, order);
                order = inc(order);
                term = divide(term, order);
                progress = add(progress, term);

                if (progress == result) {
                    break;
                }
                result = progress;
            }
        }
        // FIXME Check this negation logic works
        if (neg) {
            result = neg(result);
        }
        return result;
    }

   public static @DEC64 long exp(@DEC64 long exponent) {
      @DEC64 long result = inc(exponent);
      @DEC64 long divisor = DEC64_TWO;
      @DEC64 long term = exponent;
      while (true) {
         term = divide(multiply(term, exponent), divisor);
         @DEC64 long progress = add(result, term);
         if (equals64(result, progress)) {
            break;
         }
         result = progress;
         divisor = inc(divisor);
      }
      return result;
   }

  //FIXME
    public static @DEC64
    long sqrt(@DEC64 long radicand) {
        if (!isNaN(radicand) && radicand >= 0) {
            if (coefficient(radicand) == 0) {
                return DEC64_ZERO;
            }
            @DEC64 long result = radicand;

            while (true) {
                final @DEC64 long divided = divide(radicand, result);
                @DEC64 long progress = half(add(result, divided));
                if (progress == result) {
                    return result;
                }
                result = progress;
            }
        } else {
            return DEC64_NAN;
        }
    }

    public static @DEC64 
    long acos(@DEC64 long slope) {
        @DEC64 long result = subtract(DEC64_HALF_PI, asin64(slope));
        return result;
    }
  
    public static @DEC64
    long cos(@DEC64 long radians) {
        return sin64(add(radians, DEC64_HALF_PI));
    }

    public static @DEC64
    long tan(@DEC64 long radians) {
        return divide(sin64(radians), cos(radians));
    }

    // FIXME : Implemented in another PR
    public static @DEC64
    long log(@DEC64 long x) {
        return DEC64_NAN;
    }

    public static @DEC64
    long raise(@DEC64 long coefficient, @DEC64 long exponent) {
        if (isZero(exponent)) {
            return DEC64_ONE;
        }

        if (exponent < 0) {
            coefficient = divide(DEC64_ONE, coefficient);
            exponent = neg(exponent);
        }
        if (isZero(coefficient)) {
            return 0;
        }
        if (isNaN(coefficient)) {
            return DEC64_NAN;
        }

        if (exponent > 0 && exponent(exponent) == 0) {
            @DEC64 long aux = DEC64_ONE;
            long n = coefficient(exponent);
            if (n <= 1) {
                return coefficient;
            }
            while (n > 1) {
                if ((n & 1) != 0) {
                    aux = multiply(aux, coefficient);
                }
                coefficient = multiply(coefficient, coefficient);
                n /= 2;
            }
            return (n == 1) ? multiply(aux, coefficient) : aux;
        }
        return exp(multiply(log(coefficient), exponent));
    }
}
