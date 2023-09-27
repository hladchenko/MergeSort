package com.hladchenko.mergesort;

import java.util.ArrayList;
import java.util.List;

public class ListMergeSort {

  public static <T extends Comparable<T>> void sort(List<T> list) {
    if (list.size() > 1) {

      List<T> left = new ArrayList<>(list.subList(0, list.size() / 2));
      List<T> right = new ArrayList<>(list.subList(list.size() / 2, list.size()));

      sort(left);
      sort(right);

      merge(list, left, right);
    }
  }

  private static <T extends Comparable<T>> void merge(List<T> list, List<T> left, List<T> right) {
    int i = 0, j = 0, k = 0;

    while (i < left.size() && j < right.size()) {
      if (left.get(i).compareTo(right.get(j)) < 0) {
        list.set(k++, left.get(i++));
      } else {
        list.set(k++, right.get(j++));
      }
    }

    while (i < left.size()) {
      list.set(k++, left.get(i++));
    }

    while (j < right.size()) {
      list.set(k++, right.get(j++));
    }
  }
}
