package ru.astondevs.utils.collections;

import ru.astondevs.projects.hypersort.model.Animal;
import ru.astondevs.projects.hypersort.model.Barrel;
import ru.astondevs.projects.hypersort.model.Human;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TimSort {

    private static final int MIN_RUN_SIZE = 32;

    public static <T extends Comparable<T>> void sort(T[] arr, boolean isUsual, Comparator<T> comparator) {

        for (int start = 0; start < arr.length; start += MIN_RUN_SIZE) {
            int end = Math.min((start + MIN_RUN_SIZE - 1), (arr.length - 1));
            insertionSort(arr, start, end, isUsual, comparator);
        }

        for (int size = MIN_RUN_SIZE; size < arr.length; size = 2 * size) {
            for (int left = 0; left < arr.length; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (arr.length - 1));

                if (mid < right) {
                    merge(arr, left, mid, right, isUsual, comparator);
                }
            }
        }
    }

    private static <T extends Comparable<T>> void insertionSort(T[] arr, int start, int end, boolean isUsual, Comparator<T> comparator) {
        for (int i = start; i <= end; i++) {
            int j = i;
            if (isUsual) {
                while (j > 0 && arr[j].compareTo(arr[j - 1]) < 0) {
                    T temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    j--;
                }
            } else {
                while (j > 0 && (comparator.compare(arr[j], arr[j - 1]) < 0)) {
                    T temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    j--;
                }
            }
        }
    }

    private static <T extends Comparable<T>> void merge(T[] array, int left, int mid, int right, boolean isUsual, Comparator<T> comparator) { //слияние отсортированных массивов
        int len1 = mid - left + 1;
        int len2 = right - mid;

        T[] leftArray = Arrays.copyOfRange(array, left, left + len1);
        T[] rightArray = Arrays.copyOfRange(array, mid + 1, mid + 1 + len2);

        int i = 0, j = 0, k = left;

        if (isUsual) {
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
        } else {
            while (i < len1 && j < len2) {
                if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                    array[k] = leftArray[i];
                    i++;
                } else {
                    array[k] = rightArray[j];
                    j++;
                }
                k++;
            }
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

    private static <T extends Comparable<T>> boolean isEven(T element) {

        boolean result = false;
        if (element instanceof Human) result = ((Human) element).getAge() % 2 == 0;
        if (element instanceof Barrel) result = ((Barrel) element).getVolume() % 2 == 0;
        if (element instanceof Animal) {
            throw new IllegalArgumentException("Animal have not int value");
        }
        return result;
    }

    public static <T extends Comparable<T>> void sortEvenValues(T[] arr, boolean isUsual, Comparator<T> comparator) {

        int evenCount = 0;

        for (T element : arr) {
            if (isEven(element)) {
                evenCount++;
            }
        }
        @SuppressWarnings("uncheked")
        T[] evenValues = (T[]) Array.newInstance(arr.getClass().getComponentType(), evenCount);

        evenCount = 0;

        for (T element : arr) {
            if (isEven(element)) {
                evenValues[evenCount] = element;
                evenCount++;
            }
        }

        TimSort.sort(evenValues, isUsual, comparator);


        int evenIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (isEven(arr[i])) {
                arr[i] = evenValues[evenIndex];
                evenIndex++;
            }
        }
    }
}

