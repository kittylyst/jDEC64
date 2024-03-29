package dec64;

import dec64.annotations.DEC64;

/** @author kittylyst */
public class Constants64 {

  public static final @DEC64 long DEC64_NAN = 0x80L;
  public static final @DEC64 long DEC64_ZERO = 0x00L;
  public static final @DEC64 long DEC64_ONE = 0x100L;
  public static final @DEC64 long DEC64_TWO = 0x200L;

  public static final @DEC64 long DEC64_NULL = 0x8000000000000080L;

  public static final @DEC64 long DEC64_NEGATIVE_ONE = 0xffff_ffff_ffff_ff00L;
  public static final @DEC64 long DEC64_POINT_ONE = 0x1FFL;

  public static final @DEC64 long DEC64_E = 0x6092A113D8D574F0L;
  public static final @DEC64 long DEC64_HALF = 0x5FFL;
  public static final @DEC64 long DEC64_THIRD = 85333333333333489L;

  public static final @DEC64 long DEC64_HALF_PI = 0x37CE4F32BB21A6F0L;
  public static final @DEC64 long DEC64_NHALF_PI = 0xC831B0CD44DE59F0L;
  public static final @DEC64 long DEC64_NPI = 0x9063619A89BCB4F0L;
  public static final @DEC64 long DEC64_PI = 0x6F9C9E6576434CF0L;
  public static final @DEC64 long DEC64_TWO_PI = 0x165286144ADA42F1L;

  public static final @DEC64 long[] FACTORIAL = {
    (1L << 8) + 0,
    (1L << 8) + 0,
    (2L << 8) + 0,
    (6L << 8) + 0,
    (24L << 8) + 0,
    (120L << 8) + 0,
    (720L << 8) + 0,
    (5040L << 8) + 0,
    (40320L << 8) + 0,
    (362880L << 8) + 0,
    (3628800L << 8) + 0,
    (39916800L << 8) + 0,
    (479001600L << 8) + 0,
    (6227020800L << 8) + 0,
    (87178291200L << 8) + 0,
    (1307674368000L << 8) + 0,
    (20922789888000L << 8) + 0,
    (355687428096000L << 8) + 0,
    (6402373705728000L << 8) + 0,
    (12164510040883200L << 8) + 1,
    (24329020081766400L << 8) + 2,
    (5109094217170944L << 8) + 4,
    (11240007277776077L << 8) + 5,
    (25852016738884977L << 8) + 6,
    (6204484017332394L << 8) + 8,
    (15511210043330986L << 8) + 9,
    (4032914611266056L << 8) + 11,
    (10888869450418352L << 8) + 12,
    (30488834461171386L << 8) + 13,
    (8841761993739702L << 8) + 15,
    (26525285981219106L << 8) + 16,
    (8222838654177923L << 8) + 18,
    (26313083693369353L << 8) + 19,
    (8683317618811886L << 8) + 21,
    (29523279903960414L << 8) + 22,
    (10333147966386145L << 8) + 24,
    (3719933267899012L << 8) + 26,
    (13763753091226345L << 8) + 27,
    (5230226174666011L << 8) + 29,
    (20397882081197443L << 8) + 30,
    (8159152832478977L << 8) + 32,
    (33452526613163807L << 8) + 33,
    (14050061177528799L << 8) + 35,
    (6041526306337384L << 8) + 37,
    (26582715747884488L << 8) + 38,
    (11962222086548019L << 8) + 40,
    (5502622159812089L << 8) + 42,
    (25862324151116818L << 8) + 43,
    (12413915592536073L << 8) + 45,
    (6082818640342676L << 8) + 47,
    (30414093201713378L << 8) + 48,
    (15511187532873823L << 8) + 50,
    (8065817517094388L << 8) + 52,
    (4274883284060026L << 8) + 54,
    (23084369733924138L << 8) + 55,
    (12696403353658276L << 8) + 57,
    (7109985878048635L << 8) + 59,
    (4052691950487722L << 8) + 61,
    (23505613312828786L << 8) + 62,
    (13868311854568984L << 8) + 64,
    (8320987112741390L << 8) + 66,
    (5075802138772248L << 8) + 68,
    (31469973260387938L << 8) + 69,
    (19826083154044401L << 8) + 71,
    (12688693218588416L << 8) + 73,
    (8247650592082471L << 8) + 75,
    (5443449390774431L << 8) + 77,
    (3647111091818869L << 8) + 79,
    (24800355424368306L << 8) + 80,
    (17112245242814131L << 8) + 82,
    (11978571669969892L << 8) + 84,
    (8504785885678623L << 8) + 86,
    (6123445837688609L << 8) + 88,
    (4470115461512684L << 8) + 90,
    (33078854415193864L << 8) + 91,
    (24809140811395398L << 8) + 93,
    (18854947016660503L << 8) + 95,
    (14518309202828587L << 8) + 97,
    (11324281178206298L << 8) + 99,
    (8946182130782975L << 8) + 101,
    (7156945704626380L << 8) + 103,
    (5797126020747368L << 8) + 105,
    (4753643337012842L << 8) + 107,
    (3945523969720659L << 8) + 109,
    (33142401345653533L << 8) + 110,
    (28171041143805503L << 8) + 112,
    (24227095383672732L << 8) + 114,
    (21077572983795277L << 8) + 116,
    (18548264225739844L << 8) + 118,
    (16507955160908461L << 8) + 120,
    (14857159644817615L << 8) + 122,
    (1352001527678403L << 8) + 124,
    (12438414054641307L << 8) + 126
  };
}
