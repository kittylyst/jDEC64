package dec64;

import dec64.annotations.DEC64;
import static dec64.Basic64.*;
import static dec64.Math64.*;
import static dec64.Constants64.*;
import static dec64.FormatMode.STANDARD;
import static dec64.TestConstants.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author kittylyst
 */
public class TestMath64 {

    static private @DEC64 long nannan;
    static private @DEC64 long zip;
    static private @DEC64 long epsilon;
    static private @DEC64 long almost_one;
    static private @DEC64 long almost_negative_one;
    static private @DEC64 long half;
    static private @DEC64 long cent;
    static private @DEC64 long negative_nine;
    static private @DEC64 long negative_minnum;
    static private @DEC64 long negative_maxint;
    static private @DEC64 long negative_maxnum;
    static private @DEC64 long negative_pi;

    @Test
    @Ignore
    public void test_all_acos() {
        test_acos(DEC64_NEGATIVE_ONE, DEC64_PI, "-1");
        test_acos(DEC64_ZERO, DEC64_HALF_PI, "0");
        test_acos(epsilon, of(15707963267948965L, -16), "epsilon");
        test_acos(cent, of(15607961601207295L, -16), "0.01");
        test_acos(half, of(10471975511965977L, -16), "0.5");
        test_acos(DEC64_ONE, DEC64_ZERO, "1");
        test_acos(DEC64_HALF_PI, DEC64_NAN, "pi/2");
    }

    @Test
    @Ignore
    public void test_all_asin() {
//        test_asin(DEC64_NEGATIVE_ONE, neg(DEC64_HALF_PI), "-1");
        test_asin(DEC64_ZERO, DEC64_ZERO, "0");
        test_asin(epsilon, epsilon, "epsilon");
        test_asin(cent, of(10000166674167113L, -18), "0.01");
        test_asin(half, of(5235987755982989L, -16), "0.5");
        test_asin(DEC64_ONE, DEC64_HALF_PI, "1");
        test_asin(DEC64_HALF_PI, DEC64_NAN, "pi/2");
    }

    @Test
    @Ignore
    public void test_all_atan() {
        test_atan(DEC64_NEGATIVE_ONE, of(-7853981633974483L, -16), "-1");
        test_atan(DEC64_ZERO, DEC64_ZERO, "0");
        test_atan(cent, of(9999666686665238L, -18), "1/100");
        test_atan(half, of(4636476090008061L, -16), "1/2");
        test_atan(DEC64_ONE, of(7853981633974483L, -16), "1");
        test_atan(DEC64_HALF_PI, of(10038848218538872L, -16), "pi/2");
        test_atan(DEC64_E, of(12182829050172776L, -16), "e");
        test_atan(DEC64_PI, of(12626272556789117L, -16), "pi");
        test_atan(TEN, of(14711276743037346L, -16), "10");
    }

    @Test
    @Ignore
    public void test_all_cos() {
        test_cos(DEC64_ZERO, DEC64_ONE, "0");
        test_cos(cent, of(99995000041666528L, -17), "0.01");
        test_cos(DEC64_PI, DEC64_NEGATIVE_ONE, "pi");
        test_cos(DEC64_HALF_PI, DEC64_ZERO, "pi");
        test_cos(TEN, of(-8390715290764525L, -16), "10");
    }

    @Test
    @Ignore
    public void test_all_exp() {
        test_exp(DEC64_ZERO, DEC64_ONE, "0");
        test_exp(cent, of(10100501670841681L, -16), "0.01");
        test_exp(half, of(16487212707001281L, -16), "0.5");
        test_exp(DEC64_ONE, DEC64_E, "1");
        test_exp(DEC64_TWO, of(7389056098930650L, -15), "2");
        test_exp(TEN, of(22026465794806717L, -12), "10");
    }

    @Test
    @Ignore
    public void test_all_factorial() {
        test_factorial(DEC64_ZERO, DEC64_ONE, "0!");
        test_factorial(DEC64_ONE, DEC64_ONE, "1!");
        test_factorial(of(18, 0), of(6402373705728000L, 0), "18!");
        test_factorial(of(19, 0), of(121645100408832000L, 0), "19!");
        test_factorial(of(20, 0), of(2432902008176640000L, 0), "20!");
        test_factorial(of(21, 0), of(5109094217170944000L, 1), "21!");
        test_factorial(of(22, 0), of(11240007277776077L, 5), "22!");
        test_factorial(of(92, 0), of(12438414054641307L, 126), "92!");
        test_factorial(of(93, 0), DEC64_NAN, "93!");
        test_factorial(DEC64_NAN, DEC64_NAN, "nan!");
        test_factorial(DEC64_PI, DEC64_NAN, "pi!");
        test_factorial(DEC64_NEGATIVE_ONE, DEC64_NAN, "-1!");
    }

    @Test
    @Ignore
    public void test_all_log() {
        test_log(DEC64_ZERO, DEC64_NAN, "0");
        test_log(cent, of(-4605170185988091L, -16), "0.01");
        test_log(half, of(-6931471805599453L, -16), "1/2");
        test_log(DEC64_ONE, DEC64_ZERO, "1");
        test_log(DEC64_HALF_PI, of(4515827052894549L, -16), "pi/2");
        test_log(DEC64_TWO, of(6931471805599453L, -16), "2");
        test_log(DEC64_E, DEC64_ONE, "e");
        test_log(DEC64_PI, of(11447298858494002L, -16), "pi");
        test_log(TEN, of(23025850929940457L, -16), "10");
    }

    @Test
    @Ignore
    public void test_all_raise() {
        test_raise(DEC64_E, DEC64_ZERO, DEC64_ONE, "e^0");
        test_raise(DEC64_E, cent, of(10100501670841681L, -16), "e^0.01");
        test_raise(DEC64_E, half, of(16487212707001281L, -16), "e^0.5");
        test_raise(DEC64_E, DEC64_ONE, DEC64_E, "e^1");
        test_raise(DEC64_E, DEC64_TWO, of(7389056098930650L, -15), "e^2");
        test_raise(DEC64_E, TEN, of(22026465794806717L, -12), "e^10");
        test_raise(FOUR, half, DEC64_TWO, "4^0.5");
        test_raise(DEC64_TWO, TEN, of(1024, 0), "2^10");
    }

    @Test
    @Ignore
    public void test_all_root() {
        test_root(DEC64_TWO, DEC64_ZERO, DEC64_ZERO, "2|zero");
        test_root(THREE, DEC64_ZERO, DEC64_ZERO, "3|zero");
        test_root(THREE, half, of(7937005259840997L, -16), "3|1/2");
        test_root(THREE, of(27, 0), THREE, "3|27");
        test_root(THREE, of(-27, 0), of(-3, 0), "3|-27");
        test_root(THREE, DEC64_PI, of(14645918875615233L, -16), "3|pi");
        test_root(FOUR, of(-27, 0), DEC64_NAN, "4|-27");
        test_root(FOUR, of(256, 0), FOUR, "4|256");
        test_root(FOUR, of(1, 4), TEN, "4|1e4");
        test_root(FOUR, of(1, 16), of(1, 4), "4|1e16");
        test_root(FOUR, DEC64_PI, of(13313353638003897L, -16), "4|pi");
    }

    @Test
    @Ignore
    public void test_all_sin() {
        test_sin(DEC64_ZERO, DEC64_ZERO, "0");
        test_sin(epsilon, epsilon, "epsilon");
        test_sin(cent, of(9999833334166665L, -18), "0.01");
        test_sin(DEC64_ONE, of(8414709848078965L, -16), "1");
        test_sin(DEC64_HALF_PI, DEC64_ONE, "pi/2");
        test_sin(DEC64_TWO, of(9092974268256817L, -16), "2");
        test_sin(DEC64_E, of(4107812905029087L, -16), "e");
        test_sin(THREE, of(14112000805986722L, -17), "3");
        test_sin(DEC64_PI, DEC64_ZERO, "pi");
        test_sin(FOUR, of(-7568024953079283L, -16), "4");
        test_sin(FIVE, of(-9589242746631385L, -16), "5");
        test_sin(TEN, of(-5440211108893698L, -16), "10");
        test_sin(of(-1, 0), of(-8414709848078965L, -16), "-1");
    }

    @Test
    @Ignore
    public void test_all_sqrt() {
//        test_sqrt(DEC64_ZERO, DEC64_ZERO, "0");
//        test_sqrt(DEC64_ONE, DEC64_ONE, "1");
        test_sqrt(of(16, 0), of(4, 0), "16");
        test_sqrt(of(100, 0), of(10, 0), "100");
        test_sqrt(of(10000, -2), of(10, 0), "100");
        test_sqrt(of(1000000, -4), of(10, 0), "100");
        test_sqrt(half, of(7071067811865475L, -16), "1/2");
        test_sqrt(DEC64_TWO, of(14142135623730950L, -16), "2");
        test_sqrt(DEC64_PI, of(17724538509055160L, -16), "pi");
        test_sqrt(of(10, 0), of(31622776601683793L, -16), "10");
    }

    @Test
    @Ignore
    public void test_all_tan() {
        test_tan(DEC64_ZERO, DEC64_ZERO, "0");
        test_tan(cent, of(10000333346667206L, -18), "0.01");
        test_tan(half, of(5463024898437905L, -16), "1/2");
        test_tan(DEC64_ONE, of(15574077246549022L, -16), "1");
        test_tan(DEC64_HALF_PI, DEC64_NAN, "pi/2");
        test_tan(DEC64_PI, DEC64_ZERO, "pi");
        test_tan(TEN, of(6483608274590867L, -16), "10");
    }

    private static void test_acos(@DEC64 long input, @DEC64 long expected, String msg) {
        @DEC64 long actual = acos(input);
        String outMsg = "acos(" + msg + ") was " + STANDARD.format(actual) +" ("+ actual +") instead of "+ STANDARD.format(expected);
        assertTrue(outMsg, equals64(expected, actual));
    }

    private static void test_asin(@DEC64 long input, @DEC64 long expected, String msg) {
        @DEC64 long actual = sin64(input);
        String outMsg = "sin64(" + msg + ") was " + STANDARD.format(actual) +" ("+ actual +") instead of "+ STANDARD.format(expected);
        assertTrue(outMsg, equals64(expected, actual));
    }

    private static void test_atan(@DEC64 long input, @DEC64 long expected, String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_cos(@DEC64 long input, @DEC64 long expected, String msg) {
        @DEC64 long actual = cos(input);
        String outMsg = "cos(" + msg + ") was " + STANDARD.format(actual) +" ("+ actual +") instead of "+ STANDARD.format(expected);
        assertTrue(outMsg, equals64(expected, actual));
    }

    private static void test_exp(@DEC64 long input, @DEC64 long expected, String msg) {
        @DEC64 long actual = exp(input);
        String outMsg = "exp(" + msg + ") was " + STANDARD.format(actual) +" ("+ actual +") instead of "+ STANDARD.format(expected);
        assertTrue(outMsg, equals64(expected, actual));
    }

    private static void test_factorial(@DEC64 long input, @DEC64 long expected, String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_log(@DEC64 long input, @DEC64 long expected, String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_raise(@DEC64 long base, @DEC64 long power, @DEC64 long expected, String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_root(@DEC64 long two, @DEC64 long zero, @DEC64 long zero0, String zero1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_sin(@DEC64 long input, @DEC64 long expected, String msg) {
        @DEC64 long actual = sin64(input);
        String outMsg = "sin64(" + msg + ") was " + STANDARD.format(actual) +" ("+ actual +") instead of "+ STANDARD.format(expected);
        assertTrue(outMsg, equals64(expected, actual));
    }

    private static void test_sqrt(@DEC64 long input, @DEC64 long expected, String msg) {
        @DEC64 long actual = sqrt(input);
        String outMsg = "sqrt(" + msg + ") was " + STANDARD.format(actual) +" ("+ actual +") instead of "+ STANDARD.format(expected);
        assertTrue(outMsg, equals64(expected, actual));
    }

    private static void test_tan(@DEC64 long input, @DEC64 long expected, String msg) {
        @DEC64 long actual = tan(input);
        String outMsg = "tan(" + msg + ") was " + STANDARD.format(actual) +" ("+ actual +") instead of "+ STANDARD.format(expected);
        assertTrue(outMsg, equals64(expected, actual));
    }

}
