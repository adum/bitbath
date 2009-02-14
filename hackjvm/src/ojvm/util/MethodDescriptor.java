package ojvm.util;

import java.util.Vector;

/**
 * Parses method descriptors...
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
*/

public class MethodDescriptor {
    private String desc;
    private Descriptor[] parameterTypes;
    private Descriptor returnType;

    public MethodDescriptor (String desc) throws BadDescriptorE {
        this.desc = desc;
        int oparen = desc.indexOf(RuntimeConstants.SIG_METHOD);
        if (oparen == -1) throw new BadDescriptorE("Bad method descriptor: " + desc);
        int cparen = desc.indexOf(RuntimeConstants.SIG_ENDMETHOD);
        if (cparen == -1) throw new BadDescriptorE("Bad method descriptor: " + desc);
        String parametersString = desc.substring(oparen+1, cparen);
        String returnString = desc.substring(cparen+1, desc.length());
        this.parameterTypes = getParameterTypes(parametersString);
        boolean allowVoid = true;
        this.returnType = new Descriptor(returnString, allowVoid); 
    }

    public String toString () { return desc; }
    public Descriptor[] getParameterTypes () { return parameterTypes; }
    public Descriptor getReturnType () { return returnType; }
    public int numArgs () { return parameterTypes.length; }
    public int argValuesSize () { // excluding size of "this" if method is virtual
        int size = 0;
        for (int i=0; i<numArgs(); i++) {
            size += parameterTypes[i].getValueSize();
        }
        return size;
    }
    
    private Descriptor[] getParameterTypes (String desc) throws BadDescriptorE {
        Descriptor[] result;
        Vector tempResult = new Vector();
        int index = 0;
        while (index < desc.length()) {
            String nextDesc = getNextDescriptor(desc, index);
            Descriptor cn = new Descriptor(nextDesc);
            tempResult.addElement(cn);
            index += nextDesc.length();
        }
        result = new Descriptor[tempResult.size()];
        tempResult.copyInto(result);
        return result;
    }

    private String getNextDescriptor (String desc, int index) throws BadDescriptorE {
        BadDescriptorE err = new BadDescriptorE(desc);
        try {
            switch (desc.charAt(index)) {
            case RuntimeConstants.SIGC_BYTE: 
            case RuntimeConstants.SIGC_CHAR: 
            case RuntimeConstants.SIGC_DOUBLE: 
            case RuntimeConstants.SIGC_FLOAT: 
            case RuntimeConstants.SIGC_INT: 
            case RuntimeConstants.SIGC_LONG: 
            case RuntimeConstants.SIGC_SHORT: 
            case RuntimeConstants.SIGC_BOOLEAN: return desc.substring(index, index+1); 
            case RuntimeConstants.SIGC_CLASS: 
                int endclassIndex = desc.indexOf(RuntimeConstants.SIG_ENDCLASS, index);
                if (endclassIndex == -1) throw err;
                return desc.substring(index, endclassIndex+1);
            case RuntimeConstants.SIGC_ARRAY: 
                return RuntimeConstants.SIG_ARRAY + getNextDescriptor(desc, index+1);
            default: throw err;
            }
        }
        catch (StringIndexOutOfBoundsException e) { throw err; }
    }
}
