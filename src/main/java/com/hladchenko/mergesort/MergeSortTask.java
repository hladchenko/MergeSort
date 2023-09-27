package com.hladchenko.mergesort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask<T extends Comparable<T>> extends RecursiveAction {

  List<T> list;
  int variant;

  public MergeSortTask(List<T> list) {
    this.list = list;
    variant = 1;
  }

  public MergeSortTask(List<T> list, int variant) {
    this.list = list;
    this.variant = variant;
  }

  @Override
  protected void compute() {
    if (list.size() > 1) {

      var left = new ArrayList<>(list.subList(0, list.size() / 2));
      var right = new ArrayList<>(list.subList(list.size() / 2, list.size()));

      var leftTask = new MergeSortTask<>(left);
      var rightTask = new MergeSortTask<>(right);

      switch (variant) {
        case 1 -> {
          leftTask.fork();
          rightTask.fork();
          leftTask.join();
          rightTask.join();
        }
        case 2 -> {
          leftTask.compute();
          rightTask.compute();
        }
        case 3 -> {
          leftTask.fork();
          leftTask.join();
          rightTask.compute();
        }
      }

      merge(list, left, right);
    }
  }

  private void merge(List<T> list, List<T> left, List<T> right) {
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
