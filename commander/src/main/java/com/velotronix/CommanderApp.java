package com.velotronix;

import java.util.Scanner;


public class CommanderApp {
  public static void main(String[] args) {
    Commander commander = new Commander(
        "/Users/haib/code/JavaReview/commander/src/main/java/com/velotronix/commands.json");

    Scanner scanner = new Scanner(System.in);
    commander.run(scanner);

    scanner.close();
  }
}
