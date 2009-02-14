package ojvm.operations;

import java.util.Hashtable;
import java.util.logging.Logger;

import ojvm.data.InternalClass;
import ojvm.data.InternalClassForArray;
import ojvm.data.InternalClassForPrimitive;
import ojvm.loading.AbsynClass;
import ojvm.loading.ClassFileInputStream;
import ojvm.loading.ClassInputStream;
import ojvm.machine.ControlUnit;
import ojvm.util.CommonDescriptors;
import ojvm.util.Descriptor;

/**
 *
 * Maintains a hashtable of loaded classes. If a class is not found
 * initiates reading, verifying, and loading of the class.
 * 
 * File created June 14, 2000
 * @author Amr Sabry
 **/

public class ClassLoading {
    private static Logger logger = Logger.getLogger(ClassLoading.class.getCanonicalName());

    private ControlUnit cu; // access to the machine state in case we need to load another class...
    private String classPath;
    protected Hashtable classes;

    public ClassLoading (ControlUnit cu) { 
        this.cu = cu; 
        this.classPath = cu.getClassPath();
        this.classes = new Hashtable();
    }

    protected class ClassInfo {
        AbsynClass absynClass;
        InternalClass internalClass;

        ClassInfo (AbsynClass absynClass, InternalClass internalClass) {
            this.absynClass = absynClass;
            this.internalClass = internalClass;
        }
    }
    
    public InternalClass findClass (Descriptor classDesc) throws LinkE {
        if (classes.containsKey(classDesc)) {
            ClassInfo ci = (ClassInfo) classes.get(classDesc);
            if (ci.internalClass != null) return ci.internalClass;
            else {
                if (ci.absynClass != null) {
                    InternalClass internalClass = cu.linkClass(ci.absynClass);
                    ci.internalClass = internalClass;
                    return internalClass;
                }
                else throw new Error("Bug in classloading");
            }
        }
        else if (classDesc.isPrimitive()) {
            logger.fine("*****Creating class for " + classDesc);
            InternalClass javaLangObject = findClass(CommonDescriptors.javaLangObjectDesc);
            InternalClass ic = new InternalClassForPrimitive(javaLangObject,classDesc);
            classes.put(classDesc, new ClassInfo(null,ic));
            return ic;
        }
        else if (classDesc.isArray()) {
            logger.fine("*****Creating class for " + classDesc);
            InternalClass[] interfaces = new InternalClass[2];
            interfaces[0] = findClass(CommonDescriptors.javaLangCloneableDesc);
            interfaces[1] = findClass(CommonDescriptors.javaIoSerializableDesc);
            Descriptor componentType = classDesc.getBaseDesc();
            InternalClass componentClass = findClass(componentType);
            int numDimensions = classDesc.getNumDimensions();
            InternalClass javaLangObject = findClass(CommonDescriptors.javaLangObjectDesc);
            InternalClass ic = new InternalClassForArray(javaLangObject,componentClass,numDimensions,interfaces,classDesc);
            classes.put(classDesc, new ClassInfo(null,ic));
            return ic;
        }
        else {
            logger.fine("*****Reading class file for " + classDesc);
            String filename = classDesc.getFilename();
            ClassInputStream cis = new ClassInputStream(classPath,filename);
            AbsynClass loadedClass = new AbsynClass(cis);
            //            if (! loadedClass.getDesc().equals(classDesc))
            //                throw new ClassNotFoundE("Class file did not have a definition of " + classDesc);
            classes.put(classDesc, new ClassInfo(loadedClass,null));
            return findClass(classDesc); // link it
        }
    }
}
