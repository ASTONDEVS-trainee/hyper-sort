package ru.astondevs.utils.collections;

import java.util.Arrays;

public class TimSort {
    private static final int MIN_RUN_SIZE = 32;

    public static <T extends Comparable<T>> void sort(T[] arr) {

        for (int start = 0; start < arr.length; start += MIN_RUN_SIZE) {
            int end = Math.min((start + MIN_RUN_SIZE - 1), (arr.length - 1));
            insertionSort(arr, start, end);
        }

        for (int size = MIN_RUN_SIZE; size < arr.length; size = 2 * size) {
            for (int left = 0; left < arr.length; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (arr.length - 1));

                if (mid < right) {
                    merge(arr, left, mid, right);
                }
            }
        }
    }

    private static <T extends Comparable<T>> void insertionSort(T[] arr, int start, int end) {
        for (int i = start; i <= end; i++) {
            int j = i;
            while (j > 0 && arr[j].compareTo(arr[j - 1]) < 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    private static <T extends Comparable<T>> void merge(T[] array, int left, int mid, int right) { //слияние отсортированных массивов
        int len1 = mid - left + 1;
        int len2 = right - mid;

        T[] leftArray = Arrays.copyOfRange(array, left, left + len1);
        T[] rightArray = Arrays.copyOfRange(array, mid + 1, mid + 1 + len2);

        int i = 0, j = 0, k = left;

        while (i < len1 && j < len2) {
            if ((leftArray[i].compareTo(rightArray[j])) <= 0) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < len1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < len2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
}

