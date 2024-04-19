package com.velotronix;

import java.util.*;
import org.json.simple.*;

public class Pyramids {
  // I've used two arrays here for O(1) reading of the pharaohs and pyramids.
  // other structures or additional structures can be used
  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;
  private Character command;

  // constructor to initialize the app and read commands
  public Pyramids(String pharaohFilePath, String pyramidFilePath) {
    command = ' ';

    // read egyptian pharaohs
    JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFilePath);

    // create and intialize the pharaoh array
    initializePharaoh(pharaohJSONArray);

    // read pyramids
    JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFilePath);

    // create and initialize the pyramid array
    initializePyramid(pyramidJSONArray);
  }

  /**
   * Run the Pyramids operations. Display the menu, take the user's command
   * inputs, and
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

  // initialize the pharaoh array
  private void initializePharaoh(JSONArray pharaohJSONArray) {
    // create array and hash map
    pharaohArray = new Pharaoh[pharaohJSONArray.size()];

    // initalize the array
    for (int i = 0; i < pharaohJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pharaohJSONArray.get(i);

      // parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      Integer begin = toInteger(o, "begin");
      Integer end = toInteger(o, "end");
      Integer contribution = toInteger(o, "contribution");
      String hieroglyphic = o.get("hieroglyphic").toString();

      // add a new pharoah to array
      Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
      pharaohArray[i] = p;
    }
  }

  // initialize the pyramid array
  private void initializePyramid(JSONArray pyramidJSONArray) {
    // create array and hash map
    pyramidArray = new Pyramid[pyramidJSONArray.size()];

    // initalize the array
    for (int i = 0; i < pyramidJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pyramidJSONArray.get(i);

      // parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
      String[] contributors = new String[contributorsJSONArray.size()];
      for (int j = 0; j < contributorsJSONArray.size(); j++) {
        String c = contributorsJSONArray.get(j).toString();
        contributors[j] = c;
      }

      // add a new pyramid to array
      Pyramid p = new Pyramid(id, name, contributors);
      pyramidArray[i] = p;
    }
  }

  // get a integer from a json object, and parse it
  private Integer toInteger(JSONObject o, String key) {
    String s = o.get(key).toString();
    Integer result = Integer.parseInt(s);
    return result;
  }

  /**
   * Print a menu line
   */
  private void printMenuLine() {
    System.out.println(
        "--------------------------------------------------------------------------");
  }

  /**
   * Print a menu command item.
   * 
   * @param command     one-character command.
   * @param description The description of the command.
   */
  private void printMenuItem(Character command, String description) {
    System.out.printf("%s\t%s\n", command, description);
  }

  /**
   * Print the calculator's command menu.
   */
  private void printMenu() {
    printMenuLine();
    System.out.println("Egyptian Pyramids App");
    printMenuLine();

    System.out.printf("Command\t\tDescription\n");
    System.out.printf("-------\t\t---------------------------------------\n");
    printMenuCommand('1', "List all the pharoahs");
    printMenuCommand('2', "Display a specific pharaoah");
    printMenuCommand('3', "List all the pyramids ");
    printMenuCommand('4', "Display a specific pyramid");
    printMenuCommand('5', "Display a list of requested pyramids");
    printMenuCommand('q', "Quit");

    printMenuLine();
  }

  // print all pharaohs
  private void printAllPharaoh() {
    for (int i = 0; i < pharaohArray.length; i++) {
      printMenuLine();
      pharaohArray[i].print();
      printMenuLine();
    }
  }

  private Boolean executeCommand(Scanner scan, Character command) {
    Boolean success = true;

    switch (command) {
      case '1':
        printAllPharaoh();
        break;
      case 'q':
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        break;
      default:
        System.out.println("ERROR: Unknown commmand");
        success = false;
    }

    return success;
  }

  private static void printMenuCommand(Character command, String desc) {
    System.out.printf("%s\t\t%s\n", command, desc);
  }

}
