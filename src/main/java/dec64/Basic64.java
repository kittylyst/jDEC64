package dec64;

/**
 *
 * @author ben
 */
public final class Basic64 {

    public final static @DEC64 long DEC64_NAN = 0x80L;
    public final static @DEC64 long DEC64_ZERO = 0x00L;
    public final static @DEC64 long DEC64_ONE = 0x100L;

    public final static @DEC64 long DEC64_NEGATIVE_ONE = 0xffff_ffff_ffff_ff00L;
    public final static @DEC64 long DEC64_POINT_ONE = 0x1FFL;

    // Max and min coefficients - not DEC64 values
    public final static long MAX_DEC64 = 0x7f_ffff_ffff_ffffL; // 36028797018963967L
    public final static long MIN_DEC64 = 0xff_ffff_ffff_ffffL;

    private final static long DEC64_EXPONENT_MASK = 0xFFL;
    private final static long DEC64_COEFFICIENT_MASK = 0xffff_ffff_ffff_ff00L;

    private final static long DEC64_COEFFICIENT_OVERFLOW_MASK = 0x7F00_0000_0000_0000L;
    final static long MAX_PROMOTABLE = 3602879701896396L;
    private final static byte MAX_DIGITS = 17;

    private Basic64() {
    }

    /**
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

    public static @DEC64
    long of(long coeff, long exponent) {
        if (exponent > 127 || exponent < -127)
            return DEC64_NAN;
        return of(coeff, (byte) exponent);
    }

    public static @DEC64
    long of(long coeff, byte exponent) {
        if (overflow(coeff))
            return DEC64_NAN;
        return (coeff << 8) | exponentAsLong(exponent);
    }

    public static @DEC64
    long reduceExponent(@DEC64 long number) {
        return of(10 * coefficient(number), (byte) (exponent(number) - 1));
    }

    public static @DEC64
    long canonical(@DEC64 long number) {
        if (isNaN(number))
            return DEC64_NAN;
        byte exp = exponent(number);
        if (exp == 127 || exp == 0)
            return number;

        long out = number;
        long coeff = coefficient(number);
        if (exp > 0) {
            while (exp > 0 && coeff < MAX_PROMOTABLE) {
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
        if (isNaN(a) || isNaN(b))
            return false; // NaN != NaN
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

    public static @DEC64
    long add(@DEC64 long a, @DEC64 long b) {
        if (isNaN(a) || isNaN(b))
            return DEC64_NAN;
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

    public static @DEC64
    long subtract(@DEC64 long a, @DEC64 long b) {
        if (isNaN(a) || isNaN(b))
            return DEC64_NAN;
        if (exponent(a) == exponent(b)) {
            long coeff = coefficient(a) - coefficient(b);
            if (overflow(coeff))
                return DEC64_NAN;
            return of(coeff, exponent(a));
        }

        return 0;
    }

    public static @DEC64
    long multiply(@DEC64 long a, @DEC64 long b) {
        if (isNaN(a) || isNaN(b))
            return DEC64_NAN;
        final long coeff = coefficient(a) * coefficient(b);
        if (overflow(coeff))
            return DEC64_NAN;
        return of(coeff, (byte) (exponent(a) + exponent(b)));
    }

    public static @DEC64
    long divide(@DEC64 long a, @DEC64 long b) {
        if (isNaN(a) || isNaN(b))
            return DEC64_NAN;
        if (coefficient(b) == 0)
            return DEC64_NAN;

        byte expa = exponent(a);
        byte expb = exponent(b);

        if (expa == expb) {
            return divideEven(a, b);
        }
        long outMult = of(1, exponent(a) - exponent(b));

        return multiply(divideEven(a, b), outMult);
    }

    static @DEC64
    long divideEven(@DEC64 long a, @DEC64 long b) {
        final long exp = exponent(a);
        long coeffa = coefficient(a);
        long coeffb = coefficient(b);

        long ratio = coeffa / coeffb;
        long remainder = coeffa % coeffb;
        while (remainder > 0) {
            ratio *= 5;
            // FIXME

            return DEC64_NAN;
            
        }
        
        return of(ratio, exp);
    }

    public static @DEC64 long divide_translated() {
            movsx   r8,r1_b         ; r8 is the first exponent
    movsx   r9,r2_b         ; r9 is the second exponent
    mov     r10,r1          ; r10 is the first number
    mov     r11,r2          ; r11 is the second number

; Set nan flags in r0.

    cmp     r1_b,128        ; is the first operand nan?
    sete    r0_b            ; r0_b is 1 if the first operand is nan
    cmp     r2_b,128        ; is the second operand nan?
    sete    r0_h            ; r0_h is 1 if the second operand is nan

    sar     r10,8           ; r10 is the dividend coefficient
    setnz   r1_b            ; r1_b is 1 if the dividend coefficient is zero
    sar     r11,8           ; r11 is the divisor coefficient
    setz    r1_h            ; r1_h is 1 if dividing by zero
    or      r0_h,r0_b       ; r0_h is 1 if either is nan
    or      r1_b,r0_b       ; r1_b is zero if the dividend is zero and not nan
    jz      return_zero     ; if the dividend is zero, the quotient is zero
    sub     r8,r9           ; r8 is the quotient exponent
    or      r0_b,r1_h       ; r0_b is 1 if either is nan or the divisor is zero
    jnz     return_nan
    pad

divide_measure:

; We want to get as many bits into the quotient as possible in order to capture
; enough significance. But if the quotient has more than 64 bits, then there
; will be a hardware fault. To avoid that, we compare the magnitudes of the
; dividend coefficient and divisor coefficient, and use that to scale the
; dividend to give us a good quotient.

    mov     r0,r10          ; r0 is the first coefficient
    mov     r1,r11          ; r1 is the second coefficient
    neg     r0              ; r0 is negated
    cmovs   r0,r10          ; r0 is abs of dividend coefficient
    neg     r1              ; r1 is negated
    cmovs   r1,r11          ; r1 is abs of divisor coefficient
    bsr     r0,r0           ; r0 is the dividend most significant bit
    bsr     r1,r1           ; r1 is the divisor most significant bit
; Scale up the dividend to be approximately 58 bits longer than the divisor.
; Scaling uses factors of 10, so we must convert from a bit count to a digit
; count by multiplication by 77/256 (approximately LN2/LN10).

    add     r1,58           ; we want approximately 58 bits in the raw quotient
    sub     r1,r0           ; r1 is the number of bits to add to the dividend
    imul    r1,77           ; multiply by 77/256 to convert bits to digits
    shr     r1,8            ; r1 is the number of digits to scale the dividend

; The largest power of 10 that can be held in an int64 is 1e18.

    cmp     r1,18           ; prescale the dividend if 10**r1 won't fit
    jg      divide_prescale

; Multiply the dividend by the scale factor, and divide that 128 bit result by
; the divisor.  Because of the scaling, the quotient is guaranteed to use most
; of the 64 bits in r0, and never more. Reduce the final exponent by the number
; of digits scaled.

    mov     r0,r10          ; r0 is the dividend coefficient
    mov     r9,power
    imul    qword ptr [r9][r1*8] ; r2:r0 is the dividend coefficient * 10**r1
    idiv    r11             ; r0 is the quotient
    sub     r8,r1           ; r8 is the exponent
    jmp     pack            ; pack it up
    pad

divide_prescale:

; If the number of scaling digits is larger than 18, then we will have to
; scale in two steps: first prescaling the dividend to fill a register, and
; then repeating to fill a second register. This happens when the divisor
; coefficient is much larger than the dividend coefficient.

    mov     r1,58           ; we want 58 bits or so in the dividend
    sub     r1,r0           ; r1 is the number of additional bits needed
    imul    r1,77           ; convert bits to digits
    shr     r1,8            ; shift 8 is cheaper than div 256
    mov     r9,power
    imul    r10,qword ptr [r9][r1*8] ; multiply the dividend by power of ten
    sub     r8,r1           ; reduce the exponent
    jmp     divide_measure  ; try again

    pad; -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    }
    
    public static boolean less(@DEC64 long comparahend, @DEC64 long comparator) {
        return false;
    }/* comparison */


    public static @DEC64
    long abs(@DEC64 long number) {
        return 0;
    }/* absolution */


    public static @DEC64
    long ceiling(@DEC64 long number) {
        return 0;
    }/* integer */


    public static @DEC64
    long dec(@DEC64 long minuend) {
        minuend = canonical(minuend);
        if (isBasic(minuend)) {
            return minuend - DEC64_ONE;
        }
        byte exp = exponent(minuend);
        if (exp > 0) {
            return minuend;
        } else {
            // floating point case...
        }

        // Should never happen
        return DEC64_NAN;
    }/* decrementation */


    public static @DEC64
    long floor(@DEC64 long dividend) {
        return 0;
    }/* integer */


    public static @DEC64
    long half(@DEC64 long dividend) {
        if (isNaN(dividend))
            return DEC64_NAN;
        long coeff = coefficient(dividend);
        byte exp = exponent(dividend);
        if ((coeff & 1L) == 0L) {
            return of(coeff / 2L, exp);
        }
        return of(coeff * 5, --exp);
    }/* quotient */


    public static @DEC64
    long inc(@DEC64 long augend) {
        augend = canonical(augend);
        if (isBasic(augend)) {
            return augend + DEC64_ONE;
        }
        byte exp = exponent(augend);
        if (exp > 0) {
            return augend;
        } else {
            // floating point case...
        }

        // Should be impossible to happen
        return DEC64_NAN;
    }

    public static long integer_divide(@DEC64 long dividend, @DEC64 long divisor) {
        if (coefficient(divisor) == 0)
            return DEC64_NAN;
        // FIXME
        return 0;
    }/* quotient */


    public static @DEC64
    long modulo(@DEC64 long dividend, @DEC64 long divisor) {
        if (coefficient(divisor) == 0)
            return DEC64_NAN;
        // FIXME
        return 0;
    }/* modulation */


    public static @DEC64
    long neg(@DEC64 long number) {
        return of(-coefficient(number), exponent(number));
    }

    ////////////////////////////////////////////////////////
    public static long normal(long number) {
        return 0;
    }/* normalization */


    public static long not(long value) {
        return 0;
    }/* notation */


    public static long round(long number, long place) {
        return 0;
    }/* quantization */


    public static long signum(long number) {
        return 0;
    }/* signature */

}
