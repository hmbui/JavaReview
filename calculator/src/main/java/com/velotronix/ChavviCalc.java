package com.velotronix;

import java.util.*;

/**
 * Implementation of the Chavvi Calculator App.
 * 
 * 
 * Author: Hai Bui
 * Created: Feb 22, 2024
 */
public class ChavviCalc {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();                
        
        Scanner scanner = new Scanner(System.in);
        calculator.run(scanner);

        scanner.close();
    }
}
