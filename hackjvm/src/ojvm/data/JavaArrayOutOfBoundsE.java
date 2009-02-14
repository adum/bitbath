package ojvm.data;

/**
 * Exception thrown when manipulating a Java array
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
*/

public class JavaArrayOutOfBoundsE extends InternalException {
    public JavaArrayOutOfBoundsE (String s) { super(s); }
}
