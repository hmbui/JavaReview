/**
 * Implementing and testing the Commander App.
 * Author: Hai Bui
 * Created: April 3, 2024.
 * Credits:
 * - Gabriel Solomon <Gabriel.Solomon@sjcc.edu>: The Command Menu, test data,
 *      and test code.
 */

package com.velotronix;

import java.util.Random;
import java.util.Scanner;

import org.json.simple.*;

public class Commander {
  private Character command;
  private String[] commands;

  public Commander(String commandFilePath) {
    command = ' ';
    commands = readCommands(commandFilePath);
  }

  /**
   * Parse the JSON array to the String array.
   * 
   * @param commandArray
   * @return
   */
  private String[] readCommands(String commandFilePath) {
    JSONArray commandJSONArray = JSONFile.readArray(commandFilePath);
    String[] arr = new String[commandJSONArray.size()];

    // Get the names from JSON object
    for (int i = 0; i < commandJSONArray.size(); i++) {
      String command = commandJSONArray.get(i).toString();
      arr[i] = command;
    }
    return arr;
  }

  /**
   * Print the command array.
   * 
   * @param commandArray
   */
  private void printCommands() {
    System.out.printf("\nNumber\tCommand\n");
    System.out.printf("------\t---------------\n");
    for (int i = 0; i < commands.length; i++) {
      System.out.printf("%04d\t%s\n", i, commands[i]);
    }
  }

  /**
   * Run the calculator. Display the menu, take the user's command inputs, and
   * performs the operations.
   * 
   * @param scanner Where to read the user's input.
   */
  public void run(Scanner scanner) {
    while (command != 'q') {
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
   * 
   * @param command     A one-character command.
   * @param description The description of the command.
   */
  private void printMenuItem(Character command, String description) {
    System.out.printf("%s\t%s\n", command, description);
  }

  private void printMenu() {
    printMenuLine();
    System.out.println("\nGeneral Cavazos Commander App");
    printMenuLine();

    printMenuItem('i', "Issue a command");
    printMenuItem('l', "List all commands");
    printMenuItem('u', "Undo the last command that was issued");
    printMenuItem('r', "Redo the last command that was issued");
    printMenuItem('q', "Quit");
    printMenuLine();
  }

  /**
   * Issue a random command
   * 
   * @param commandArray
   * @param numCommand
   */
  private void randomCommand() {
    Random rand = new Random();
    int randIndex = rand.nextInt(commands.length);
    System.out.printf("[COMMAND ISSUED]: %s\n", commands[randIndex]);
  }

  /**
   * Read the first character of the user's console input as the menu command.
   * 
   * @param scanner The input reader object.
   * @return The one-character user's command.
   */
  private Character getMenuCommand(Scanner scanner) {
    Character command = ' ';

    String rawInput = scanner.nextLine();
    if (rawInput.length() > 0) {
      rawInput = rawInput.toLowerCase();
      command = rawInput.charAt(0);
    }

    return command;
  }

  /**
   * Perform the command.
   * 
   * @param scanner Where to obtain the user's inputs when needed.
   * @param command The user's command.
   * @return True if the operation is successful; False otherwise.
   */
  private boolean executeCommand(Scanner scanner, Character command) {
    boolean isSuccessful = true;

    switch (command) {
      case 'i':
        randomCommand();
        break;
      case 'l':
        printCommands();
        break;
      case 'u':
        break;
      case 'r':
        break;
      case 'q':
        System.out.println("Thank you, General.\n");
        break;
      default:
        System.out.println("ERROR: Unknown command.");
        isSuccessful = false;
    }

    return isSuccessful;
  }
}
