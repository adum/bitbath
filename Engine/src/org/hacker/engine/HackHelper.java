package org.hacker.engine;

import ojvm.data.InternalClass;
import ojvm.data.JavaArray;
import ojvm.data.JavaDoubleValue;
import ojvm.data.JavaIntValue;
import ojvm.data.JavaNonnullReferenceValue;
import ojvm.data.JavaReferenceValue;
import ojvm.data.JavaValue;
import ojvm.machine.ControlUnit;
import ojvm.util.Descriptor;

/**
 * some convenience methods for dealing with the HVM
 */
public class HackHelper {
    /**
     * copies the values of a 2d array from native form into HackJVM form
     */
    public static void copy2Darray(int[][] nativeArray, JavaValue boardArg) {
        try {
            JavaNonnullReferenceValue refVal = (JavaNonnullReferenceValue) boardArg.toReference();
            JavaArray arr = refVal.toArray();
            for (int x = 0; x < nativeArray.length; x++) {
                JavaValue value = arr.get(x);
                JavaArray arr2 = ((JavaNonnullReferenceValue) value).toArray();
                for (int y = 0; y < nativeArray[0].length; y++) {
                    arr2.store(y, new JavaIntValue(nativeArray[x][y]));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JavaValue create2DArrayArg(final ControlUnit cu, int[][] arr) throws Exception {
        Descriptor componentType = new Descriptor("[[I");
        int[] dimSizes = new int[2];
        // reverse order for dim sizes
        int len = 0;
        if (arr.length > 0)
                len = arr[0].length;
                dimSizes[0] = len;
        dimSizes[1] = arr.length;
        JavaArray ja = cu.createMultiArray(2, dimSizes, componentType);

        JavaReferenceValue arrayref = new JavaNonnullReferenceValue(ja);
        return arrayref;
    }

    public static JavaValue createArrayArg(final ControlUnit cu, double[] arr) throws Exception {
        Descriptor componentType = new Descriptor("[D");

        Descriptor compClassDesc = new Descriptor("D");
        InternalClass componentClass = cu.findClass(compClassDesc);
        InternalClass arrayClass = cu.findClass(componentType);

        JavaArray ja = new JavaArray(arrayClass, componentClass, arr.length);

        JavaReferenceValue arrayref = new JavaNonnullReferenceValue(ja);
        for (int i = 0; i < arr.length; i++) {
                ja.store(i, new JavaDoubleValue(arr[i]));
        }
        return arrayref;
    }

    public static JavaValue createArrayArg(final ControlUnit cu, int[] arr) throws Exception {
        Descriptor componentType = new Descriptor("[I");

        Descriptor compClassDesc = new Descriptor("I");
        InternalClass componentClass = cu.findClass(compClassDesc);
        InternalClass arrayClass = cu.findClass(componentType);

        JavaArray ja = new JavaArray(arrayClass, componentClass, arr.length);

        JavaReferenceValue arrayref = new JavaNonnullReferenceValue(ja);
        for (int i = 0; i < arr.length; i++) {
                ja.store(i, new JavaIntValue(arr[i]));
        }
        return arrayref;
    }

    public static JavaValue createIntArg(int a) {
        JavaIntValue val = new JavaIntValue(a);
        return val;
    }

        public static JavaValue createDoubleArg(double x) {
        JavaDoubleValue val = new JavaDoubleValue(x);
        return val;
        }
}
