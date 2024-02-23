package com.velotronix;

import java.util.*;


public class ChavviCalc {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        
        Scanner scanner = new Scanner(System.in);
        calculator.run(scanner);

        scanner.close();
    }
}
