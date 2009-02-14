package ojvm.loading;
                         
/**
 * Dispatch on name of attribute and call the appropriate constructor.
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

public abstract class AbsynAttribute {
    public abstract String getName();

    static AbsynAttribute read (ClassInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {

        int name_index = classFile.readU2();
        String name = cp.getUtf8Entry(name_index).getString();
        int len = classFile.readU4();

        if (name.equals("ConstantValue")) {
            return new ConstantValueAttribute(classFile, cp, len);
        }
        else if (name.equals("Code")) {
            return new CodeAttribute(classFile, cp, len);
        }
        else if (name.equals("Exceptions")) {
            return new ExceptionsAttribute(classFile, cp, len);
        }
        else if (name.equals("InnerClasses")) {
            return new InnerClassesAttribute(classFile, cp, len);
        }
        else if (name.equals("Synthetic")) {
            return new SyntheticAttribute(classFile, cp, len);
        }
        else if (name.equals("SourceFile")) {
            return new SourceFileAttribute(classFile, cp, len);
        }
        else if (name.equals("LineNumberTable")) {
            return new LineNumberTableAttribute(classFile, cp, len);
        }
        else if (name.equals("LocalVariableTable")) {
            return new LocalVariableTableAttribute(classFile, cp, len);
        }
        else if (name.equals("Deprecated")) {
            return new DeprecatedAttribute(classFile, cp, len);
        }
        else { 
            return new UnknownAttribute(name, classFile, len); 
        }
    }
}
