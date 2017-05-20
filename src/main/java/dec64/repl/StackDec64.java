package dec64.repl;

import dec64.DEC64;
import java.util.EmptyStackException;

/**
 *
 * @author kittylyst
 */
public final class StackDec64 {

    private int top;
    private final @DEC64 long[] contents;

    public StackDec64(final int size) {
        contents = new @DEC64 long[size];
        top = 0;
    }

    /**
     * Looks at the value at the top of this stack without removing it
     * from the stack.
     *
     * @return  the value at the top of this stack
     * @throws  EmptyStackException if this stack is empty.
     */
    public synchronized @DEC64
    long peek() {
        int len = size();

        if (len == 0)
            throw new EmptyStackException();
        return contents[len - 1];
    }

    /**
     * Pushes a value onto the top of this stack. 
     *
     * @param   value   the value to be pushed onto this stack.
     * @return  the <code>value</code> argument.
     */
    public synchronized @DEC64
    long push(@DEC64 long value) {
        int len = top++;
        contents[len] = value;

        return value;
    }

    /**
     * Removes the value at the top of this stack and returns it
     *
     * @return  The value at the top of this stack
     * @throws  EmptyStackException if this stack is empty.
     */
    public synchronized @DEC64
    long pop() {
        @DEC64 long out = peek();
        top--;

        return out;
    }

    /**
     * Returns the number of components in this stack.
     *
     * @return  the number of components in this stack
     */
    public synchronized int size() {
        return top;
    }

    /**
     * Tests if this stack is empty.
     *
     * @return  <code>true</code> if and only if this stack contains
     *          no items; <code>false</code> otherwise.
     */
    public synchronized boolean empty() {
        return size() == 0;
    }

    /**
     * Returns the 1-based position where a value is on this stack.
     * If the value <tt>v</tt> occurs as an item in this stack, this
     * method returns the distance from the top of the stack of the
     * occurrence nearest the top of the stack; the topmost item on the
     * stack is considered to be at distance <tt>1</tt>. 
     *
     * @param   v   the desired value.
     * @return  the 1-based position from the top of the stack where
     *          the object is located; the return value <code>-1</code>
     *          indicates that the object is not on the stack.
     */
    public synchronized int search(@DEC64 long v) {
        int i = lastIndexOf(v);

        if (i >= 0) {
            return size() - i;
        }
        return -1;
    }

    public synchronized int lastIndexOf(@DEC64 long v) {
        for (int i = top; i >= 0; i--) {
            if (contents[i] == v)
                return i;
        }
        return -1;
    }
}
