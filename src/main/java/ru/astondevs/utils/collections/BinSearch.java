package ru.astondevs.utils.collections;

public class BinSearch {
    public static <T extends Comparable<T>> int binarySearch(T[] array, T key) {

        if (array == null || key == null) {
            throw new IllegalArgumentException("Нулевое значение");
        }

        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int result = array[mid].compareTo(key);

            if (result == 0) {
                return mid;
            } else if (result < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}

