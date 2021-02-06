package dec64.repl;

import static dec64.Basic64.equals64;
import static dec64.Constants64.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dec64.Basic64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** @author ben */
public class TestREPL {

  private REPL64 r;

  @BeforeEach
  public void startup() {
    r = new REPL64();
  }

  @Test
  public void testSimple() {
    assertTrue(equals64(DEC64_TWO, r.evaluateExpr("2")), "Evaluating 2 does not give 2");
    assertTrue(equals64(DEC64_HALF, r.evaluateExpr("0.5")), "Evaluating 0.5 does not give 0.5");
    assertTrue(
        equals64(Basic64.of(150, -2), r.evaluateExpr("1.5")), "Evaluating 1.5 does not give 1.5");
  }

  @Test
  public void testExpression() {
    assertTrue(
        equals64(Basic64.of(6, (byte) 0), r.evaluateExpr("2 * 3")),
        "Evaluating 2 * 3 does not give 2");
    assertTrue(
        equals64(Basic64.of(7, (byte) 0), r.evaluateExpr("2 + 5")),
        "Evaluating 2 + 5 does not give 7");
    assertTrue(
        equals64(Basic64.of(15, (byte) -1), r.evaluateExpr("3 / 2")),
        "Evaluating 3 / 2 does not give 1.5");
    assertTrue(
        equals64(Basic64.of(15, (byte) 0), r.evaluateExpr("10 * (3 / 2)")),
        "Evaluating 10 * (3 / 2) does not give 15");
  }
}
