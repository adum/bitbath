package ojvm.machine;

import java.util.Stack;
import java.util.EmptyStackException;

import ojvm.data.JavaValue;

/**
 * Operand stack in a VMFrame. 
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

// Neither the class nor the methods are public; they are only visible
// within the package.
class OperandStack {
    private Stack s;
    private int maximumDepth; // Maximum depth is known at compile time.
    private int currentDepth; // long and double contribute 2; everything else contributes 1
    
    OperandStack (int maximumDepth) {
        this.maximumDepth = maximumDepth;
        // The operand stack is empty when the frame that contains
        // it is created.
        this.s = new Stack();
        this.currentDepth = 0;
    }

    void push (JavaValue v) throws OperandStackE {
        currentDepth += v.getSize();
        if (currentDepth > maximumDepth) {
            String msg = "Maximum depth of operand stack is " + maximumDepth;
            msg += "; trying to expand it to " + currentDepth;
            throw new OperandStackE(msg);
        }
        s.push(v);
    }

    JavaValue pop () throws OperandStackE {
        try {
            JavaValue v = (JavaValue) (s.pop());
            currentDepth -= v.getSize();
            return v;
        }
        catch (EmptyStackException e) {
            throw new OperandStackE("Attempt to pop from empty operand stack");
        }
    }

    public String toString () {
        return s.toString();
    }

	public int getStackSize() {
		return currentDepth;
	}
}
