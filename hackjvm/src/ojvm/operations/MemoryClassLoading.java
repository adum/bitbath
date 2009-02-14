
package ojvm.operations;

import java.io.DataInputStream;

import ojvm.data.InternalClass;
import ojvm.loading.AbsynClass;
import ojvm.loading.ClassInputStream;
import ojvm.machine.ControlUnit;
import ojvm.util.Descriptor;

public class MemoryClassLoading extends ClassLoading {
    private DataInputStream extraClassInput;
    private String className;

    public MemoryClassLoading (ControlUnit cu, String className, DataInputStream extraClassInput) { 
        super(cu);
        this.extraClassInput = extraClassInput;
        this.className = className;
    }

    @Override
    public InternalClass findClass(Descriptor classDesc) throws LinkE {
        InternalClass foundClass;
        try {
            foundClass = super.findClass(classDesc);
            return foundClass;
        }
        catch (LinkE e) {
            // let's see if this puppy matches our input
            if (className.equals(classDesc.getJavaForm())) {
                // use the data input stream
                ClassInputStream cis = new ClassInputStream(extraClassInput);
                AbsynClass loadedClass = new AbsynClass(cis);
                classes.put(classDesc, new ClassInfo(loadedClass,null));
                return findClass(classDesc); // link it
            }
            else {
//                e.printStackTrace();
                throw e;
            }
        }
    }
}
