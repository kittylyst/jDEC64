package dec64.repl;


/**
 *
 * @author kittylyst
 */
public final class REPL64 {

    private final StackDec64 interpStack = new StackDec64(255);
    
    
    
    public static void main(String[] args) {
        final REPL64 r = new REPL64();
        r.loop();
    }

    private void loop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
