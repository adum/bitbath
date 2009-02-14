package ojvm.operations;

import ojvm.data.JavaValue;
import ojvm.data.JavaByteValue;
import ojvm.data.JavaShortValue;
import ojvm.data.JavaIntValue;
import ojvm.data.JavaLongValue;
import ojvm.data.JavaFloatValue;
import ojvm.data.JavaDoubleValue;
import ojvm.data.JavaCharValue;
import ojvm.data.JavaBooleanValue;
import ojvm.data.BadConversionE;

/**
 * Dictionary of the operations on primitive JavaValues.
 * 
 * @author Amr Sabry
 * @version jdk-1.2 
 * File created June 9, 2000
 **/

public class PrimitiveOperations {
    // No instances allowed ... just use static methods 
    private PrimitiveOperations () {}

    //////////////////////////////////////////////////////////////////////////////////
    // Conversions between data types ...
    //////////////////////////////////////////////////////////////////////////////////

    public static JavaFloatValue d2f (JavaValue v) throws BadConversionE {
        v.toDouble(false); // check if v is a double
        return v.toFloat(true); // force it to be converted to a float
    }

    public static JavaIntValue d2i (JavaValue v) throws BadConversionE {
        v.toDouble(false);
        return v.toInt(true);
    }

    public static JavaLongValue d2l (JavaValue v) throws BadConversionE {
        v.toDouble(false);
        return v.toLong(true);
    }

    public static JavaDoubleValue f2d (JavaValue v) throws BadConversionE {
        v.toFloat(false);
        return v.toDouble(true);
    }

    public static JavaIntValue f2i (JavaValue v) throws BadConversionE {
        v.toFloat(false);
        return v.toInt(true);
    }

    public static JavaLongValue f2l (JavaValue v) throws BadConversionE {
        v.toFloat(false);
        return v.toLong(true);
    }

    public static JavaDoubleValue l2d (JavaValue v) throws BadConversionE {
        v.toLong(false);
        return v.toDouble(true);
    }

    public static JavaFloatValue l2f (JavaValue v) throws BadConversionE {
        v.toLong(false);
        return v.toFloat(true);
    }

    public static JavaIntValue l2i (JavaValue v) throws BadConversionE {
        v.toLong(false);
        return v.toInt(true);
    }

    public static JavaByteValue i2b (JavaValue v) throws BadConversionE {
        v.toInt(false);
        return v.toByte(true);
    }

    public static JavaCharValue i2c (JavaValue v) throws BadConversionE {
        v.toInt(false);
        return v.toChar(true);
    }

    public static JavaDoubleValue i2d (JavaValue v) throws BadConversionE {
        v.toInt(false);
        return v.toDouble(true);
    }

    public static JavaFloatValue i2f (JavaValue v) throws BadConversionE {
        v.toInt(false);
        return v.toFloat(true);
    }

    public static JavaLongValue i2l (JavaValue v) throws BadConversionE {
        v.toInt(false);
        return v.toLong(true);
    }

    public static JavaShortValue i2s (JavaValue v) throws BadConversionE {
        v.toInt(false);
        return v.toShort(true);
    }

    //////////////////////////////////////////////////////////////////////////////////    
    // Arithmetic
    //////////////////////////////////////////////////////////////////////////////////    

    public static JavaDoubleValue dadd (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaDoubleValue av1 = v1.toDouble(false);
        JavaDoubleValue av2 = v2.toDouble(false);
        return new JavaDoubleValue(av1.getValue() + av2.getValue());
    }

    public static JavaIntValue dcmpg (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaDoubleValue av1 = v1.toDouble(false);
        JavaDoubleValue av2 = v2.toDouble(false);
        double d1 = av1.getValue(); 
        double d2 = av2.getValue();
        if (d1 > d2) return new JavaIntValue(1);
        else if (d1 == d2) return new JavaIntValue(0);
        else if (d1 < d2) return new JavaIntValue(-1);
        else return new JavaIntValue(1);
    }

    public static JavaIntValue dcmpl (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaDoubleValue av1 = v1.toDouble(false);
        JavaDoubleValue av2 = v2.toDouble(false);
        double d1 = av1.getValue(); 
        double d2 = av2.getValue();
        if (d1 > d2) return new JavaIntValue(1);
        else if (d1 == d2) return new JavaIntValue(0);
        else if (d1 < d2) return new JavaIntValue(-1);
        else return new JavaIntValue(-1);
    }

    public static JavaDoubleValue ddiv (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaDoubleValue av1 = v1.toDouble(false);
        JavaDoubleValue av2 = v2.toDouble(false);
        return new JavaDoubleValue(av1.getValue() / av2.getValue());
    }

    public static JavaDoubleValue dmul (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaDoubleValue av1 = v1.toDouble(false);
        JavaDoubleValue av2 = v2.toDouble(false);
        return new JavaDoubleValue(av1.getValue() * av2.getValue());
    }

    public static JavaDoubleValue dneg (JavaValue v) throws BadConversionE {
        JavaDoubleValue av = v.toDouble(false);
        return new JavaDoubleValue(- av.getValue());
    }

    public static JavaDoubleValue drem (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaDoubleValue av1 = v1.toDouble(false);
        JavaDoubleValue av2 = v2.toDouble(false);
        return new JavaDoubleValue(av1.getValue() % av2.getValue());
    }

    public static JavaDoubleValue dsub (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaDoubleValue av1 = v1.toDouble(false);
        JavaDoubleValue av2 = v2.toDouble(false);
        return new JavaDoubleValue(av1.getValue() - av2.getValue());
    }

    public static JavaFloatValue fadd (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaFloatValue av1 = v1.toFloat(false);
        JavaFloatValue av2 = v2.toFloat(false);
        return new JavaFloatValue(av1.getValue() + av2.getValue());
    }

    public static JavaIntValue fcmpg (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaFloatValue av1 = v1.toFloat(false);
        JavaFloatValue av2 = v2.toFloat(false);
        float f1 = av1.getValue(); 
        float f2 = av2.getValue();
        if (f1 > f2) return new JavaIntValue(1);
        else if (f1 == f2) return new JavaIntValue(0);
        else if (f1 < f2) return new JavaIntValue(-1);
        else return new JavaIntValue(1);
    }

    public static JavaIntValue fcmpl (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaFloatValue av1 = v1.toFloat(false);
        JavaFloatValue av2 = v2.toFloat(false);
        float f1 = av1.getValue(); 
        float f2 = av2.getValue();
        if (f1 > f2) return new JavaIntValue(1);
        else if (f1 == f2) return new JavaIntValue(0);
        else if (f1 < f2) return new JavaIntValue(-1);
        else return new JavaIntValue(-1);
    }

    public static JavaFloatValue fdiv (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaFloatValue av1 = v1.toFloat(false);
        JavaFloatValue av2 = v2.toFloat(false);
        return new JavaFloatValue(av1.getValue() / av2.getValue());
    }

    public static JavaFloatValue fmul (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaFloatValue av1 = v1.toFloat(false);
        JavaFloatValue av2 = v2.toFloat(false);
        return new JavaFloatValue(av1.getValue() * av2.getValue());
    }

    public static JavaFloatValue fneg (JavaValue v) throws BadConversionE {
        JavaFloatValue av = v.toFloat(false);
        return new JavaFloatValue(- av.getValue());
    }

    public static JavaFloatValue frem (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaFloatValue av1 = v1.toFloat(false);
        JavaFloatValue av2 = v2.toFloat(false);
        return new JavaFloatValue(av1.getValue() % av2.getValue());
    }


    public static JavaFloatValue fsub (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaFloatValue av1 = v1.toFloat(false);
        JavaFloatValue av2 = v2.toFloat(false);
        return new JavaFloatValue(av1.getValue() - av2.getValue());
    }

    public static JavaIntValue iinc (JavaValue v, int c) throws BadConversionE {
        JavaIntValue av = v.toInt(false);
        return new JavaIntValue(av.getValue() + c);
    }

    public static JavaIntValue iadd (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaIntValue(av1.getValue() + av2.getValue());
    }

    public static JavaIntValue idiv (JavaValue v1, JavaValue v2) throws BadConversionE, DivisionE {
        try {
            JavaIntValue av1 = v1.toInt(false);
            JavaIntValue av2 = v2.toInt(false);
            return new JavaIntValue(av1.getValue() / av2.getValue());
        }
        catch (ArithmeticException e) {
            throw new DivisionE("Attempting to divide by 0");
        }
    }

    public static JavaIntValue imul (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaIntValue(av1.getValue() * av2.getValue());
    }

    public static JavaIntValue ineg (JavaValue v) throws BadConversionE {
        JavaIntValue av = v.toInt(false);
        return new JavaIntValue(- av.getValue());
    }

    public static JavaIntValue irem (JavaValue v1, JavaValue v2) throws BadConversionE, DivisionE {
        try {
            JavaIntValue av1 = v1.toInt(false);
            JavaIntValue av2 = v2.toInt(false);
            return new JavaIntValue(av1.getValue() % av2.getValue());
        }
        catch (ArithmeticException e) {
            throw new DivisionE("Attempting to divide by 0");
        }
    }

    public static JavaIntValue isub (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaIntValue(av1.getValue() - av2.getValue());
    }

    public static JavaLongValue ladd (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaLongValue av1 = v1.toLong(false);
        JavaLongValue av2 = v2.toLong(false);
        return new JavaLongValue(av1.getValue() + av2.getValue());
    }

    public static JavaIntValue lcmp (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaLongValue av1 = v1.toLong(false);
        JavaLongValue av2 = v2.toLong(false);
        long l1 = av1.getValue();
        long l2 = av2.getValue();
        if (l1 > l2) return new JavaIntValue(1);
        else if (l1 == l2) return new JavaIntValue(0);
        else return new JavaIntValue(-1);
    }

    public static JavaLongValue ldiv (JavaValue v1, JavaValue v2) throws BadConversionE, DivisionE {
        try {
            JavaLongValue av1 = v1.toLong(false);
            JavaLongValue av2 = v2.toLong(false);
            return new JavaLongValue(av1.getValue() / av2.getValue());
        }
        catch (ArithmeticException e) {
            throw new DivisionE("Attempting to divide by 0");
        }
    }

    public static JavaLongValue lmul (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaLongValue av1 = v1.toLong(false);
        JavaLongValue av2 = v2.toLong(false);
        return new JavaLongValue(av1.getValue() * av2.getValue());
    }

    public static JavaLongValue lneg (JavaValue v) throws BadConversionE {
        JavaLongValue av = v.toLong(false);
        return new JavaLongValue(- av.getValue());
    }

    public static JavaLongValue lrem (JavaValue v1, JavaValue v2) throws BadConversionE, DivisionE {
        try {
            JavaLongValue av1 = v1.toLong(false);
            JavaLongValue av2 = v2.toLong(false);
            return new JavaLongValue(av1.getValue() % av2.getValue());
        }
        catch (ArithmeticException e) {
            throw new DivisionE("Attempting to divide by 0");
        }
    }

    public static JavaLongValue lsub (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaLongValue av1 = v1.toLong(false);
        JavaLongValue av2 = v2.toLong(false);
        return new JavaLongValue(av1.getValue() - av2.getValue());
    }

    //////////////////////////////////////////////////////////////////////////////////    
    // Comparisons
    //////////////////////////////////////////////////////////////////////////////////    

    public static boolean icmpeq (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return av1.getValue() == av2.getValue();
    }

    public static boolean icmpne (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return av1.getValue() != av2.getValue();
    }

    public static boolean icmplt (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return av1.getValue() < av2.getValue();
    }

    public static boolean icmple (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return av1.getValue() <= av2.getValue();
    }

    public static boolean icmpgt (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return av1.getValue() > av2.getValue();
    }

    public static boolean icmpge (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return av1.getValue() >= av2.getValue();
    }

    public static boolean eq (JavaValue v) throws BadConversionE {
        if (v instanceof JavaBooleanValue)
                return !((JavaBooleanValue)v).getValue();
        JavaIntValue av = v.toInt(false); // could be char too
        return av.getValue() == 0; 
    }

    public static boolean ne (JavaValue v) throws BadConversionE {
        if (v instanceof JavaBooleanValue)
                return ((JavaBooleanValue)v).getValue();
        JavaIntValue av = v.toInt(true);
        return av.getValue() != 0; 
    }

    public static boolean lt (JavaValue v) throws BadConversionE {
        JavaIntValue av = v.toInt(false);
        return av.getValue() < 0; 
    }

    public static boolean le (JavaValue v) throws BadConversionE {
        JavaIntValue av = v.toInt(false);
        return av.getValue() <= 0; 
    }

    public static boolean gt (JavaValue v) throws BadConversionE {
        JavaIntValue av = v.toInt(false);
        return av.getValue() > 0; 
    }

    public static boolean ge (JavaValue v) throws BadConversionE {
        JavaIntValue av = v.toInt(false);
        return av.getValue() >= 0; 
    }

    //////////////////////////////////////////////////////////////////////////////////    
    // Logical Operations
    //////////////////////////////////////////////////////////////////////////////////    

    public static JavaIntValue iand (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaIntValue(av1.getValue() & av2.getValue());
    }

    public static JavaIntValue ior (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaIntValue(av1.getValue() | av2.getValue());
    }

    public static JavaIntValue ishl (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaIntValue(av1.getValue() << av2.getValue());
    }

    public static JavaIntValue ishr (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaIntValue(av1.getValue() >> av2.getValue());
    }

    public static JavaIntValue iushr (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaIntValue(av1.getValue() >>> av2.getValue());
    }

    public static JavaIntValue ixor (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaIntValue av1 = v1.toInt(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaIntValue(av1.getValue() ^ av2.getValue());
    }

    public static JavaLongValue land (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaLongValue av1 = v1.toLong(false);
        JavaLongValue av2 = v2.toLong(false);
        return new JavaLongValue(av1.getValue() & av2.getValue());
    }

    public static JavaLongValue lor (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaLongValue av1 = v1.toLong(false);
        JavaLongValue av2 = v2.toLong(false);
        return new JavaLongValue(av1.getValue() | av2.getValue());
    }

    public static JavaLongValue lshl (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaLongValue av1 = v1.toLong(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaLongValue(av1.getValue() << av2.getValue());
    }

    public static JavaLongValue lshr (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaLongValue av1 = v1.toLong(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaLongValue(av1.getValue() >> av2.getValue());
    }

    public static JavaLongValue lushr (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaLongValue av1 = v1.toLong(false);
        JavaIntValue av2 = v2.toInt(false);
        return new JavaLongValue(av1.getValue() >>> av2.getValue());
    }

    public static JavaLongValue lxor (JavaValue v1, JavaValue v2) throws BadConversionE {
        JavaLongValue av1 = v1.toLong(false);
        JavaLongValue av2 = v2.toLong(false);
        return new JavaLongValue(av1.getValue() ^ av2.getValue());
    }
}
