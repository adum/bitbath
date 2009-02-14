package ojvm.loading;

/**
 * Exception thrown when a class is not found
 * 
 * @author Amr Sabry 
 * @version jdk-1.2 
 **/

public class ClassNotFoundE extends LoadE {
    public ClassNotFoundE (String s) { super(s); }
}
