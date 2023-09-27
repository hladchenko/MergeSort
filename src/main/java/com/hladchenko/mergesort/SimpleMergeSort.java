package com.hladchenko.mergesort;

import java.util.Arrays;

public class SimpleMergeSort {

  public static void sort(int[] arr) {
    if (arr.length > 1) {

      int middle = arr.length / 2;

      int[] left = Arrays.copyOfRange(arr, 0, middle);
      int[] right = Arrays.copyOfRange(arr, middle, arr.length);

      sort(left);
      sort(right);

      merge(arr, left, right);
    }
  }

  private static void merge(int[] arr, int[] left, int[] right) {
    int i = 0, j = 0, k = 0;

    while (i < left.length && j < right.length) {
      if (left[i] < right[j]) {
        arr[k++] = left[i++];
      } else {
        arr[k++] = right[j++];
      }
    }

    System.arraycopy(left, i, arr, k, left.length - i);
    System.arraycopy(right, j, arr, k, right.length - j);
  }
}
