package ojvm.data;

/**
 * 
 * These are internal representation of Java exceptions. They are
 * caught by the main loop of the interpreter and the running program
 * is given a chance to handle them.
 * 
 * File created June 27, 2000
 * @author Amr Sabry
 **/

public class JavaException extends Exception {
    private JavaNonnullReferenceValue throwable;

    public JavaException (JavaNonnullReferenceValue throwable) { 
        super();
        this.throwable = throwable;
    }

    public JavaNonnullReferenceValue getThrowable () { return throwable; }
}
