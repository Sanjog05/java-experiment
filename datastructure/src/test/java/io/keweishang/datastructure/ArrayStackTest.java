package io.keweishang.datastructure;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by kshang on 27/12/2016.
 */
public class ArrayStackTest {

    @Test
    public void test_pop_pushed_in_order() {
        ArrayStack<String> stack = new ArrayStack<>(3);
        stack.push("abc");
        stack.push("xyz");
        Assertions.assertThat(stack.pop()).isEqualTo("xyz");
        Assertions.assertThat(stack.pop()).isEqualTo("abc");
    }



    @Test
    public void test_is_empty() {
        ArrayStack<String> stack = new ArrayStack<>(3);
        stack.push("abc");
        stack.push("xyz");
        int count = 0;
        while (!stack.isEmpty()) {
            stack.pop();
            count++;
        }
        Assertions.assertThat(count).isEqualTo(2);
    }
}