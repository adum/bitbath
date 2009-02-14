package ojvm.machine;

import ojvm.data.JavaValue;
import ojvm.data.SecondWord;

/**
 * Local variables in a VMFrame.
 * 
 * File created June 8, 2000
 * @author Amr Sabry
 **/

// Neither the class nor the methods are public; they are only visible
// within the package.
class LocalVars {
    // Length is known at compile time; long and double contribute 2
    // each; everything else contributes 1.
    private int numVars;
    private JavaValue[] vars;

    private static JavaValue invalidEntry = new SecondWord();

    // LocalVars are initialized to the arguments of the method whose frame we're building
    LocalVars (int numVars, JavaValue[] args) throws LocalVarsE {
        this.numVars = numVars;
        this.vars = new JavaValue[numVars];
        int index = 0;
        for (int i=0; i<args.length; i++) {
            put(index,args[i]);
            index += args[i].getSize();
        }
    }

    JavaValue get (int i) throws LocalVarsE {
        isValid(i);
        JavaValue v = vars[i];
        if (v == null) {
            throw new LocalVarsE("No value at index " + i + " of LocalVars");
        }
        // a filler for a double or long is not accessible
        if (v.getSize() == 0) throw new LocalVarsE("Attempt to access second word of double/long");
        if (v.getSize() == 2) isValid(i+1); // accessing a long/double; next entry must exist
        return v; 
    }

    void put (int i, JavaValue v) throws LocalVarsE {
        isValid(i);
        // check the old entry: updating a filler for a double or long
        // invalidates the previous entry
        
        //AM: commenting out because screws up on code that reuses vars in certain ways
        //eg:
//        public java.lang.Object think(double, double, double, double, boolean, int, int,
//        		 double, double, double, double, double[], double[], int[], int[], int[], int[][
//        		]);
//        		  Code:
//        		   0:   iconst_0
//        		   1:   istore  26
//        		   3:   iconst_0
//        		   4:   istore  27
//        		   6:   dconst_0
//        		   7:   dstore  28
//        		   9:   ldc2_w  #27; //double 1.0E8d
//        		   12:  dstore  26
//        		   14:  iconst_0
//        		   15:  istore  28
//        		   17:  ldc2_w  #29; //double 100.0d
//        		   20:  dstore  29
//        		   22:  iload   28
//        		   24:  ifeq    29
//        		   27:  aload_0
//        		   28:  areturn
//        		   29:  aload_0
//        		   30:  areturn
//        JavaValue old = vars[i]; 
//        if (old != null) {
//            if (old.getSize() == 0) {
//                isValid(i-1);
//                vars[i-1] = invalidEntry;
//            }    
//        }

        // if we are storing a double or long, invalidate i+1
        if (v.getSize() == 2) { 
            isValid(i+1);
            vars[i] = v;
            vars[i+1] = invalidEntry;
        }
        else vars[i] = v; 
    }

    private void isValid (int index) throws LocalVarsE {
        if (index >= 0 && index < numVars) return;
        else throw new LocalVarsE("LocalVars index " + index + 
                                  " out of bounds [" + 0 + ".." + numVars + "]");
    }

    public String toString () {
        String res = "[";
        for (int i=0; i<vars.length; i++) {
            res += vars[i] + ",";
        }
        return res + "]";
    }
}
