package ojvm.util;

import ojvm.data.InternalException;

/**
 * Exception thrown within the classes Descriptor and MethodDescriptor
 * when a bad descriptor is encountered.
 * 
 * @author Amr Sabry 
 * @version jdk-1.2 
 **/

public class BadDescriptorE extends InternalException {
    public BadDescriptorE (String s) { super(s); }
}
