package ojvm.machine;

import java.util.LinkedList;

import ojvm.data.JavaValue;
import ojvm.data.ReturnAddress;
import ojvm.data.InternalClass;
import ojvm.data.InternalMethod;

/**
 * Runtime data areas for threads.
 * 
 * File created June 7, 2000
 * @author Amr Sabry
 * 
 */

class ThreadsArea {
    // Maintains a collection of thread areas one for each thread
    private LinkedList threadList;
    private ThreadArea currentThread;
    
    ThreadsArea () {
        this.threadList = new LinkedList();
        this.currentThread = new ThreadArea();
    }

    InternalClass getCurrentClass () { 
        return currentThread.getCurrentClass(); 
    }

    InternalMethod getCurrentMethod () { 
        return currentThread.getCurrentMethod(); 
    }

    JavaValue popOperandStack () throws OperandStackE { 
        return currentThread.popOperandStack();
    }

    void pushOperandStack (JavaValue v) throws OperandStackE { 
        currentThread.pushOperandStack(v);
    }

    JavaValue getLocalVar (int i) throws LocalVarsE {
        return currentThread.getLocalVar(i);
    }

    void putLocalVar (int i, JavaValue v) throws LocalVarsE {
        currentThread.putLocalVar(i,v);
    }

    ReturnAddress getCurrentPC() { 
        return currentThread.getCurrentPC(); 
    }

    void setPC (ReturnAddress newPC) {
        currentThread.setPC(newPC);
    }

    void pushFrame (InternalMethod m, JavaValue[] args) throws LocalVarsE {
        currentThread.pushFrame(m,args);
    }

    void popFrame () throws VMStackE {
        currentThread.popFrame();
    }

    VMFrame getCurrentFrame () { 
        return currentThread.getCurrentFrame();
    }

	public int getStackSize() {
		return currentThread.getStackSize();
	}
}
