package com.velotronix;

import java.util.Scanner;

public class PyramidsApp {
    public static void main(String[] args) {
        String pharaohFilePath = "/Users/haib/code/JavaReview/pyramids/src/main/java/com/velotronix/pharaoh.json";
        String pyramidFilePath = "/Users/haib/code/JavaReview/pyramids/src/main/java/com/velotronix/pyramid.json";

        Pyramids pyramids = new Pyramids(pharaohFilePath, pyramidFilePath);

        Scanner scanner = new Scanner(System.in);
        pyramids.run(scanner);

        scanner.close();
    }
}
