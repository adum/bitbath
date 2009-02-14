package ojvm.data;

import ojvm.loading.AbsynField;
import ojvm.loading.ConstantValueAttribute;

import ojvm.util.Descriptor;
import ojvm.util.NameAndDescriptor;

/**
 * Internal representation of fields.
 * 
 * File created June 14, 2000
 * @author Amr Sabry
 *  
*/

public class InternalField extends InternalObject {
    private InternalClass declaringClass;
    private AbsynField absynField;

    public InternalField (InternalClass declaringClass, AbsynField absynField) {
        this.declaringClass = declaringClass;
        this.absynField = absynField;
    }

    public InternalClass getDeclaringClass () { return declaringClass; }
    public boolean isPublic () { return absynField.isPublic(); }
    public boolean isPrivate () { return absynField.isPrivate(); }
    public boolean isProtected () { return absynField.isProtected(); }
    public boolean isStatic () { return absynField.isStatic(); }
    public boolean isFinal () { return absynField.isFinal(); }
    public boolean isVolatile () { return absynField.isVolatile(); }
    public boolean isTransient () { return absynField.isTransient(); }
    public String getName () { return absynField.getName(); }
    public Descriptor getType () { return absynField.getType(); }
    public NameAndDescriptor getKey () { return new NameAndDescriptor(getName(), getType().toString()); }

    public boolean hasConstantValue () { return absynField.getConstantValue() != null; }
    public ConstantValueAttribute getConstantValue () { return absynField.getConstantValue(); }
}
