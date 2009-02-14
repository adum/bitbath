package ojvm.operations;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import ojvm.data.InternalClass;
import ojvm.loading.AbsynClass;
import ojvm.loading.ClassInputStream;
import ojvm.machine.ControlUnit;
import ojvm.util.Descriptor;

public class DirectoryClassLoading extends ClassLoading {
    private String root;

    public DirectoryClassLoading(ControlUnit cu, String rootDir) {
        super(cu);
        if (rootDir == null)
            throw new IllegalArgumentException("Null root directory");
        root = rootDir;
    }

    @Override
    public InternalClass findClass(Descriptor classDesc) throws LinkE {
        InternalClass foundClass;
        try {
            foundClass = super.findClass(classDesc);
            return foundClass;
        }
        catch (LinkE e) {
            // Convert class name argument to filename
            String[] split = classDesc.getJavaForm().split("\\.");
            String filename = split[split.length - 1] + ".class";
//            System.err.println(e.getMessage());

            try {
                DataInputStream dinp = loadClassData(filename);
                // use the data input stream
                ClassInputStream cis = new ClassInputStream(dinp);
                AbsynClass loadedClass = new AbsynClass(cis);
                classes.put(classDesc, new ClassInfo(loadedClass,null));
                return findClass(classDesc); // link it
            }
            catch (Exception ex) {
                throw new LinkE("not found: " + filename);
            }
        }
    }

    private DataInputStream loadClassData(String filename) throws IOException {
        // Create a file object relative to directory provided
        File f = new File(root, filename);

        // Get stream to read from
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);

        // return data
        return dis;
    }
}
