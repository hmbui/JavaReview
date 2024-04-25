/**
 * Implementation of the recursive power function.
 *
 *
 * Author: Hai Bui
 * Created: April 24, 2024
 */

package com.velotronix;

public class Power {
    /**
     * Syntax Power <base> <exponent>
     *
     * @param args The arguments for the program.
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            float base = Float.parseFloat(args[0]);
            int exponent = Integer.parseInt(args[1]);

            System.out.printf("%1.4f ** %d = %1.4f\n", base, exponent, power(base, exponent));
        } else {
            System.out.println("Syntax: Power <base> <exponent>");
        }
    }

    /**
     * Calculate the nth power of a float value.
     *
     * @param base     The base value.
     * @param exponent The exponential value (a non-negative value only.)
     * @return The nth power of the base value.
     */
    public static float power(float base, int exponent) {
        if (exponent <= 0) {
            return 1;
        }
        return base * power(base, exponent - 1);
    }
}
