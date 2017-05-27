package dec64;

import dec64.annotations.DEC64;
import static dec64.Basic64.*;

/**
 *
 * @author kittylyst
 */
public enum FormatMode {

    ENGINEERING("ENG") {
                public String format(@DEC64 long num) {
                    final StringBuilder sb = new StringBuilder();

                    return sb.toString();
                }

            },
    SCIENTIFIC("SCI") {
                public String format(@DEC64 long num) {
                    final StringBuilder sb = new StringBuilder();

                    return sb.toString();
                }

            },
    STANDARD("STD") {
                public String format(@DEC64 long num) {
                    final StringBuilder sb = new StringBuilder();
                    byte exp = exponent(num);
                    long coeff = coefficient(num);
                    if (exp == 0) {
                        sb.append(coeff);
                    } else if (exp > 0) {
                        sb.append(coeff);
                        while (exp-- > 0) {
                            sb.append('0');
                        }
                    } else {
                        char[] digits = (""+ coeff).toCharArray();
                        int expp = Math.abs(exp);
                        if (expp == digits.length) {
                            sb.append("0.");
                            sb.append(coeff);
                        } else if (expp < digits.length) {
                            for (int z=0; z< digits.length - expp; z++) {
                                sb.append(digits[z]);
                            }
                            sb.append('.');
                            for (int z=digits.length - expp; z< digits.length; z++) {
                                sb.append(digits[z]);
                            }
                        } else {
                            sb.append("0.");
                            for (int z=0; z< expp - digits.length; z++) {
                                sb.append('0');
                            }
                            sb.append(coeff);
                        }
                    }
                    // FIXME Need to trim trailing zeros...
//                    for (int i=sb.length() - 1; i>0; i--) {
//                        if (sb.charAt(i) == '0')
//                            sb.deleteCharAt(i);
//                    }

                    return sb.toString();
                }
            };

    private String displayName;

    private FormatMode(String name) {
        displayName = name;
    }

    public abstract String format(@DEC64 long num);

    public String displayName() {
        return displayName;
    }
}
