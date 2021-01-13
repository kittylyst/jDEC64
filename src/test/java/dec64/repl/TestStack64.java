package dec64.repl;

import dec64.Basic64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dec64.Constants64.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

/**
 *
 * @author ben
 */
public class TestStack64 {

    private StackDec64 stack;

    @BeforeEach
    public void emptyStack() {
        stack = new StackDec64(255);
    }

    @Test
    public void testEmpty() {
        assertTrue(stack.empty(), "Unexpected non-empty stack");
    }

    @Test
    public void testPushPop() {
        stack.push(DEC64_HALF_PI);
        assertEquals(DEC64_HALF_PI, stack.pop(), "Popped value does not equal pushed");
    }

    @Test
    public void testPushPeekSize() {
        stack.push(DEC64_HALF_PI);
        assertEquals(DEC64_HALF_PI, stack.peek(), "Unexpected peeking value");
        assertEquals(stack.size(), 1, "Size should be 1");
    }

    @Test
    public void testPushPushPopPop() {
        stack.push(DEC64_NEGATIVE_ONE);
        stack.push(DEC64_HALF_PI);
        assertEquals(DEC64_HALF_PI, stack.pop(), "Pop failed");
        assertEquals(1, stack.size(), "Size should be 1");
        assertEquals(DEC64_NEGATIVE_ONE, stack.pop(), "Pop failed");
    }

    @Test
    public void empty_stack_should_throw_on_peek() {
        assertThrows(EmptyStackException.class, () -> {
            stack.peek();
        }, "Empty stack should throw on peek");
    }

    @Test
    public void empty_stack_should_throw_on_pop() {
        assertThrows(EmptyStackException.class, () -> {
            stack.pop();
        }, "Empty stack should throw on pop");
    }

    @Test
    public void empty_stack_should_throw_on_underflow() {
        stack.push(DEC64_HALF_PI);
        stack.pop();
        assertThrows(EmptyStackException.class, () -> {
            stack.pop();
        }, "Empty stack should throw on underflow");
    }

    @Test
    public void empty_stack_should_throw_on_overflow() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            for (int i = 0; i < 1000; i++) {
                stack.push(Basic64.of(i, 0));
            }
        }, "Stack should throw on overflow");
    }

}
