package ojvm.operations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import ojvm.data.InternalClass;
import ojvm.loading.AbsynClass;
import ojvm.loading.ClassInputStream;
import ojvm.machine.ControlUnit;
import ojvm.util.Descriptor;

public class JarStreamClassLoading extends ClassLoading {
    private Map<String, byte[]> jarClasses = new HashMap<String, byte[]>();

    public JarStreamClassLoading(ControlUnit cu, DataInputStream stream) { 
        super(cu);
        try {
            JarInputStream jinp = new JarInputStream(stream);
            JarEntry entry;
            // read all entries into memory
            while ((entry = jinp.getNextJarEntry()) != null) {
                if (entry.getName().endsWith(".class")) {
                    byte[] buf = new byte[1024];
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    int nBytes; 
                    while((nBytes = jinp.read(buf)) != -1) { 
                        os.write(buf, 0, nBytes); 
                    } 
                    byte[] data = os.toByteArray();
                    jarClasses.put(entry.getName().replace('/', '.'), data);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InternalClass findClass(Descriptor classDesc) throws LinkE {
        InternalClass foundClass;
        try {
            foundClass = super.findClass(classDesc);
            return foundClass;
        }
        catch (LinkE e) {
            String nm = classDesc.getJavaForm() + ".class";
            // let's see if this puppy matches our input
            if (jarClasses.containsKey(nm)) {
                // use the data input stream
                DataInputStream dinp = new DataInputStream(new ByteArrayInputStream(jarClasses.get(nm)));
                ClassInputStream cis = new ClassInputStream(dinp);
                AbsynClass loadedClass = new AbsynClass(cis);
                classes.put(classDesc, new ClassInfo(loadedClass,null));
                return findClass(classDesc); // link it
            }
            else {
                e.printStackTrace();
                throw e;
            }
        }
    }
}
