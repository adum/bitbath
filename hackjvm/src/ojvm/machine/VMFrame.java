package ojvm.machine;

import ojvm.data.JavaValue;
import ojvm.data.ReturnAddress;
import ojvm.data.InternalClass;
import ojvm.data.InternalMethod;

/**
 * Data structures for stack frames. A new frame is created each time
 * a method is invoked. A frame is destroyed when its method
 * invocation completes, whether that completion is normal or abrupt.
 * 
 * File created June 7, 2000
 * @author Amr Sabry
 **/

class VMFrame {
    private InternalMethod currentMethod; 
    private InternalClass currentClass;
    private ReturnAddress pc;
    private LocalVars localVars;
    private OperandStack operandStack;

    VMFrame (InternalMethod currentMethod, JavaValue[] args) throws LocalVarsE {
        this.currentMethod = currentMethod;
        this.currentClass = currentMethod.getDeclaringClass();
        this.pc = new ReturnAddress(0);
        int numVars = currentMethod.getNumLocalVars();
        this.localVars = new LocalVars(numVars,args);
        int maximumDepth = currentMethod.getMaximumOperandStackDepth();
        this.operandStack = new OperandStack(maximumDepth);
    }

    InternalClass getCurrentClass () { 
        return currentClass;
    }

    InternalMethod getCurrentMethod () { 
        return currentMethod;
    }

    JavaValue popOperandStack () throws OperandStackE { 
        return operandStack.pop();
    }

    void pushOperandStack (JavaValue v) throws OperandStackE { 
        operandStack.push(v);
    }

    JavaValue getLocalVar (int i) throws LocalVarsE {
        return localVars.get(i);
    }

    void putLocalVar (int i, JavaValue v) throws LocalVarsE {
        localVars.put(i,v);
    }

    ReturnAddress getCurrentPC() { 
        return pc; 
    }

    void setPC (ReturnAddress newPC) {
        pc = newPC;
    }

    public String toString () {
        String res = "Frame: " + localVars + "$$" + operandStack;
        return res;
    }

	public int getStackSize() {
		return operandStack.getStackSize();
	}
}
