
/**
 * Stack array based generic implementation
 * 
 * @author kewei
 *
 * @param <U>
 *            the type of element stored in stack
 */
public class ArrayStack<U> {

    private U[] s;
    private int N = 0;

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        // Java does not support generic array creation
        s = (U[]) new Object[capacity];
    }

    public void push(U item) {
        if (N == s.length)
            // double the capacity
            resize(2 * s.length);
        s[N++] = item;
    }

    public U pop() {
        U item = s[--N];
        s[N] = null;
        if (N > 0 && N == s.length / 4)
            // half the capacity if only a quarter of the array is filled
            resize(s.length / 2);
        return item;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Resize array to capacity
     * 
     * @param capacity
     */
    private void resize(int capacity) {
        @SuppressWarnings("unchecked")
        U[] copy = (U[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }
}
