package dec64;

import dec64.annotations.DEC64;

/**
 * The immutable boxed form of a DEC64 number - needed for generics etc.
 * 
 * @author kittylyst
 */
public final class Dec64 extends Number {
    
    private final @DEC64 long value;
    
    private Dec64(@DEC64 long v) {
        value = v;
    }
    
    public static Dec64 of(@DEC64 long v) {
        return new Dec64(v);
    }
    
    public static Dec64 of(long coeff, byte exponent) {
        return new Dec64(Basic64.of(coeff, exponent));
    }
    
    @Override
    public int intValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public long longValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public float floatValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public double doubleValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public @DEC64
    long dec64Value() {
        return value;
    }
}
