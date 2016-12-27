package io.keweishang.algorithm.bst;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kshang on 27/12/2016.
 */
public class BSTsTest {

    @Test
    public void nullIsNotBST() throws Exception {
        boolean isBST = BSTs.isBST(null);
        assertThat(isBST).isFalse();
    }

    @Test
    public void oneLevelIsBST() throws Exception {
        boolean isBST = BSTs.isBST(new Node(10));
        assertThat(isBST).isTrue();
    }

    @Test
    public void twoLevelIsBST() throws Exception {

        Node root = new Node(10);
        Node child1 = new Node(5);
        Node child2 = new Node(15);
        root.left = child1;
        root.right = child2;

        boolean isBST = BSTs.isBST(root);
        assertThat(isBST).isTrue();
    }

    @Test
    public void twoLevelIsNotBST() throws Exception {

        Node root = new Node(10);
        Node child1 = new Node(5);
        Node child2 = new Node(15);
        root.left = child2;
        root.right = child1;

        boolean isBST = BSTs.isBST(root);
        assertThat(isBST).isFalse();
    }
}