package ojvm.data;

import java.util.Hashtable;

import ojvm.loading.AbsynClass;
import ojvm.loading.AbsynField;
import ojvm.loading.AbsynMethod;

import ojvm.util.Descriptor;
import ojvm.util.MethodDescriptor;
import ojvm.util.NameAndDescriptor;
import ojvm.util.BadDescriptorE;

/**
 * Internal representation of classes.
 * 
 * File created June 9, 2000
 * @author Amr Sabry
 **/

public class InternalClassFromAbsyn extends InternalClass {
    private AbsynClass absynClass;

    private InternalField[] declaredInstanceFields;
    private int numAllInstanceFields;
    private InternalField[] declaredStaticFields;
    private Hashtable declaredStaticFieldTable; 

    private InternalMethod initializer; // may be null
    private Hashtable declaredMethodTable;

    public InternalClassFromAbsyn (InternalClass superClass, InternalClass[] interfaces, AbsynClass absynClass) 
        throws PrepareE {

        super(superClass,interfaces,false);
        this.absynClass = absynClass;
        prepare();
    }

    public boolean isPrimitive() { return false; }
    public boolean isArray() { return false; }
    public boolean isReference() { return true; }

    public InternalClass getComponentClass() { return null; }

    public Descriptor getDesc () { return absynClass.getDesc(); }
    public boolean isPublic () { return absynClass.isPublic(); }
    public boolean isFinal () { return absynClass.isFinal(); }
    public boolean accSuperSet () { return absynClass.accSuperSet(); }
    public boolean isInterface () { return absynClass.isInterface(); }
    public boolean isAbstract () { return absynClass.isAbstract(); }

    public int getNumAllInstanceFields () { return numAllInstanceFields; }
    public InternalField[] getDeclaredInstanceFields () { return declaredInstanceFields; }

    public JavaValue[] makeNewInstanceFields () {
        JavaValue[] result = new JavaValue[numAllInstanceFields];
        int myLastIndex = numAllInstanceFields - 1; 
        initializeInstanceValues(result, myLastIndex);
        return result;
    }

    public void initializeInstanceValues (JavaValue[] vs, int myLastIndex) {
        try {
            int myFirstIndex = myLastIndex - declaredInstanceFields.length + 1;
            if (superClass != null) {
                superClass.initializeInstanceValues(vs, myFirstIndex-1);
            }
            for (int i=myFirstIndex, j=0; i<=myLastIndex; i++, j++) {
                Descriptor desc = declaredInstanceFields[j].getType();
                vs[i] = Descriptor.getDefaultValue(desc);
            }
        }
        catch (BadDescriptorE e) {
            throw new Error("Illegal descriptor " + e.getMessage());
        }
    }

    public int findField (NameAndDescriptor key) throws FieldNotFoundE {
        int previousFields = numAllInstanceFields - declaredInstanceFields.length;
        for (int i=0; i<declaredInstanceFields.length; i++) {
            if (declaredInstanceFields[i].getKey().equals(key)) {
                return previousFields+i;
            }
        }
        if (superClass != null) return superClass.findField(key);
        else throw new FieldNotFoundE("Field " + key + " not found");
    }

    public InternalField[] getDeclaredStaticFields () { 
        return declaredStaticFields; 
    }

    public JavaValue getstatic (NameAndDescriptor key) throws FieldNotFoundE {
        if (declaredStaticFieldTable.containsKey(key)) return (JavaValue) declaredStaticFieldTable.get(key);
        else if (superClass != null) return superClass.getstatic(key);
        else throw new FieldNotFoundE("Static field " + key + " not found");
    }

    public void putstatic (NameAndDescriptor key, JavaValue fv) throws FieldNotFoundE {
        if (declaredStaticFieldTable.containsKey(key)) declaredStaticFieldTable.put(key,fv); 
        else if (superClass != null) superClass.putstatic(key,fv); 
        else throw new FieldNotFoundE("Static field " + key + " not found");
    }

    public InternalMethod getInitializer () { return initializer; }

    public InternalMethod findMethod (NameAndDescriptor key) throws MethodNotFoundE { 
        if (declaredMethodTable.containsKey(key)) return (InternalMethod) declaredMethodTable.get(key);
        else if (superClass != null) return superClass.findMethod(key);
        else throw new MethodNotFoundE("Method " + key + " not found");
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void prepare () throws PrepareE {
        // superclass already prepared since it is impossible to constructor an InternalClass that is not prepared

        AbsynField[] absynFields = absynClass.getDeclaredFields(); 
        int numInstanceFields = 0;
        int numStaticFields = 0;
        for (int i=0; i<absynFields.length; i++) {
            if (absynFields[i].isStatic()) numStaticFields++; else numInstanceFields++;
        }
        
        declaredInstanceFields = new InternalField[numInstanceFields];
        for (int i=0, j=0; i<absynFields.length; i++) {
            if (! absynFields[i].isStatic()) 
                declaredInstanceFields[j++] = new InternalField(this, absynFields[i]);
        }

        numAllInstanceFields = declaredInstanceFields.length;
        numAllInstanceFields += superClass == null? 0 : superClass.getNumAllInstanceFields();

        declaredStaticFields = new InternalField[numStaticFields];
        for (int i=0, j=0; i<absynFields.length; i++) {
            if (absynFields[i].isStatic()) 
                declaredStaticFields[j++] = new InternalField(this, absynFields[i]);
        }

        declaredStaticFieldTable = new Hashtable();
        for (int i=0; i<declaredStaticFields.length; i++) {
            NameAndDescriptor fk = declaredStaticFields[i].getKey();
            try { 
                JavaValue fv = Descriptor.getDefaultValue(declaredStaticFields[i].getType());
                if (declaredStaticFieldTable.containsKey(fk)) 
                    throw new PrepareE("Duplicate field " + fk + " in class " + getDesc());
                else declaredStaticFieldTable.put(fk,fv); 
            }
            catch (BadDescriptorE e) { 
                throw new Error("Illegal descriptor " + e.getMessage());
            }
        }

        AbsynMethod[] absynMethods = absynClass.getDeclaredMethods(); 
        declaredMethodTable = new Hashtable();
        for (int i=0; i<absynMethods.length; i++) {
            InternalMethod dm = new InternalMethod(this, absynMethods[i]);
            if (dm.isClassInitializer()) {
                if (initializer != null) throw new PrepareE("More than one static initializer in class " + getDesc());
                initializer = dm;
            }
            else {
                NameAndDescriptor mk = dm.getKey();
                if (declaredMethodTable.containsKey(mk)) 
                    throw new PrepareE("Duplicate method " + mk + " in class " + getDesc());
                else declaredMethodTable.put(mk,dm); 
            }
        }
    }
}
