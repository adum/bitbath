package ojvm.operations;

import java.util.logging.Logger;

import ojvm.loading.ConstantValueAttribute;

import ojvm.data.JavaValue;
import ojvm.data.JavaLongValue;
import ojvm.data.JavaFloatValue;
import ojvm.data.JavaDoubleValue;
import ojvm.data.JavaIntValue;
import ojvm.data.InternalClass;
import ojvm.data.InternalMethod;
import ojvm.data.InternalField;
import ojvm.data.JavaException;
import ojvm.data.FieldNotFoundE;

import ojvm.machine.ControlUnit;
import ojvm.machine.LocalVarsE;
import ojvm.machine.ThreadHaltE;

import ojvm.util.NameAndDescriptor;


/**
 *
 * Initializes classes ignoring recursion; locks; threads; etc for now
 * 
 * File created June 20, 2000
 * @author Amr Sabry
 **/

public class ClassInitializing {
    private static Logger logger = Logger.getLogger(ClassInitializing.class.getCanonicalName());
    private ControlUnit cu;

    public ClassInitializing (ControlUnit cu) {
        this.cu = cu;
    }

    public void initialize (InternalClass internalClass) throws LinkE, InitializeE { 
        try {
            if (internalClass.isInitialized()) return; 

            internalClass.setInitialized();        
        
            InternalClass superClass = internalClass.getSuperClass();
            if (superClass != null) {
                if (! superClass.isInitialized()) initialize(superClass);
            }

            logger.fine("*****Initializing " + internalClass.getDesc());
            initializeStaticFields(internalClass);
            InternalMethod initializer = internalClass.getInitializer();
            if (initializer != null) {
                cu.runSpecialMethod(initializer,new JavaValue[0]);
            }
        }
        catch (JavaException e) { 
            throw new InitializeE(e, "Exception in class initializer of " + internalClass.getDesc()); 
        }
        catch (LocalVarsE e) { 
            throw new Error("Bug in running class initializer");
        }
    }

    private void initializeStaticFields (InternalClass internalClass) throws LinkE, JavaException {
        try {
            InternalField[] declaredStaticFields = internalClass.getDeclaredStaticFields();

            for (int i=0; i<declaredStaticFields.length; i++) {
                if (declaredStaticFields[i].hasConstantValue()) {
                    NameAndDescriptor key = declaredStaticFields[i].getKey();
                    JavaValue fv = convertConstantValue(declaredStaticFields[i].getConstantValue());
                    internalClass.putstatic(key, fv);
                }                
            }
        }
        catch (FieldNotFoundE e) {
            throw new Error("Bug in initialization of static fields");
        }
    }

    private JavaValue convertConstantValue (ConstantValueAttribute cva) throws LinkE, JavaException {
        if (cva == null) throw new Error("Bug in class initializing"); 
       
        if (cva.ctype == ConstantValueAttribute.LONG_TYPE) 
            return new JavaLongValue(cva.getLongValue());
        else if (cva.ctype == ConstantValueAttribute.FLOAT_TYPE) 
            return new JavaFloatValue(cva.getFloatValue());
        else if (cva.ctype == ConstantValueAttribute.DOUBLE_TYPE) 
            return new JavaDoubleValue(cva.getDoubleValue());
        else if (cva.ctype == ConstantValueAttribute.INT_TYPE) 
            return new JavaIntValue(cva.getIntValue());
        else if (cva.ctype == ConstantValueAttribute.STRING_TYPE) {
            String chars = cva.getStringValue();
            return cu.makeStringInstanceFromLiteral(chars);
        }
        else throw new Error("Bug in convert constant value");
    }
}
