package ojvm.data;

import ojvm.machine.ControlUnit;
import ojvm.machine.OperandStackE;
import ojvm.operations.LinkE;
import ojvm.util.BadDescriptorE;
import ojvm.util.Descriptor;

/**
 * Representations of Java arrays.
 * 
 * File created June 23, 2000
 * @author Amr Sabry
 **/

public class JavaArray extends JavaObject {
    private int size;
    private InternalClass componentClass;
    private JavaValue[] elements;

    public JavaArray (InternalClass arrayClass, InternalClass componentClass, int size) {
        super(arrayClass); 
        this.componentClass = componentClass;
        this.size = size;
        elements = new JavaValue[size];
        JavaValue v = componentClass.getDefaultValue();
        for (int i=0; i<size; i++) elements[i] = v;
    }

    public JavaArray toArray () { return this; }
    public JavaInstance toClassInstance () throws BadConversionE { 
        throw new BadConversionE("Expecting a class instance; found an array");
    }

    public InternalClass getComponentClass () { return componentClass; }

    public int arraylength () { 
        return elements.length;
    }

    public JavaValue[] getElements () {
        return elements;
    }

    public void store (int index, JavaValue v) throws JavaArrayOutOfBoundsE {
        try {
            elements[index] = v;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new JavaArrayOutOfBoundsE("Bad array index " + index);
        }
    }

    public JavaValue get (int index) throws JavaArrayOutOfBoundsE {
        try {
            return elements[index];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new JavaArrayOutOfBoundsE("Bad array index " + index);
        }
    }

    public String toString () {
        return "arrayof(" + getComponentClass().getDesc() + ")";
    }
}
