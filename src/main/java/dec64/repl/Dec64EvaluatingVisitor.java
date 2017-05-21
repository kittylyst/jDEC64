package dec64.repl;

import dec64.Basic64;
import static dec64.Constants64.DEC64_NAN;
import dec64.Dec64;
import dec64.annotations.DEC64;
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

    @Override
    public Dec64 visitExpression(ExpressionContext ctx) {
        final AtomContext actx = ctx.atom();
        // Handle atomic case, and signs - including unary +
        if (actx != null) {
            if (ctx.MINUS() != null) {
                final Dec64 val = visitAtom(actx);
                return Dec64.of(Basic64.neg(val.dec64Value()));
            } else {
                return visitAtom(actx);
            }
        }
        final @DEC64 long left = visitExpression(ctx.left).dec64Value();
        final @DEC64 long right = visitExpression(ctx.right).dec64Value();
        if (ctx.PLUS() != null) {
            return Dec64.of(Basic64.add(left, right));
        }
        if (ctx.MINUS() != null) {
            return Dec64.of(Basic64.subtract(left, right));
        }
        if (ctx.TIMES() != null) {
            return Dec64.of(Basic64.multiply(left, right));
        }
        if (ctx.DIV() != null) {
            return Dec64.of(Basic64.divide(left, right));
        }
        if (ctx.MOD() != null) {
            return Dec64.of(Basic64.modulo(left, right));
        }

        return Dec64.of(DEC64_NAN);
    }

    @Override
    public Dec64 visitAtom(AtomContext ctx) {
        if (ctx.base != null) {
            // FIXME
            return Dec64.of(DEC64_NAN);
        }
        if (ctx.expression() != null) {
            return visitExpression(ctx.expression());
        }
        if (ctx.func() != null) {
            // FIXME
            return Dec64.of(DEC64_NAN);
        }
        // Number case
        return visitNumber(ctx.number(0));
    }

    @Override
    public Dec64 visitNumber(NumberContext ctx) {
        final boolean hasPoint = ctx.POINT() != null;
        // Fast path - a simple integer
        if (!hasPoint) {
            long basic = Long.parseLong(ctx.getText());
            return Dec64.of(basic, (byte) 0);
        }
        final String whole = ctx.wholepart.getText();
        final String frac = ctx.fracpart.getText();
        long wholeVal = Long.parseLong(ctx.wholepart.getText());
        long fracVal = Long.parseLong(frac);
        if (fracVal == 0) {
            return Dec64.of(wholeVal, (byte) 0);
        }
        String whole64;
        if (wholeVal == 0) {
            whole64 = frac;
        } else {
            whole64 = whole + frac;
        }

        return Dec64.of(Long.parseLong(whole64), (byte) (-frac.length()));
    }

}
