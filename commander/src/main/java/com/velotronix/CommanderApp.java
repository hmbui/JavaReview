package com.velotronix;

import java.util.Random;
import java.util.Scanner;

import org.json.simple.*;

import com.velotronix.JSONFile;
import com.velotronix.Commander;

public class CommanderApp {
  public static void main(String[] args) {
    Commander commander = new Commander();

    Scanner scanner = new Scanner(System.in);
    commander.run(scanner);

    scanner.close();
  }
}
