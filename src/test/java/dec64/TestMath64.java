package dec64;

import static dec64.Basic64.*;
import static dec64.Constants64.*;

/**
 *
 * @author ben
 */
public class TestMath64 {

    static private @DEC64 long nan;
    static private @DEC64 long nannan;
    static private @DEC64 long zero = DEC64_ZERO;
    static private @DEC64 long zip;
    static private @DEC64 long one = DEC64_ONE;
    static private @DEC64 long two = DEC64_TWO;
    static private @DEC64 long three = 0x300L;
    static private @DEC64 long four = 0x400L;
    static private @DEC64 long five = 0x500L;
    static private @DEC64 long six = 0x600L;
    static private @DEC64 long seven = 0x700L;
    static private @DEC64 long eight = 0x800L;
    static private @DEC64 long nine = 0x900L;
    static private @DEC64 long ten = of(10, (byte) 0);
    static private @DEC64 long maxint;
    static private @DEC64 long maxint_plus;
    static private @DEC64 long maxnum;
    static private @DEC64 long minnum;
//    static private @DEC64 long e = DEC64_E;
    static private @DEC64 long epsilon;
    static private @DEC64 long almost_one;
    static private @DEC64 long almost_negative_one;
    static private @DEC64 long half_pi;
    static private @DEC64 long half;
    static private @DEC64 long cent;
    static private @DEC64 long negative_nine;
    static private @DEC64 long negative_minnum;
    static private @DEC64 long negative_maxint;
    static private @DEC64 long negative_maxnum;
    static private @DEC64 long negative_pi;

    static void test_all_acos() {
        test_acos(DEC64_NEGATIVE_ONE, DEC64_PI, "-1");
        test_acos(zero, half_pi, "0");
        test_acos(epsilon, Basic64.of(15707963267948965L, -16), "epsilon");
        test_acos(cent, Basic64.of(15607961601207295L, -16), "0.01");
        test_acos(half, Basic64.of(10471975511965977L, -16), "0.5");
        test_acos(one, zero, "1");
        test_acos(half_pi, nan, "pi/2");
    }

    static void test_all_asin() {
        test_asin(DEC64_NEGATIVE_ONE, neg(half_pi), "-1");
        test_asin(zero, zero, "0");
        test_asin(epsilon, epsilon, "epsilon");
        test_asin(cent, Basic64.of(10000166674167113L, -18), "0.01");
        test_asin(half, Basic64.of(5235987755982989L, -16), "0.5");
        test_asin(one, half_pi, "1");
        test_asin(half_pi, nan, "pi/2");
    }

    static void test_all_atan() {
        test_atan(DEC64_NEGATIVE_ONE, Basic64.of(-7853981633974483L, -16), "-1");
        test_atan(zero, zero, "0");
        test_atan(cent, Basic64.of(9999666686665238L, -18), "1/100");
        test_atan(half, Basic64.of(4636476090008061L, -16), "1/2");
        test_atan(one, Basic64.of(7853981633974483L, -16), "1");
        test_atan(half_pi, Basic64.of(10038848218538872L, -16), "pi/2");
        test_atan(DEC64_E, Basic64.of(12182829050172776L, -16), "e");
        test_atan(DEC64_PI, Basic64.of(12626272556789117L, -16), "pi");
        test_atan(ten, Basic64.of(14711276743037346L, -16), "10");
    }

    static void test_all_cos() {
        test_cos(zero, one, "0");
        test_cos(cent, Basic64.of(99995000041666528L, -17), "0.01");
        test_cos(DEC64_PI, DEC64_NEGATIVE_ONE, "pi");
        test_cos(half_pi, zero, "pi");
        test_cos(ten, Basic64.of(-8390715290764525L, -16), "10");
    }

    static void test_all_exp() {
        test_exp(zero, one, "0");
        test_exp(cent, Basic64.of(10100501670841681L, -16), "0.01");
        test_exp(half, Basic64.of(16487212707001281L, -16), "0.5");
        test_exp(one, DEC64_E, "1");
        test_exp(two, Basic64.of(7389056098930650L, -15), "2");
        test_exp(ten, Basic64.of(22026465794806717L, -12), "10");
    }

    static void test_all_factorial() {
        test_factorial(zero, one, "0!");
        test_factorial(one, one, "1!");
        test_factorial(Basic64.of(18, 0), Basic64.of(6402373705728000L, 0), "18!");
        test_factorial(Basic64.of(19, 0), Basic64.of(121645100408832000L, 0), "19!");
        test_factorial(Basic64.of(20, 0), Basic64.of(2432902008176640000L, 0), "20!");
        test_factorial(Basic64.of(21, 0), Basic64.of(5109094217170944000L, 1), "21!");
        test_factorial(Basic64.of(22, 0), Basic64.of(11240007277776077L, 5), "22!");
        test_factorial(Basic64.of(92, 0), Basic64.of(12438414054641307L, 126), "92!");
        test_factorial(Basic64.of(93, 0), nan, "93!");
        test_factorial(nan, nan, "nan!");
        test_factorial(DEC64_PI, nan, "pi!");
        test_factorial(DEC64_NEGATIVE_ONE, nan, "-1!");
    }

    static void test_all_log() {
        test_log(zero, nan, "0");
        test_log(cent, Basic64.of(-4605170185988091L, -16), "0.01");
        test_log(half, Basic64.of(-6931471805599453L, -16), "1/2");
        test_log(one, zero, "1");
        test_log(half_pi, Basic64.of(4515827052894549L, -16), "pi/2");
        test_log(two, Basic64.of(6931471805599453L, -16), "2");
        test_log(DEC64_E, one, "e");
        test_log(DEC64_PI, Basic64.of(11447298858494002L, -16), "pi");
        test_log(ten, Basic64.of(23025850929940457L, -16), "10");
    }

    static void test_all_raise() {
        test_raise(DEC64_E, zero, one, "e^0");
        test_raise(DEC64_E, cent, Basic64.of(10100501670841681L, -16), "e^0.01");
        test_raise(DEC64_E, half, Basic64.of(16487212707001281L, -16), "e^0.5");
        test_raise(DEC64_E, one, DEC64_E, "e^1");
        test_raise(DEC64_E, two, Basic64.of(7389056098930650L, -15), "e^2");
        test_raise(DEC64_E, ten, Basic64.of(22026465794806717L, -12), "e^10");
        test_raise(four, half, two, "4^0.5");
        test_raise(two, ten, Basic64.of(1024, 0), "2^10");
    }

    static void test_all_root() {
        test_root(two, zero, zero, "2|zero");
        test_root(three, zero, zero, "3|zero");
        test_root(three, half, Basic64.of(7937005259840997L, -16), "3|1/2");
        test_root(three, Basic64.of(27, 0), three, "3|27");
        test_root(three, Basic64.of(-27, 0), Basic64.of(-3, 0), "3|-27");
        test_root(three, DEC64_PI, Basic64.of(14645918875615233L, -16), "3|pi");
        test_root(four, Basic64.of(-27, 0), nan, "4|-27");
        test_root(four, Basic64.of(256, 0), four, "4|256");
        test_root(four, Basic64.of(1, 4), ten, "4|1e4");
        test_root(four, Basic64.of(1, 16), Basic64.of(1, 4), "4|1e16");
        test_root(four, DEC64_PI, Basic64.of(13313353638003897L, -16), "4|pi");
    }

    static void test_all_sin() {
        test_sin(zero, zero, "0");
        test_sin(epsilon, epsilon, "epsilon");
        test_sin(cent, Basic64.of(9999833334166665L, -18), "0.01");
        test_sin(one, Basic64.of(8414709848078965L, -16), "1");
        test_sin(half_pi, one, "pi/2");
        test_sin(two, Basic64.of(9092974268256817L, -16), "2");
        test_sin(DEC64_E, Basic64.of(4107812905029087L, -16), "e");
        test_sin(three, Basic64.of(14112000805986722L, -17), "3");
        test_sin(DEC64_PI, zero, "pi");
        test_sin(four, Basic64.of(-7568024953079283L, -16), "4");
        test_sin(five, Basic64.of(-9589242746631385L, -16), "5");
        test_sin(ten, Basic64.of(-5440211108893698L, -16), "10");
        test_sin(Basic64.of(-1, 0), Basic64.of(-8414709848078965L, -16), "-1");
    }

    static void test_all_sqrt() {
        test_sqrt(zero, zero, "0");
        test_sqrt(half, Basic64.of(7071067811865475L, -16), "1/2");
        test_sqrt(two, Basic64.of(14142135623730950L, -16), "2");
        test_sqrt(one, one, "1");
        test_sqrt(DEC64_PI, Basic64.of(17724538509055160L, -16), "pi");
        test_sqrt(Basic64.of(10, 0), Basic64.of(31622776601683793L, -16), "10");
        test_sqrt(Basic64.of(16, 0), Basic64.of(4, 0), "16");
        test_sqrt(Basic64.of(100, 0), Basic64.of(10, 0), "100");
        test_sqrt(Basic64.of(10000, -2), Basic64.of(10, 0), "100");
        test_sqrt(Basic64.of(1000000, -4), Basic64.of(10, 0), "100");
    }

    static void test_all_tan() {
        test_tan(zero, zero, "0");
        test_tan(cent, Basic64.of(10000333346667206L, -18), "0.01");
        test_tan(half, Basic64.of(5463024898437905L, -16), "1/2");
        test_tan(one, Basic64.of(15574077246549022L, -16), "1");
        test_tan(half_pi, nan, "pi/2");
        test_tan(DEC64_PI, zero, "pi");
        test_tan(ten, Basic64.of(6483608274590867L, -16), "10");
    }

    private static void test_acos(@DEC64 long DEC64_NEGATIVE_ONE, @DEC64 long pi, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_asin(@DEC64 long epsilon, @DEC64 long epsilon0, String epsilon1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_atan(@DEC64 long cent, @DEC64 long of, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_cos(@DEC64 long cent, @DEC64 long of, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_exp(@DEC64 long one, @DEC64 long e, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_factorial(@DEC64 long one, @DEC64 long one0, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_log(@DEC64 long zero, @DEC64 long nan, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_raise(@DEC64 long e, @DEC64 long half, @DEC64 long of, String e05) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_root(@DEC64 long two, @DEC64 long zero, @DEC64 long zero0, String zero1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_sin(@DEC64 long zero, @DEC64 long zero0, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_sqrt(@DEC64 long zero, @DEC64 long zero0, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void test_tan(@DEC64 long zero, @DEC64 long zero0, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
