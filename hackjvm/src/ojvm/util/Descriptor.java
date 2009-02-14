package ojvm.util;

import java.io.File;

import ojvm.data.JavaValue;
import ojvm.data.JavaByteValue;
import ojvm.data.JavaShortValue;
import ojvm.data.JavaIntValue;
import ojvm.data.JavaLongValue;
import ojvm.data.JavaFloatValue;
import ojvm.data.JavaDoubleValue;
import ojvm.data.JavaCharValue;
import ojvm.data.JavaBooleanValue;
import ojvm.data.JavaNullReferenceValue;

/**
 * The JVM spec. includes many ways to specify a class name. This file manages
 * all the different naming conventions and maps among them.
 * 
 * The first way to specify a class name is by using a descriptor. See Section
 * 4.3 of the JVM Spec. In more detail: - Classes for primitive types are named
 * using the descriptors B, C, D, F, I, J, S, and Z. In the return type of a
 * method we also allow V. - Classes for reference types other than arrays are
 * named using descriptors that start with L (e.g. Ljava/lang/Thread;) - Classes
 * for array types are named using descriptors that start with [ (e.g., [I and
 * [Ljava/lang/Thread;)
 * 
 * Classes for references types can also be named using a string. The format of
 * this string could be: - an external Java name (e.g., java.lang.Thread) - an
 * internal form of a Java name as stored in class files (e.g.,
 * java/lang/Thread) - a filename using the host machine conventions (e.g.,
 * java\lang\Thread.class)
 * 
 * Finally classes for primitive types are named using an integer tag in the
 * newarray instruction. See p.343 of the JVM Spec.
 * 
 * We have a constructor for each of the three above cases. Without proper care,
 * a string argument called "B" could be confused as the descriptor for the
 * class byte or as the name of a class B. We distinguish the two cases by using
 * constructor one to take descriptor arguments and constructor two to take
 * other string arguments.
 * 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Descriptor {
    // A string argument to the second constructor must come with one of the
    // following two flags:
    public static final int JAVA_FORM     = 0;
    public static final int INTERNAL_FORM = 1;

    // What kind of class name do we have:
    private boolean         isPrimitive;
    private boolean         isArray;
    private boolean         isClassInterface;
    private int             valueSize;        // everything has size one
                                                // except long and double (void
                                                // has size 0)

    // Various formats used for the name; their validity depends on the flags
    // above.
    protected String        primitiveName;
    private String          descriptor;
    private String          javaForm;
    private String          internalForm;
    private String          filename;
    private int             num_dimensions;
    private Descriptor      baseDesc;

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor 1

    // Takes a descriptor that can't be V
    public Descriptor(String desc) throws BadDescriptorE {
        this(desc, false);
    }

    // Takes a descriptor; may accept V as a valid descriptor
    public Descriptor(String desc, boolean allowVoid) throws BadDescriptorE {
        BadDescriptorE err = new BadDescriptorE(desc);
        descriptor = desc;
        try {
            switch (desc.charAt(0)) {
                case RuntimeConstants.SIGC_BYTE:
                    isPrimitive = true;
                    valueSize = 1;
                    primitiveName = "byte";
                    break;
                case RuntimeConstants.SIGC_CHAR:
                    isPrimitive = true;
                    valueSize = 1;
                    primitiveName = "char";
                    break;
                case RuntimeConstants.SIGC_DOUBLE:
                    isPrimitive = true;
                    valueSize = 2;
                    primitiveName = "double";
                    break;
                case RuntimeConstants.SIGC_FLOAT:
                    isPrimitive = true;
                    valueSize = 1;
                    primitiveName = "float";
                    break;
                case RuntimeConstants.SIGC_INT:
                    isPrimitive = true;
                    valueSize = 1;
                    primitiveName = "int";
                    break;
                case RuntimeConstants.SIGC_LONG:
                    isPrimitive = true;
                    valueSize = 2;
                    primitiveName = "long";
                    break;
                case RuntimeConstants.SIGC_SHORT:
                    isPrimitive = true;
                    valueSize = 1;
                    primitiveName = "short";
                    break;
                case RuntimeConstants.SIGC_BOOLEAN:
                    isPrimitive = true;
                    valueSize = 1;
                    primitiveName = "boolean";
                    break;
                case RuntimeConstants.SIGC_VOID:
                    if (allowVoid) {
                        isPrimitive = true;
                        valueSize = 0;
                        primitiveName = "void";
                        break;
                    }
                    else
                        throw err;
                case RuntimeConstants.SIGC_CLASS:
                    isClassInterface = true;
                    valueSize = 1;
                    int endclassIndex = desc.indexOf(RuntimeConstants.SIGC_ENDCLASS);
                    if (endclassIndex == -1)
                        throw err;
                    internalForm = desc.substring(1, endclassIndex);
                    javaForm = internalForm.replace('/', '.');
                    filename = internalForm.replace('/', File.separatorChar) + ".class";
                    break;
                case RuntimeConstants.SIGC_ARRAY: {
                    isArray = true;
                    valueSize = 1;
                    num_dimensions = getNumDimensions(descriptor);
                    baseDesc = getBaseDesc(descriptor, num_dimensions);
                    break;
                }
                default:
                    throw err;
            }
        }
        catch (StringIndexOutOfBoundsException e) {
            throw err;
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor 2

    // Takes a string that represents a class name is either JAVA_FORM or
    // INTERNAL_FORM
    public Descriptor(String name, int kind) throws BadDescriptorE {
        isPrimitive = isArray = false;
        isClassInterface = true;
        valueSize = 1;

        if (kind == JAVA_FORM) {
            javaForm = name;
            internalForm = name.replace('.', '/');
        }
        else if (kind == INTERNAL_FORM) {
            javaForm = name.replace('/', '.');
            internalForm = name;
        }
        else
            throw new BadDescriptorE("Unknown descriptor kind " + kind);

        filename = internalForm.replace('/', File.separatorChar) + ".class";
        descriptor = RuntimeConstants.SIG_CLASS + internalForm + RuntimeConstants.SIG_ENDCLASS;
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor 3

    // Takes an integer tag that represents the name of a primitive
    // class. See p.343 of the JVM Spec. (instruction newarray)
    public Descriptor(int atype) throws BadDescriptorE {
        switch (atype) {
            case RuntimeConstants.T_BOOLEAN: {
                isPrimitive = true;
                descriptor = RuntimeConstants.SIG_BOOLEAN;
                valueSize = 1;
                primitiveName = "boolean";
                break;
            }
            case RuntimeConstants.T_CHAR: {
                isPrimitive = true;
                descriptor = RuntimeConstants.SIG_CHAR;
                valueSize = 1;
                primitiveName = "char";
                break;
            }
            case RuntimeConstants.T_FLOAT: {
                isPrimitive = true;
                descriptor = RuntimeConstants.SIG_FLOAT;
                valueSize = 1;
                primitiveName = "float";
                break;
            }
            case RuntimeConstants.T_DOUBLE: {
                isPrimitive = true;
                descriptor = RuntimeConstants.SIG_DOUBLE;
                valueSize = 2;
                primitiveName = "double";
                break;
            }
            case RuntimeConstants.T_BYTE: {
                isPrimitive = true;
                descriptor = RuntimeConstants.SIG_BYTE;
                valueSize = 1;
                primitiveName = "byte";
                break;
            }
            case RuntimeConstants.T_SHORT: {
                isPrimitive = true;
                descriptor = RuntimeConstants.SIG_SHORT;
                valueSize = 1;
                primitiveName = "short";
                break;
            }
            case RuntimeConstants.T_INT: {
                isPrimitive = true;
                descriptor = RuntimeConstants.SIG_INT;
                valueSize = 1;
                primitiveName = "int";
                break;
            }
            case RuntimeConstants.T_LONG: {
                isPrimitive = true;
                descriptor = RuntimeConstants.SIG_LONG;
                valueSize = 2;
                primitiveName = "long";
                break;
            }
            default:
                throw new BadDescriptorE("Illegal tag for primitive name " + atype);
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////
    // Public methods

    public String getJavaForm() {
        return javaForm;
    }

    public String getInternalForm() {
        return internalForm;
    }

    public String getFilename() {
        return filename;
    }

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public boolean isArray() {
        return isArray;
    }

    public boolean isReference() {
        return isArray || isClassInterface;
    }

    public int getNumDimensions() {
        return num_dimensions;
    }

    public Descriptor getBaseDesc() {
        return baseDesc;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean equals(Object other) {
        if (other != null && other instanceof Descriptor) {
            Descriptor otherClassDesc = (Descriptor) other;
            return toString().equals(otherClassDesc.toString());
        }
        else
            return false;
    }

    public String getStringDescriptor() {
        return descriptor;
    }

    public String toString() {
        if (isPrimitive)
            return primitiveName;
        else if (isArray)
            return descriptor;
        else
            return javaForm;
    }

    public int getValueSize() {
        return valueSize;
    }

    public static JavaValue getDefaultValue(Descriptor desc) throws BadDescriptorE {
        if (desc.isPrimitive()) {
            String pname = desc.toString();
            if (pname.equals("byte"))
                return new JavaByteValue((byte) 0);
            else if (pname.equals("short"))
                return new JavaShortValue((short) 0);
            else if (pname.equals("int"))
                return new JavaIntValue(0);
            else if (pname.equals("long"))
                return new JavaLongValue(0L);
            else if (pname.equals("float"))
                return new JavaFloatValue(0.0f);
            else if (pname.equals("double"))
                return new JavaDoubleValue(0.0);
            else if (pname.equals("char"))
                return new JavaCharValue('\u0000');
            else if (pname.equals("boolean"))
                return new JavaBooleanValue(false);
            else
                throw new BadDescriptorE("Unknown descriptor for primitive name " + desc);
        }
        else
            return JavaNullReferenceValue.nullRef;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Private methods 

    private static int getNumDimensions(String descriptor) throws BadDescriptorE {
        int num = 0;
        while (descriptor.charAt(num) == RuntimeConstants.SIGC_ARRAY)
            num++;
        if (num > RuntimeConstants.MAX_NUMBER_DIMENSIONS)
            throw new BadDescriptorE("Array descriptor has too many dimensions");
        return num;
    }

    private static Descriptor getBaseDesc(String descriptor, int num_dimensions) throws BadDescriptorE {
        String componentDescriptor = descriptor.substring(num_dimensions, descriptor.length());
        return new Descriptor(componentDescriptor);
    }
}
