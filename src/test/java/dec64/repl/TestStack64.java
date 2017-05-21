package dec64.repl;

import dec64.Basic64;
import org.junit.Before;
import org.junit.Test;

import static dec64.Constants64.*;
import java.util.EmptyStackException;
import static org.junit.Assert.*;

/**
 *
 * @author ben
 */
public class TestStack64 {

    private StackDec64 stack;

    @Before
    public void emptyStack() {
        stack = new StackDec64(255);
    }

    @Test
    public void testEmpty() {
        assertTrue("Unexpected non-empty stack", stack.empty());
    }

    @Test
    public void testPushPop() {
        stack.push(DEC64_HALF_PI);
        assertEquals("Popped value does not equal pushed", DEC64_HALF_PI, stack.pop());
    }

    @Test
    public void testPushPeekSize() {
        stack.push(DEC64_HALF_PI);
        assertEquals("Unexpected peeking value", DEC64_HALF_PI, stack.peek());
        assertEquals("Size should be 1", 1, stack.size());
    }

    @Test
    public void testPushPushPopPop() {
        stack.push(DEC64_NEGATIVE_ONE);
        stack.push(DEC64_HALF_PI);
        assertEquals("Pop failed", DEC64_HALF_PI, stack.pop());
        assertEquals("Size should be 1", 1, stack.size());
        assertEquals("Pop failed", DEC64_NEGATIVE_ONE, stack.pop());
    }

    @Test(expected = EmptyStackException.class)
    public void empty_stack_should_throw_on_peek() {
        stack.peek();
        fail("Empty stack should throw on peek");
    }

    @Test(expected = EmptyStackException.class)
    public void empty_stack_should_throw_on_pop() {
        stack.pop();
        fail("Empty stack should throw on pop");
    }

    @Test(expected = EmptyStackException.class)
    public void empty_stack_should_throw_on_underflow() {
        stack.push(DEC64_HALF_PI);
        stack.pop();
        stack.pop();
        fail("Empty stack should throw on underflow");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void empty_stack_should_throw_on_overflow() {
        for (int i = 0; i < 1000; i++) {
            stack.push(Basic64.of(i, 0));
        }
        fail("Stack should throw on overflow");
    }

}
