package dec64;

import static dec64.Math64.*;

/**
 *
 * @author ben
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
                        sb.append("0.");
                        while (++exp < 0) {
                            sb.append('0');
                        }
                        sb.append(coeff);
                    }

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
