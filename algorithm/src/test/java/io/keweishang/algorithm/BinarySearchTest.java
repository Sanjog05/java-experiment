package io.keweishang.algorithm;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kshang on 27/12/2016.
 */
public class BinarySearchTest {
    private int[] a = {2, 3, 5, 7, 10, 20};

    @Test
    public void testKeyExist() throws Exception {
        int index = BinarySearch.rank(a, 5);
        assertThat(index).isEqualTo(2);
    }

    @Test
    public void testKeyNotExist() throws Exception {
        int index = BinarySearch.rank(a, 9);
        assertThat(index).isEqualTo(4);
    }

    @Test
    public void testKeyLessThanFirst() throws Exception {
        int index = BinarySearch.rank(a, 1);
        assertThat(index).isEqualTo(0);
    }

    @Test
    public void testKeyLargerThanLast() throws Exception {
        int index = BinarySearch.rank(a, 20);
        assertThat(index).isEqualTo(a.length - 1);
    }
}