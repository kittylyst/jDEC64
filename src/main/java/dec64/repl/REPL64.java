package dec64.repl;

import static dec64.Constants64.*;
import dec64.Dec64;
import dec64.annotations.DEC64;
import dec64.repl.DEC64ReplParser.ExpressionContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 *
 * @author kittylyst
 */
public final class REPL64 {

    private final StackDec64 interpStack = new StackDec64(255);

    public static void main(String[] args) {
        final REPL64 r = new REPL64();
        r.evaluateExpr(args[0]);
//        r.loop();
    }

    public @DEC64 long evaluateExpr(final String l) {
        final DEC64ReplLexer lexer = new DEC64ReplLexer(new ANTLRInputStream(l));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final DEC64ReplParser parser = new DEC64ReplParser(tokens);
        final ExpressionContext expr =  parser.expression();
        
        final Dec64EvaluatingVisitor visitor = new Dec64EvaluatingVisitor(interpStack);
        Dec64 result = visitor.visitExpression(expr);
        
//        final Dec64Printer printer = new Dec64Printer();
//        printer.print(parser.getRuleContext());

        return result.dec64Value();
    }

    private void loop() {

    }

}
