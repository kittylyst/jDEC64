package dec64.repl;

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
        r.parseLine(args[0]);
//        r.loop();
    }

    public void parseLine(final String l) {
        final DEC64ReplLexer lexer = new DEC64ReplLexer(new ANTLRInputStream(l));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final DEC64ReplParser parser = new DEC64ReplParser(tokens);
        
        
    }

    private void loop() {

    }

}
