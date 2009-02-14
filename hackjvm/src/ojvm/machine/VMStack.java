package ojvm.machine;

import java.util.Stack;
import java.util.EmptyStackException;

import ojvm.data.JavaValue;
import ojvm.data.ReturnAddress;
import ojvm.data.InternalClass;
import ojvm.data.InternalMethod;

/**
 * Private stack of VMFrames for thread. The size of the stack can
 * expand and contract as required by the computation.
 * 
 * Throws InternalOutOfMemoryError if it can't be expanded. 
 * 
 * File created June 7, 2000
 * @author Amr Sabry
 **/

class VMStack {
    private Stack s;
    private VMFrame currentFrame;

    VMStack () {
        this.s = new Stack(); 
    }

    void pushFrame (InternalMethod internalMethod, JavaValue[] args) throws LocalVarsE {
        currentFrame = new VMFrame(internalMethod,args); 
        s.push(currentFrame);
    }

    void popFrame () throws VMStackE {
        try {
            s.pop();
            currentFrame = (VMFrame) s.peek();
        }
        catch (EmptyStackException e) {
            throw new VMStackE("Empty VM stack");
        }
    }

    InternalClass getCurrentClass () { 
        return currentFrame.getCurrentClass(); 
    }

    InternalMethod getCurrentMethod () { 
        return currentFrame.getCurrentMethod(); 
    }

    JavaValue popOperandStack () throws OperandStackE { 
        return currentFrame.popOperandStack();
    }

    void pushOperandStack (JavaValue v) throws OperandStackE { 
        currentFrame.pushOperandStack(v);
    }

    VMFrame getCurrentFrame () { 
        return currentFrame;
    }

    JavaValue getLocalVar (int i) throws LocalVarsE {
        return currentFrame.getLocalVar(i);
    }

    void putLocalVar (int i, JavaValue v) throws LocalVarsE {
        currentFrame.putLocalVar(i,v);
    }

    ReturnAddress getCurrentPC() { 
        return currentFrame.getCurrentPC();
    }

    void setPC (ReturnAddress newPC) {
        currentFrame.setPC(newPC);
    }

	public int getStackSize() {
		return currentFrame.getStackSize();
	}
}
