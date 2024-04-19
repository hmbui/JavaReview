/**
 * Implementation of the Pyramids App Engine.
 * 
 * 
 * Author: Hai Bui
 * 
 *  
 * Credits: 
 * - Gabriel Solomon <Gabriel.Solomon@sjcc.edu>, for the utility code.
 * 
 * Created: April 18, 2024
 */

package com.velotronix;

import java.util.*;
import org.json.simple.*;

public class Pyramids {
  // The first HashMap is for locating a pharaoh by String ID; the second is by
  // hieroglyphic (referred to as 'glyphs' from this point forward)
  private HashMap<String, Pharaoh> pharaohs = new HashMap<String, Pharaoh>();
  private HashMap<String, Pharaoh> pharaohsByGlyphs = new HashMap<String, Pharaoh>();

  // Locating a pyramid by a String ID
  private HashMap<String, Pyramid> pyramids = new HashMap<String, Pyramid>();

  // Recording all the unique requested pyramid IDs
  private HashSet<String> requestedPyramids = new HashSet<String>();

  // The menu command input by the user
  private Character command;

  // Constructor to initialize the app and read commands
  public Pyramids(String pharaohFilePath, String pyramidFilePath) {
    command = ' ';

    // Read the pharaoh data from the JSON file
    JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFilePath);

    // Parse the pharaoh JSON data into the HashMaps
    initializePharaoh(pharaohJSONArray);

    // Read the pyramid data from the JSON file
    JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFilePath);

    // Parse the pyramid JSON data into the HashMaps
    initializePyramid(pyramidJSONArray);
  }

  /**
   * Run the Pyramids operations. Display the menu, take the user's command
   * inputs, and performs the operations.
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

  /**
   * Get the user's input for a pharaoh's ID, or for the ID of a pyramid
   * 
   * @param scanner The input reader object.
   * @param prompt The instruction to display to the user.
   * @return The ID as input by the user.
   */
  private String getIdCommand(Scanner scanner, String prompt) {
    String id = new String();
    while (id.isEmpty()) {
      System.out.println(prompt);
      id = scanner.nextLine();
    }
    return id;
  }

  /**
   * Read the pharaoh JSON data and parse it into Pharaoh objects. Then, add them into the HashMaps.
   * @param pharaohJSONArray The pharaoh JSON data.
   */
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

  /**
   * Read the pyramid JSON data and parse it into Pyramid objects. Then, add them
   * into the HashMap.
   * 
   * @param pharaohJSONArray The pyramid JSON data.
   */
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

  /**
   * Get a integer from a json object, and parse it
   * 
   * @param o
   * @param key
   * @return
   */
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
   * Print the command menu
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

  /**
   * Locate and display data about a specific pharaoh.
   * @param id The pharaoh's ID.
   */
  private void displayPharaoh(String id) {
    printMenuLine();
    pharaohs.get(id).print();
    printMenuLine();
  }

  /**
   * Locate and display data about a specific pyramid.
   * 
   * @param id The ID of a pyramid.
   */
  private void displayPyramid(String id) {
    printMenuLine();
    
    Pyramid pyramid = pyramids.get(id);
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

  /**
   * Display all the pyramids.
   */
  private void printAllPyramids() {
    for (String id : pyramids.keySet()) {      
      displayPyramid(id);      
    }
  }

  /**
   * Display the unique IDs and names of all pyramids previously requested.
   */
  private void displayRequestedPyramids() {
    System.out.printf("ID\t\tName\n");
    System.out.printf("---\t\t-------\n");

    for(String id : requestedPyramids) {
      System.out.printf("%s\t\t%s\n", id, pyramids.get(id).getName());     
    }
  }

  /**
   * Execute a menu command as input by the user.
   * @param scanner The scanner object to receive the user's input.
   * @param command The user's input command
   * @return True if the command execution was successful; False otherwise.
   */
  private Boolean executeCommand(Scanner scanner, Character command) {
    Boolean isSuccessful = true;
    String id = new String();
    
    switch (command){
      case '1':
        printAllPharaohs();
        break;
      case '2':
        id = getIdCommand(scanner, "Enter a Pharaoh ID: ");
        displayPharaoh(id);
        break;
      case '3':
        printAllPyramids();
        break;
      case '4':
        id = getIdCommand(scanner, "Enter a Pyramid ID: ");
        displayPyramid(id);
        requestedPyramids.add(id.toString());
        break;
      case '5':
        displayRequestedPyramids();
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
