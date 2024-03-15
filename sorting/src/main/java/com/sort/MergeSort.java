package com.sort;

import org.json.simple.*;

public class MergeSort {

  private static int count = 0;

  public static void main(String[] args) {
    // String fileName = "/Users/haib/code/JavaReview/sorting/src/main/java/com/sort/cupcake_3906.json";
    // String fileName =
    //   "/Users/haib/code/JavaReview/sorting/src/main/java/com/sort/cupcake_test_10.json";
    String fileName =
      "/Users/haib/code/JavaReview/sorting/src/main/java/com/sort/cupcake_test_5.json";

    // read cupcake names
    JSONArray cupcakeArray = JSONFile.readArray(fileName);
    String[] cupcakeNameArray = nameArray(cupcakeArray);
    System.out.println(cupcakeNameArray);

    // print unsorted list
    System.out.println("----- Unsorted array -----");
    print(cupcakeNameArray);

    // sort
    mergeSort(cupcakeNameArray);

    // print sorted list
    System.out.println("----- Sorted array----- ");
    print(cupcakeNameArray);

    System.out.println("----- Statistics -----");
    System.out.printf("Size of array = %d\n", cupcakeNameArray.length);
    System.out.printf("Count = %d\n", count);
  }

  // print array
  public static void print(String[] arr) {
    System.out.printf("Number\tName\n");
    System.out.printf("------\t---------------\n");
    for (int i = 0; i < arr.length; i++) {
      System.out.printf("%04d\t%s\n", i, arr[i]);
    }
  }

  // print array
  public static void print(String[] arr, int left, int right) {
    System.out.printf("Number\tName\n");
    System.out.printf("------\t---------------\n");
    for (int i = left; i < right; i++) {
      System.out.printf("%04d\t%s\n", i, arr[i]);
    }
  }

  // get array of cupcake names
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

  // merge sort array, O(n logBase2 n)
  public static void mergeSort(String[] arr) {
    // call recurisve merge method with the array
    int left = 0;
    int right = arr.length - 1;
    sort(arr, left, right);
  }

  // merge sort
  public static void sort(String[] arr, int left, int right) {
    // if left >= right, we are done
    if (left >= right) {
      return;
    }
    // split the array in half, sort each half recursively, then merge
    int middle = left + ((right - left) / 2);

    int leftStart = left;
    int leftEnd = middle;
    int rightStart = middle + 1;
    int rightEnd = right;

    // System.out.println("merge sort left");
    // print(arr, leftStart, leftEnd);
    sort(arr, leftStart, leftEnd);

    // System.out.println("merge sort right");
    // print(arr, rightStart, rightEnd);
    sort(arr, rightStart, rightEnd);

    // merge the two sorted arrays
    merge(arr, left, middle, right);
    // System.out.println("after merge");
    // print(arr, left, right);
  }

  // merge two arrays indicated by [left to middle] and [middle+1 to right]
  public static void merge(String[] arr, int left, int middle, int right) {
    // compare all the values adding them in to temp
    String[] temp = new String[right - left + 1];

    // indices for left and right
    int l = left;
    int r = middle + 1;

    // merge the elements such that lower elements go into temp first
    int t = 0;
    while ((l <= middle) && (r <= right)) {
      if ((arr[l].compareTo(arr[r])) < 0) {
        // add left element to temp
        temp[t] = arr[l];
        t++;
        l++;
      } else {
        // add right element to temp
        temp[t] = arr[r];
        t++;
        r++;
      }
    }

    // add the rest of the remaining elements (empty out the left and right arrays)

    // add any remaining left elements in to temp
    while (l <= middle) {
      // add left element to temp
      temp[t] = arr[l];
      t++;
      l++;
    }

    // add any remaining right elements in to temp
    while (r <= right) {
      // add right element to temp
      temp[t] = arr[r];
      t++;
      r++;
    }

    // copy the temp elements in to arr starting at left, element by element
    for (int i = 0; i < temp.length; i++) {
      arr[i + left] = temp[i];
    }

    // merge is a O(n) operation so we add n to the count
    count += right - left + 1;
  }
}
