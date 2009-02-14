package ojvm.loading;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

/**
 * Abstract class for finding class files and reading them. We define
 * methods to read the types u1, u2, and u4 used in the specification
 * of the class file format.
 *
 * There are two subclasses. One that specializes in reading the
 * instructions byteArray, and another that reads all other class
 * information. 
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
 **/

public abstract class ClassFileInputStream {
    private static Logger logger = Logger.getLogger(ClassFileInputStream.class.getCanonicalName());

    private String filename;
  protected DataInputStream stream; // underlying stream from which we are reading
  protected ClassFileInputStreamE err; // object to throw in case of reading error

  protected ClassFileInputStream (DataInputStream stream) {
      this.stream = stream;
      filename = "";
      this.err = new ClassFileInputStreamE("Error reading from existing stream");
  }
  
  protected ClassFileInputStream (String classPath, String filename) throws ClassNotFoundE, ClassFileInputStreamE {
      try {
          StringTokenizer st = new StringTokenizer(classPath, File.pathSeparator);
          while (true) {
              String dir = st.nextToken();
              if (dir.endsWith(".jar")) {
                  try {
                      JarFile jf = new JarFile(dir);
                      String fname = filename.replace('\\', '/'); // we might be on windows
                      ZipEntry ze = jf.getEntry(fname);
                      this.filename = dir + ":" + filename;
                      this.stream = new DataInputStream(jf.getInputStream(ze));
                      this.err = new ClassFileInputStreamE("Error reading file " + this.filename);
                      break;
                  }
                  catch (Exception e) {} // try next alternative in classpath
              }
              else {
//                  {
////                      String fullname = dir + File.separator + "foox";
//                      String fullname = "foox";
//                      File f = new File(fullname);
//                      try {
//                        f.createNewFile();
//                    }
//                    catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                  }
                  String fullname = dir + File.separator + filename;
                  if (new File(fullname).exists()) {
                      try {
                          this.filename = fullname;
//                          this.stream = new DataInputStream(new FileInputStream(filename));
                          this.stream = new DataInputStream(new FileInputStream(fullname));
                          this.err = new ClassFileInputStreamE("Error reading file " + this.filename);
                          break;
                      }
                      catch (Exception e) {
                          System.err.println(e);
                          // try next alternative in classpath   
                      }
                  }
              }
          }
      }
      catch (NoSuchElementException e) {
            try {
                // some code to point out the places we looked -- debugging help
                StringTokenizer st = new StringTokenizer(classPath, File.pathSeparator);
                while (true) {
                    String dir = st.nextToken();
                    if (dir.endsWith(".jar")) {
                        try {
                            JarFile jf = new JarFile(dir);
                            if (jf.size() == 0)
                                logger.finer("no jar file entries: " + dir);
                            break;
                        }
                        catch (Exception ex) {
                        } // try next alternative in classpath
                    }
                    else {
                        String fullname = dir + File.separator + filename;
                        if (! new File(dir).exists()) {
                            logger.finer("dir does not exist: " + dir);
                        }
                        else if (! new File(fullname).exists()) {
                            logger.finer("file does not exist: " + fullname);
                        }
                    }
                }
            }
            catch (NoSuchElementException eee) {
            }
            throw new ClassNotFoundE("File " + filename + " not found in: " + classPath);
        }
  }

  protected ClassFileInputStream (byte[] instructionArray, String filename) {
      this.filename = filename;
      this.stream = new DataInputStream(new ByteArrayInputStream(instructionArray));
      this.err = new ClassFileInputStreamE("Error reading byte array of instructions in file " + filename);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////
    
  public String getFilename () { return filename; }

  public boolean hasMoreBytes () throws ClassFileInputStreamE { 
    try { 
      if (stream.available() == 0) return false;
      else return true;
    }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }

  public void skipBytes (int len) throws ClassFileInputStreamE {
    try { stream.skipBytes(len); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }

  public int readU1 () throws ClassFileInputStreamE {
    try { return stream.readUnsignedByte(); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }

  public int readU2 () throws ClassFileInputStreamE {
    try { return stream.readUnsignedShort(); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }

  public int readU4 () throws ClassFileInputStreamE {
    try { return stream.readInt(); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }

  public byte readByte () throws ClassFileInputStreamE {
    try { return stream.readByte(); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }

  public short readShort () throws ClassFileInputStreamE {
    try { return stream.readShort(); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }

  public int readInt () throws ClassFileInputStreamE {
    try { return stream.readInt(); }
    catch (EOFException e) { throw err; }
    catch (IOException e) { throw err; }
  }
}
