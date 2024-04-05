/**
 * Implementing of the Commander class.
 * Author: Hai Bui
 * Created: April 3, 2024.
 * 
 * Credits:
 * - Gabriel Solomon <Gabriel.Solomon@sjcc.edu>: menu and command display code.
 */

package com.velotronix;

import java.util.Stack;
import java.util.Random;
import java.util.Scanner;

import org.json.simple.*;

/**
 * The Commander class.
 * - Can read a list of commands it can issue from a JSON file.
 * - Can issue a random command from the command list.
 * - Can undo the last command that was issued, down to the entire
 *    command history as a stack until it's empty.
 * - Can redo the last command that was issued, down to the entire
 *    command history as a stack until it's empty.
 */
public class Commander {
  private Character command;
  private String[] availableCommands;

  private Stack<String> undoCommands;
  private Stack<String> redoCommands;

  public Commander(String commandFilePath) {
    command = ' ';
    readCommands(commandFilePath);

    undoCommands = new Stack<String>();
    redoCommands = new Stack<String>();
  }

  /**
   * Parse the JSON array to the String array.
   * 
   * @param commandFilePath The complete file path to the JSON file that stores
   *  a complete list of available commands.
   */
  private void readCommands(String commandFilePath) {
    JSONArray commandJSONArray = JSONFile.readArray(commandFilePath);
    availableCommands = new String[commandJSONArray.size()];

    // Get the names from JSON object
    for (int i = 0; i < commandJSONArray.size(); i++) {
      String command = commandJSONArray.get(i).toString();
      availableCommands[i] = command;
    }
  }

  /**
   * Print the command array.
   * 
   * @param commandArray The command collection to print.
   */
  private void printCommands() {
    System.out.printf("\nNumber\tCommand\n");
    System.out.printf("------\t---------------\n");
    for (int i = 0; i < availableCommands.length; i++) {
      System.out.printf("%04d\t%s\n", i, availableCommands[i]);
    }
  }

  /**
   * Display the menu, take the user's command inputs, and
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
   * @param command A one-character command.
   * @param description The description of the command.
   */
  private void printMenuItem(Character command, String description) {
    System.out.printf("%s\t%s\n", command, description);
  }

  /**
   * Pop a command from the source stack and push that to the destination stack
   * as part of the undo/redo mechanism. Also print out the command popped
   * from the source stack.
   * @param source The source stack to pop the command from.
   * @param destination The destination stack to push the command to.
   * @param messageHeader The header of the message that prints out the command.
   * @param emptySourceMsg The message to print out if the source stack is empty.
   */
  private void shuffleCommand(
    Stack<String> source, Stack<String> destination, String messageHeader,
    String emptySourceMsg) {
      if(source.size() > 0) {
        String cmd = source.pop();
        destination.push(cmd);

        System.out.printf("[%s] %s\n", messageHeader, cmd);
      }
      else {
        System.out.println(emptySourceMsg + " Issue a new command.");
      }
  }

  /**
   * Print out the user's menu.
   */
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
   * Issue a command, taken at random from the available command collection.
   * Also add that command to the undo command collection as part of the
   * undo/redo mechanism.
   * 
   */
  private void issueCommand() {
    Random rand = new Random();
    int randIndex = rand.nextInt(availableCommands.length);
    
    String command = availableCommands[randIndex];
    undoCommands.push(command);

    System.out.printf("[COMMAND ISSUED]: %s\n", command);
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
        issueCommand();
        break;
      case 'l':
        printCommands();
        break;
      case 'u':
        shuffleCommand(undoCommands, redoCommands, "UNDO COMMAND ISSUED", "ERROR: There is no command to undo.");
        break;
      case 'r':
        shuffleCommand(redoCommands, undoCommands, "REDO COMMAND ISSUED", "ERROR: There is no command to redo.");
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
