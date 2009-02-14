package ojvm.data;

import ojvm.loading.AbsynMethod;
import ojvm.loading.ExceptionsAttribute;
import ojvm.loading.ExceptionHandler;
import ojvm.loading.instructions.Instruction;

import ojvm.util.MethodDescriptor;
import ojvm.util.NameAndDescriptor;

/**
 * Internal representation of methods.
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class InternalMethod extends InternalObject {
    private InternalClass declaringClass; 
    private AbsynMethod absynMethod; 

    public InternalMethod (InternalClass declaringClass, AbsynMethod absynMethod) {
        this.declaringClass = declaringClass;
        this.absynMethod = absynMethod;
    }

    public InternalClass getDeclaringClass () { return declaringClass; }
    public boolean isPublic () { return absynMethod.isPublic(); }
    public boolean isPrivate () { return absynMethod.isPrivate(); }
    public boolean isProtected () { return absynMethod.isProtected(); }
    public boolean isStatic () { return absynMethod.isStatic(); }
    public boolean isFinal () { return absynMethod.isFinal(); }
    public boolean isSynchronized () { return absynMethod.isSynchronized(); }
    public boolean isNative () { return absynMethod.isNative(); }
    public boolean isAbstract () { return absynMethod.isAbstract(); }
    public boolean isStrict () { return absynMethod.isStrict(); }
    public boolean isConstructor () { return absynMethod.isConstructor(); }
    public boolean isClassInitializer () { return absynMethod.isClassInitializer(); }
    public String getName () { return absynMethod.getName(); }
    public MethodDescriptor getType () { return absynMethod.getType(); }
    public NameAndDescriptor getKey () { return new NameAndDescriptor(getName(), getType().toString()); }
    public int getNumLocalVars() { return absynMethod.getCode().getMaxLocals(); }
    public int getMaximumOperandStackDepth() { return absynMethod.getCode().getMaxStack(); }
    public ExceptionHandler[] getExceptionHandlers () { return absynMethod.getCode().getExceptionHandlers(); }
    public Instruction[] getInstructions () { return absynMethod.getCode().getInstructions(); }
    public ExceptionsAttribute getExceptions () { return absynMethod.getExceptions(); }

    public String toString () { 
        return absynMethod.toString();
    }
}
