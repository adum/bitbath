package ojvm.operations;

import ojvm.data.JavaException;

/**
 * Exceptions related to class initialization
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
*/

public class InitializeE extends LinkE {
    private JavaException e;

    public InitializeE (JavaException e, String s) { 
        super(s);
    }

    public JavaException getJavaException () { return e; }
}
