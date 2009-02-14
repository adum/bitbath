package ojvm.loading;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * Stream that specializes in reading everything from a class file
 * other than the instruction byteArray. It includes methods to read
 * long, float, double, and those UTF values which do not appear
 * within instructions.
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
 **/

public class ClassInputStream extends ClassFileInputStream {
  public ClassInputStream (String classPath, String fileName) throws ClassNotFoundE, ClassFileInputStreamE {
    super(classPath,fileName);
  }

  public ClassInputStream (DataInputStream stream) {
      super(stream);
  }

  public long readLong () throws ClassFileInputStreamE {
    try { return stream.readLong(); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }


  public float readFloat () throws ClassFileInputStreamE {
    try { return stream.readFloat(); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }

  public double readDouble () throws ClassFileInputStreamE {
    try { return stream.readDouble(); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }

  public String readUTF () throws ClassFileInputStreamE {
   try { return stream.readUTF(); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }

  public byte[] readAttribute (int len) throws ClassFileInputStreamE {
    try {
        byte[] result = new byte[len];
        stream.readFully(result);
        return result;
    }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }
}
