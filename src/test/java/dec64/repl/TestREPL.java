package dec64.repl;

import dec64.Basic64;
import static dec64.Basic64.equals64;
import static dec64.Constants64.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author ben
 */
public class TestREPL {

    @Test
    public void testEmpty() {
        REPL64 r = new REPL64();
        assertTrue("Evaluating 2 does not give 2", equals64(DEC64_TWO, r.evaluateExpr("2")));
        assertTrue("Evaluating 0.5 does not give 0.5", equals64(DEC64_HALF, r.evaluateExpr("0.5")));
        assertTrue("Evaluating 1.5 does not give 1.5", equals64(Basic64.of(150, -2), r.evaluateExpr("1.5")));
    }

}
