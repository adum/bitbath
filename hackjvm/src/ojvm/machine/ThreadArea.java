package ojvm.machine;

import ojvm.data.JavaValue;
import ojvm.data.ReturnAddress;
import ojvm.data.InternalClass;
import ojvm.data.InternalMethod;

/**
 * Runtime data areas for one thread. These areas are created when a
 * thread is created and destroyed when the thread exits.
 * 
 * File created June 7, 2000
 * @author Amr Sabry
 **/

class ThreadArea {
    // If the currentMethod is not native, the pc register contains
    // the address of the instruction being executed. Only one frame,
    // the frame for the executing method is active at any point in a
    // given thread of control. The class in which the current method
    // is defined is the current class.
    private VMStack stack; 

    ThreadArea () {
        this.stack = new VMStack();
    }

    InternalClass getCurrentClass () { 
        return stack.getCurrentClass(); 
    }

    InternalMethod getCurrentMethod () { 
        return stack.getCurrentMethod(); 
    }

    JavaValue popOperandStack () throws OperandStackE { 
        return stack.popOperandStack();
    }

    void pushOperandStack (JavaValue v) throws OperandStackE { 
        stack.pushOperandStack(v);
    }

    JavaValue getLocalVar (int i) throws LocalVarsE {
        return stack.getLocalVar(i);
    }

    void putLocalVar (int i, JavaValue v) throws LocalVarsE {
        stack.putLocalVar(i,v);
    }

    ReturnAddress getCurrentPC() { 
        return stack.getCurrentPC();
    }

    void setPC (ReturnAddress newPC) {
        stack.setPC(newPC);
    }

    void pushFrame (InternalMethod m, JavaValue[] args) throws LocalVarsE {
        stack.pushFrame(m,args);
    }

    void popFrame () throws VMStackE {
        stack.popFrame();
    }

    VMFrame getCurrentFrame () { 
        return stack.getCurrentFrame();
    }

    public String toString () {
        String res = "ThreadArea:\n";
        res += "Stack: \n" + stack + "\n";
        return res;
    }

	public int getStackSize() {
		return stack.getStackSize();
	}
}
