package ojvm.loading;

/**
 * Exception thrown when something goes wrong with the
 * ClassFileInputStream used to read classfiles.
 * 
 * @author Amr Sabry 
 * @version jdk-1.2 
 **/

public class ClassFileInputStreamE extends LoadE {
    public ClassFileInputStreamE (String s) { super(s); }
}
