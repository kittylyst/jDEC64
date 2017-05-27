package dec64;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import static dec64.Basic64.*;
import static dec64.Constants64.*;

/**
 *
 * @author ben
 */
public class NaNTest {
    /*
     ; These operations will produce a result of nan:
     ;
     ;   dec64_abs(nan)
     ;   dec64_ceiling(nan)
     ;   dec64_dec(nan)
     ;   dec64_floor(nan)
     ;   dec64_half(nan)
     ;   dec64_inc(nan)
     ;   dec64_int(nan)
     ;   dec64_neg(nan)
     ;   dec64_normal(nan)
     ;   dec64_not(nan)
     ;   dec64_signum(nan)
     */
    @Test
    public void NaN_input_gives_NaN() {
       assertTrue("abs(nan) should be NaN", isNaN(abs(DEC64_NAN))); 
       assertTrue("ceiling(nan) should be NaN", isNaN(ceiling(DEC64_NAN))); 
       assertTrue("dec(nan) should be NaN", isNaN(dec(DEC64_NAN))); 
       assertTrue("floor(nan) should be NaN", isNaN(floor(DEC64_NAN))); 
       assertTrue("half(nan) should be NaN", isNaN(half(DEC64_NAN))); 
       assertTrue("inc(nan) should be NaN", isNaN(inc(DEC64_NAN))); 
//       assertTrue("integer(nan) should be NaN", isNaN(integer(DEC64_NAN))); 
       assertTrue("neg(nan) should be NaN", isNaN(neg(DEC64_NAN))); 
       assertTrue("normal(nan) should be NaN", isNaN(normal(DEC64_NAN))); 
       assertTrue("not(nan) should be NaN", isNaN(not(DEC64_NAN))); 
       assertTrue("signum(nan) should be NaN", isNaN(signum(DEC64_NAN))); 
    }
    
    /*
     ; These operations will produce a result of zero for all values of n,
     ; even if n is nan:
     ;
     ;   dec64_divide(0, n)
     ;   dec64_integer_divide(0, n)
     ;   dec64_modulo(0, n)
     ;   dec64_multiply(0, n)
     ;   dec64_multiply(n, 0)
     */
    
    /*
     ; These operations will produce a result of nan for all values of n
     ; except zero:
     ;
     ;   dec64_divide(n, 0)
     ;   dec64_divide(n, nan)
     ;   dec64_integer_divide(n, 0)
     ;   dec64_integer_divide(n, nan)
     ;   dec64_modulo(n, 0)
     ;   dec64_modulo(n, nan)
     ;   dec64_multiply(n, nan)
     ;   dec64_multiply(nan, n)
     */
    
    /*
     ; These operations will produce a result of nan for all values of n:
     ;
     ;   dec64_add(n, nan)
     ;   dec64_add(nan, n)
     ;   dec64_divide(nan, n)
     ;   dec64_integer_divide(nan, n)
     ;   dec64_less(n, nan)
     ;   dec64_less(nan, n)
     ;   dec64_modulo(nan, n)
     ;   dec64_round(n, nan)
     ;   dec64_round(nan, n)
     ;   dec64_subtract(n, nan)
     ;   dec64_subtract(nan, n)

     */
}
