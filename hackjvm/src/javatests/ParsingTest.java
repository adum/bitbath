// Class used to test our parsing

interface PropertyOne { int m1 (boolean b) throws RuntimeException; }

interface PropertyTwo {}

abstract class Parent { 
    protected int px;
    protected synchronized final int pm1 () { return 1; }
    static int spm () { return 1; }
    private int pm2 () { return 0; }
    public abstract long pm3 ();
}

public final class ParsingTest extends Parent implements PropertyOne, PropertyTwo {
    static int x1;
    private int x2;
    public int x3 = 4;
    final float x4 = 4.0f;
    volatile double x5 = 4.0;
    transient String x6 = "Hello";
    private long x7 = 1L;

    public long pm3 () { return x7; }

    public int m1 (boolean b) throws RuntimeException { 
        try { 
            PropertyOne p = null;
            p.m1(b);
        }
        catch (RuntimeException re) { throw re; }
        catch (Exception e) { return 0; }
        catch (Throwable t) { return -1; }
        finally { return super.pm1() + px; }
    }
    
    native int m3 (); 
}
