package com.hladchenko.mergesort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {

  public static void main(String[] args) {

    System.out.println("------ Sort Check ------");
    int[] test = new int[]{10, 5, 20, 30, 2, 7, 9, -2, 15};
    SimpleMergeSort.sort(test);
    System.out.println("SimpleMerge: " + Arrays.toString(test));

    ArrayList<Integer> integers = new ArrayList<>(List.of(400, 12, -6, 27, 10, 9, 11));
    ListMergeSort.sort(integers);
    System.out.println("ListMerge: " + integers);

    ArrayList<Integer> integers2 = new ArrayList<>(List.of(70, -30, 2, 4, -11, 25));
    MergeSortTask<Integer> mergeSortTaskTest = new MergeSortTask<>(integers2);
    ForkJoinPool.commonPool().invoke(mergeSortTaskTest);
    System.out.println("ForkMerge: " + integers2);

    int n = 10_000_000;
    System.out.println("\n------  Time Check for " + n + " elements ------");

    int[] arr = generateArray(n);

    List<Integer> list = convertToArrayList(arr);
    List<Integer> list1 = convertToArrayList(arr);
    List<Integer> list2 = convertToArrayList(arr);
    List<Integer> list3 = convertToArrayList(arr);
    MergeSortTask<Integer> mergeSortTask = new MergeSortTask<>(list1, 1);
    MergeSortTask<Integer> mergeSortTask2 = new MergeSortTask<>(list2, 2);
    MergeSortTask<Integer> mergeSortTask3 = new MergeSortTask<>(list3, 3);

    long start = System.nanoTime();
    SimpleMergeSort.sort(arr);
    long simpleSortTime = System.nanoTime() - start;

    start = System.nanoTime();
    ListMergeSort.sort(list);
    long listSortTime = System.nanoTime() - start;

    start = System.nanoTime();
    ForkJoinPool.commonPool().invoke(mergeSortTask);
    long forkJoinTime = System.nanoTime() - start;

    start = System.nanoTime();
    ForkJoinPool.commonPool().invoke(mergeSortTask2);
    long forkJoinTime1 = System.nanoTime() - start;

    start = System.nanoTime();
    ForkJoinPool.commonPool().invoke(mergeSortTask3);
    long forkJoinTime2 = System.nanoTime() - start;

    System.out.println("SimpleMerge Time                : " + simpleSortTime);
    System.out.println("ListMerge Time                  : " + listSortTime);
    System.out.println("ForkJoinMerge (fork + join)     : " + forkJoinTime);
    System.out.println("ForkJoinMerge (compute)         : " + forkJoinTime1);
    System.out.println("ForkJoinMerge (fork + compute)  : " + forkJoinTime2);

    System.out.println("\n --------- Fork Join Merge Demo ---------");
    List<Integer> listFork = new ArrayList<>(List.of(12, 25, 37, 11, 16, -1, -60, 4));
    MergeSortTask<Integer> mergeSortDemoTask = new MergeSortTask<>(listFork, 3);

    start = System.nanoTime();
    ForkJoinPool.commonPool().invoke(mergeSortDemoTask);
    long end = System.nanoTime();

    System.out.println("Sorted: " + listFork);
    System.out.println("Fork Join Merge Time: " + (end - start));
  }

  private static int[] generateArray(int n) {
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = new Random().nextInt(10);
    }
    return arr;
  }

  private static ArrayList<Integer> convertToArrayList(int[] arr) {
    ArrayList<Integer> list = new ArrayList<>();
    for (var item : arr) {
      list.add(item);
    }
    return list;
  }
}