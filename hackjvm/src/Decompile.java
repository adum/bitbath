// package ojvm;

import ojvm.util.*;
import ojvm.machine.*;
import ojvm.data.*;
import ojvm.loading.*;

public class Decompile {
    public static void main (String[] args) throws Throwable { 
        String name = args[0];
        String classPath = System.getProperty("java.class.path");
        ClassInputStream cis = new ClassInputStream(classPath,name);
        System.out.println(new AbsynClass(cis));
    }
}
