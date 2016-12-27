package io.keweishang.algorithm.bst;

/**
 * Created by kshang on 27/12/2016.
 */
public class BSTs {

    /**
     * Check if the Node is a binary search tree
     *
     * @param root
     * @return is root is null, it is not a binary search tree
     */
    public static boolean isBST(Node root) {
        if (root == null) return false;
        int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
        return isBST(root, min, max);
    }

    private static boolean isBST(Node root, int min, int max) {
        if (root == null) return true;
        boolean rootInRange = root.value > min && root.value < max;
        return rootInRange && isBST(root.left, min, root.value) && isBST(root.right, root.value, max);
    }
}
