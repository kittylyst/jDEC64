package dec64.repl;

import static dec64.Constants64.DEC64_NAN;
import dec64.Dec64;
import dec64.repl.DEC64ReplParser.*;
import java.util.List;

/**
 *
 * @author ben
 */
public final class Dec64EvaluatingVisitor extends DEC64ReplBaseVisitor<Dec64> {

    private final StackDec64 stack;

    public Dec64EvaluatingVisitor(StackDec64 s) {
        stack = s;
    }

//    @Override
//    public Dec64 visitExpression(ExpressionContext ctx) {
//        List<MultExprContext> terms = ctx.multExpr();
//
//        return Dec64.of(DEC64_NAN);
//    }

    @Override
    public Dec64 visitNumber(NumberContext ctx) {
        final boolean hasPoint = ctx.POINT() != null;
        // Fast path - a simple integer
        if (!hasPoint) {
            long basic = Long.parseLong(ctx.getText());
            return Dec64.of(basic, (byte)0);
        }
        final String whole = ctx.wholepart.getText();
        final String frac = ctx.fracpart.getText();
        long wholeVal = Long.parseLong(ctx.wholepart.getText());
        long fracVal = Long.parseLong(frac);
        if (fracVal == 0) {
            return Dec64.of(wholeVal, (byte)0);
        }
        String whole64;
        if (wholeVal == 0) {
            whole64 = frac;
        } else {
            whole64 = whole + frac;
        }
        
        return Dec64.of(Long.parseLong(whole64), (byte)(-frac.length()));
    }

}
