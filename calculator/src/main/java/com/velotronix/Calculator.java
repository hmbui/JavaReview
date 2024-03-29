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


    /**
     * Run the calculator. Display the menu, take the user's command inputs, and
     * performs the operations.
     * @param scanner Where to read the user's input.
     */
    public void run(Scanner scanner) {
        while(command != 'q') {
            printMenu();
            System.out.print("Enter a command: ");
            command = getMenuCommand(scanner);
            executeCommand(scanner, command);
        }
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
        System.out.printf("A = %.3f\t\tB = %.3f%n", firstNumber, secondNumber);
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

    /**
     * Obtain a numeric value from the user input. Expecting a float.
     * @param scanner Where to read the user's input.
     * @param prompt Instruction to the user -- the target of the input.
     * @return The numeric value, parsed from the user's input.
     */
    private float getInputValue(Scanner scanner, String prompt) {
        float value = 0;

        System.out.print(prompt);        
        String rawInput = scanner.nextLine();        
        if(rawInput.length() > 0) {
            value = Float.parseFloat(rawInput);            
        }

        return value;
    }

    /**
     * Perform the calculator's operations.
     * @param scanner Where to obtain the user's numeric inputs when needed.
     * @param command The user's command, which is the calculator's prompt.
     * @return True if the operation is successful; False otherwise.
     */
    private boolean executeCommand(Scanner scanner, Character command) {
        boolean isSuccessful = true;
        
        switch(command) {            
            case 'a':
                try {
                    firstNumber = getInputValue(scanner, "Enter value for A: ");        
                } catch(NullPointerException | NumberFormatException e) {
                    System.out.println("ERROR: Expected a float.");
                }
                break;            
            case 'b':                
                try {
                    secondNumber = getInputValue(scanner, "Enter value for B: ");        
                } catch(NullPointerException | NumberFormatException e) {
                    System.out.println("ERROR: Expected a float.");
                }
                break;
            case '+':
                firstNumber += secondNumber;
                break;
            case '-':
                firstNumber -= secondNumber;
                break;
            case '*':
                firstNumber *= secondNumber;
                break;
            case '/':
                if(secondNumber == 0) {
                    System.out.println("ERROR: Divide by zero.");
                }
                else {
                    firstNumber /= secondNumber;
                }
                break;
            case 'c':
                firstNumber = 0;
                secondNumber = 0;
                break;
            case 'q':
                System.out.println("Thank you for using ChavviCalc.\n");
                break;
            default:
                System.out.println("ERROR: Unknown command.");
                isSuccessful = false;
        }

        return isSuccessful;
    }
}
