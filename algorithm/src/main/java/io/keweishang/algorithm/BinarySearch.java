package io.keweishang.algorithm;

/**
 * @author kshang
 */
public class BinarySearch {

    /**
     * Binary search the key in a sorted array.
     *
     * @param a array to be searched
     * @param key the search key
     * @return the index of the key if it exists; the index where to insert the key if it does not exist.
     */
    public static int rank(int[] a, int key) {
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] < key) lo = mid + 1;
            else if (a[mid] > key) hi = mid - 1;
            else return mid; // find the key
        }
        return lo;
    }
}