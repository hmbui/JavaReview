package com.velotronix.sort;

import org.json.simple.*;

public class InsertionSort {

    private static int count = 0;

    public static void main(String[] args) {
        String fileName = "/Users/haib/code/JavaReview/sorting/src/main/java/com/sort/cupcake_3906.json";
        //String fileName = "/Users/haib/code/JavaReview/sorting/src/main/java/com/sort/cupcake_test_10.json";
        //String fileName = "/Users/haib/code/JavaReview/sorting/src/main/java/com/sort/cupcake_test_5.json";

        // Read cupcake names
        JSONArray cupcakeArray = JSONFile.readArray(fileName);
        String[] cupcakeNameArray = nameArray(cupcakeArray);
        System.out.println(cupcakeNameArray);

        // Print unsorted list
        System.out.println("----- Unsorted array -----");
        print(cupcakeNameArray);

        // Sort
        insertionSort(cupcakeNameArray);

        // Print sorted list
        System.out.println("----- Sorted array----- ");
        print(cupcakeNameArray);

        System.out.println("----- Statistics -----");
        System.out.printf("Size of array = %d\n", cupcakeNameArray.length);
        System.out.printf("Count = %d\n", count);
    }

    // Print array
    public static void print(String[] arr) {
        System.out.printf("Number\tName\n");
        System.out.printf("------\t---------------\n");
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%04d\t%s\n", i, arr[i]);
        }
    }

    // Print array
    public static void print(String[] arr, int left, int right) {
        System.out.printf("Number\tName\n");
        System.out.printf("------\t---------------\n");
        for (int i = left; i < right; i++) {
            System.out.printf("%04d\t%s\n", i, arr[i]);
        }
    }

    // Get array of cupcake names
    public static String[] nameArray(JSONArray cupcakeArray) {
        String[] arr = new String[cupcakeArray.size()];

        // get names from json object
        for (int i = 0; i < cupcakeArray.size(); i++) {
            JSONObject o = (JSONObject) cupcakeArray.get(i);
            String name = (String) o.get("name");
            arr[i] = name;
        }
        return arr;
    }

    // Insertion Sort an array, O(n^2)
    public static void insertionSort(String[] arr) {
        int length = arr.length;
        for (int i = 1; i < length; i++) {
            String currentElement = arr[i];
            int j = i - 1;

            // For all array elements preceding the current element, if one of
            // such elements is greater than the current element, swap the
            // greater element with the element right behind it
            for (; j >= 0 && arr[j].compareToIgnoreCase(currentElement) > 0; j--) {
                arr[j + 1] = arr[j];
                
                // Increase the count for profiling
                count++;
            }
            arr[j + 1] = currentElement;
            count++;
        }
    }
}
