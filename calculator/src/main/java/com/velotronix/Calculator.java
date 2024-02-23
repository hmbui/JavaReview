package com.velotronix;

import java.util.*;


/**
 * Implementation of the Calculator class.
 * 
 * Credits: 
 * - Gabriel Solomon <Gabriel.Solomon@sjcc.edu>, for the menu display and
 *      command input.

 * 
 * Author: Hai Bui
 * Created: Feb 22, 2024
 */


 /**
  * Class Calculator
  */
public class Calculator {
    private float firstNumber;
    private float secondNumber;
    private Character command;

    public Calculator() {
        firstNumber = 0;
        secondNumber = 0;

        command = ' ';
    }


    public void run(Scanner scanner) {
        while(command != 'q') {
            printMenu();
            System.out.println("Enter a command: ");
            command = getMenuCommand(scanner);
            executeCommand(scanner, command);

    }

    /**
     * Print a menu line
     */
    private void printMenuLine() {
        System.out.println(
            "----------------------------------------------------------");
    }

    /**
     * Print a menu command item.
     * @param A one-character command.
     * @param The description of the command.
     */
    private void printMenuItem(Character command, String description) {
        System.out.printf("%s\t%s\n", command, description);
    }

    /**
     * Print the calculator's command menu.
     */
    private void printMenu() {
        printMenuLine();
        System.out.println("ChavviCalc");
        printMenuLine();

        printMenuItem('a', "Enter a value for A");
        printMenuItem('b', "Enter a value for B");
        printMenuItem('+', "Add");
        printMenuItem('-', "Subtract");
        printMenuItem('*', "Multiply");
        printMenuItem('/', "Divide");
        printMenuItem('c', "Clear");
        printMenuItem('q', "Quit");

        printMenuLine();
    }

    /**
     * Read the first character of the user's console input as the menu command.
     * @param scanner The input reader object.
     * @return The one-character user's command.
     */
    private Character getMenuCommand(Scanner scanner) {
        Character command = ' ';
        
        String rawInput = scanner.nextLine();
        if(rawInput.length() > 0) {
            rawInput = rawInput.toLowerCase();
            command = rawInput.charAt(0);
        }

        return command;
    }

    private float getInputValue(Scanner scanner, String prompt) {
        float value = 0;

        System.out.print(prompt);
        try {
            value = scanner.nextFloat();
            if(value % 1 == 0) {
                throw new InputMismatchException("Wrong type. Expecting a float.");
            }
        } catch(InputMismatchException exception) {
            System.out.println(exception.toString());
            scanner.next();
        }
        return value;
    }

    private Boolean executeCommand(Scanner scanner, Character command) {
        Boolean isSuccessful = true;
        
        switch(command) {
            case 'a':
                firstNumber = getInputValue(scanner, "Enter value for A");               
                break;
            case 'b':
                firstNumber = getInputValue(scanner, "Enter value for B");
                break;
            case '+':
                System.out.printf("%f + %f = %.3f%n ", firstNumber, secondNumber, firstNumber + secondNumber);
                break;
            case '-':
                System.out.printf("%f - %f = %.3f%n ", firstNumber, secondNumber, firstNumber - secondNumber);
                break;
            case '*':
                System.out.printf("%f * %f = %.3f%n ", firstNumber, secondNumber, firstNumber * secondNumber);
                break;
            case '/':
                System.out.printf("%f / %f = %.3f%n ", firstNumber, secondNumber, firstNumber / secondNumber);
                break;
            case 'c':
                firstNumber = 0;
                secondNumber = 0;

                System.out.println("A and B values are cleared.");
                break;
            case 'q':
                System.out.println("Quitting...Thank you for using ChavviCalc.");
                break;
            default:
                System.out.println("ERROR: Unknown command. Please retry.")
                isSuccessful = false;
        }

        return isSuccessful;
    }
}
