package ojvm.loading;
                         
/**
 * The constant value attribute of static fields
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

public class ConstantValueAttribute extends AbsynAttribute {
    public final static int LONG_TYPE = -2;
    public final static int FLOAT_TYPE = -1;
    public final static int DOUBLE_TYPE = 0;
    public final static int INT_TYPE = 1;
    public final static int STRING_TYPE = 2;
    public int ctype;
    private long longValue;
    private float floatValue;
    private double doubleValue;
    private int intValue;
    private String stringValue;

    public String getName () { return "ConstantValue"; }
    public long getLongValue() { return longValue; }
    public float getFloatValue() { return floatValue; }
    public double getDoubleValue() { return doubleValue; }
    public int getIntValue() { return intValue; }
    public String getStringValue() { return stringValue; }

    ConstantValueAttribute (ClassInputStream classFile, ConstantPool cp, int length) 
        throws ClassFileInputStreamE, ConstantPoolE {

        if (length != 2) throw new ConstantPoolE("Bad length for \"ConstantValue\" attribute");
        int constantvalue_index = classFile.readU2();
        CPEntry ve = cp.get(constantvalue_index);
        if (ve instanceof CPLongEntry) {
            ctype = LONG_TYPE;
            longValue = ((CPLongEntry)ve).getLong();
        }
        else if (ve instanceof CPFloatEntry) {
            ctype = FLOAT_TYPE;
            floatValue = ((CPFloatEntry)ve).getFloat();
        }
        else if (ve instanceof CPDoubleEntry) {
            ctype = DOUBLE_TYPE;
            doubleValue = ((CPDoubleEntry)ve).getDouble();
        }
        else if (ve instanceof CPIntEntry) {
            ctype = INT_TYPE;
            intValue = ((CPIntEntry)ve).getInt();
        }
        else if (ve instanceof CPStringEntry) {
            ctype = STRING_TYPE;
            stringValue = ((CPStringEntry)ve).getString();
        }
        else throw new ConstantPoolE("Bad entry for \"ConstantValue\" attribute");
    }

    public String toString () { 
        String res = "";
        if (ctype == LONG_TYPE) res += longValue;
        else if (ctype == FLOAT_TYPE) res += floatValue;
        else if (ctype == DOUBLE_TYPE) res += doubleValue;
        else if (ctype == INT_TYPE) res += intValue;
        else res += "\"" + stringValue + "\"";
        return res;
    }
}
