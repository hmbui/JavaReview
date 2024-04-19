package com.velotronix;

import java.util.*;
import org.json.simple.*;

public class Pyramids {
  // I've used two arrays here for O(1) reading of the pharaohs and pyramids.
  // other structures or additional structures can be used
  private HashMap<String, Pharaoh> pharaohs = new HashMap<String, Pharaoh>();
  private HashMap<String, Pharaoh> pharaohsByGlyphs = new HashMap<String, Pharaoh>();

  private HashMap<String, Pyramid> pyramids = new HashMap<String, Pyramid>();

  private HashSet<String> requestedPyramids = new HashSet<String>();
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

  private String getIdCommand(Scanner scanner, String prompt) {
    String id = new String();
    while (id.isEmpty()) {
      System.out.println(prompt);
      id = scanner.nextLine();
    }
    return id;
  }

  // initialize the pharaoh array
  private void initializePharaoh(JSONArray pharaohJSONArray) {
    // Initalize the JSON array
    for (int i = 0; i < pharaohJSONArray.size(); i++) {
      JSONObject o = (JSONObject) pharaohJSONArray.get(i);

      // Parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      Integer begin = toInteger(o, "begin");
      Integer end = toInteger(o, "end");
      Integer contribution = toInteger(o, "contribution");
      String hieroglyphic = o.get("hieroglyphic").toString();

      Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
      pharaohs.put(Integer.toString(i), p);
      pharaohsByGlyphs.put(hieroglyphic, p);
    }
  }

  // initialize the pyramid array
  private void initializePyramid(JSONArray pyramidJSONArray) {
    // Initalize the JSON array
    for (int i = 0; i < pyramidJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pyramidJSONArray.get(i);

      // Parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
      String[] contributors = new String[contributorsJSONArray.size()];
      for (int j = 0; j < contributorsJSONArray.size(); j++) {
        String c = contributorsJSONArray.get(j).toString();
        contributors[j] = c;
      }

      Pyramid p = new Pyramid(id, name, contributors);
      pyramids.put(Integer.toString(i), p);
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
        "--------------------------------------------------------------------------------------");
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

  /**
   * Print all pharaohs
   */
  private void printAllPharaohs() {
    for (Pharaoh pharaoh : pharaohs.values()) {
      printMenuLine();
      pharaoh.print();
      printMenuLine();
    }
  }

  private void displayPharaoh(String id) {
    printMenuLine();
    pharaohs.get(id).print();
    printMenuLine();
  }

  private void printAllPyramids() {
    for (Pyramid pyramid : pyramids.values()) {
      printMenuLine();

      String[] contributors = pyramid.getContributors();
      pyramid.print();

      int totalContributions = 0;
      for (int i = 0; i < contributors.length; i++) {
        String glyphId = contributors[i];
        Pharaoh pharaoh = pharaohsByGlyphs.get(glyphId);
        int contribution = pharaoh.getContribution();

        totalContributions += contribution;

        System.out.printf("\tContributor %d: %s %d gold coins\n", i + 1, pharaoh.getName(), contribution);
      }
      System.out.printf("\tTotal contribution: %d\n", totalContributions);
      printMenuLine();
    }
  }

  private Boolean executeCommand(Scanner scanner, Character command) {
    Boolean isSuccessful = true;

    switch (command) {
      case '1':
        printAllPharaohs();
        break;
      case '2':
        String id = getIdCommand(scanner, "Enter the Pharaoh ID: ");
        displayPharaoh(id);
        break;
      case '3':
        printAllPyramids();
        break;
      case 'q':
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        break;
      default:
        System.out.println("ERROR: Unknown commmand");
        isSuccessful = false;
    }

    return isSuccessful;
  }

  private static void printMenuCommand(Character command, String desc) {
    System.out.printf("%s\t\t%s\n", command, desc);
  }

}
