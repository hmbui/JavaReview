/**
 * The Commander App.
 * Author: Hai Bui
 * Created: April 3, 2024.
 * 
 * Credits:
 * - Gabriel Solomon <Gabriel.Solomon@sjcc.edu>: menu and command display code.
 */

package com.velotronix;

import java.util.Scanner;

/**
 * The Commander App.
 * Initiate a Commander object, giving the path to a JSON file that contains all
 * the commands that can be issued. Then, display a user's menu and input
 * the user's commands.
 */
public class CommanderApp {
  public static void main(String[] args) {
    Commander commander = new Commander(
        "/Users/haib/code/JavaReview/commander/src/main/java/com/velotronix/commands.json");

    Scanner scanner = new Scanner(System.in);
    commander.run(scanner);

    scanner.close();
  }
}
