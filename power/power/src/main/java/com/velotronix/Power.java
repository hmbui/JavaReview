package com.velotronix;

public class Power {
    public static void main(String[] args) {
        if(args.length == 2) {
            float base = Float.parseFloat(args[0]);
            int exponent = Integer.parseInt(args[1]);

            System.out.printf("%1.4f ** %d = %1.4f\n", base, exponent, power(base, exponent));
        }
        else {
            System.out.println("Syntax: Power <base>,<exponent>");
        }
    }

    public static float power(float base, int exponent) {
        if(exponent <= 0) {
            return 1;
        }
        return base * power(base, exponent - 1);
    }
}
