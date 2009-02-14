package ojvm.loading;
                         
import ojvm.util.RuntimeConstants;

/**
 * The constant pool ... It is originally created by reading a
 * classFile.  The constructor reads the information and then checks
 * that indices point to the right places For example a
 * constant_class_info index should point to an entry that has a
 * name_index which itself points to a UTF8 structure, etc. We must do
 * it in two passes.
 * 
 * @author Amr Sabry
 * @version jdk-1.1 
 **/

public class ConstantPool {
  private static final int FIRST_VALID_INDEX = 1;
  private int constant_pool_count;
  private CPEntry[] cp_info;

  ConstantPool (ClassInputStream classFile) throws ClassFileInputStreamE, ConstantPoolE {
      constant_pool_count = classFile.readU2();
      cp_info = new CPEntry[constant_pool_count];

      CPEntry badEntry = new CPInvalidEntry();
      // First entry in cp_info is for internal use by the meta JVM. We have no use for it.
      // The first value in the class file is stored at index 1. 
      cp_info[0] = badEntry;
      for (int i=FIRST_VALID_INDEX; i<constant_pool_count; i++) {
          int tag = classFile.readU1();
          switch (tag) {
          case RuntimeConstants.CONSTANT_CLASS: 
              cp_info[i] = new CPClassEntry(classFile); break;
          case RuntimeConstants.CONSTANT_FIELD: 
              cp_info[i] = new CPFieldEntry(classFile); break;
          case RuntimeConstants.CONSTANT_METHOD: 
              cp_info[i] = new CPMethodEntry(classFile); break;
          case RuntimeConstants.CONSTANT_INTERFACEMETHOD: 
              cp_info[i] = new CPInterfaceMethodEntry(classFile); break;
          case RuntimeConstants.CONSTANT_STRING: 
              cp_info[i] = new CPStringEntry(classFile); break;
          case RuntimeConstants.CONSTANT_INTEGER: 
              cp_info[i] = new CPIntEntry(classFile); break;
          case RuntimeConstants.CONSTANT_FLOAT: 
              cp_info[i] = new CPFloatEntry(classFile); break;
          case RuntimeConstants.CONSTANT_LONG: 
              cp_info[i] = new CPLongEntry(classFile); 
              cp_info[++i] = badEntry; // next entry is invalid
              break;
          case RuntimeConstants.CONSTANT_DOUBLE: 
              cp_info[i] = new CPDoubleEntry(classFile); 
              cp_info[++i] = badEntry; // next entry is invalid
              break;
          case RuntimeConstants.CONSTANT_NAMEANDTYPE: 
              cp_info[i] = new CPNameAndTypeEntry(classFile); break;
          case RuntimeConstants.CONSTANT_UTF8: 
              cp_info[i] = new CPUtf8Entry(classFile); break;
          default: throw new ConstantPoolE("Unknown tag " + tag + " in constant pool ");
          }
      }
      
      // check and resolve indices
      for (int i=FIRST_VALID_INDEX; i<constant_pool_count; i++) { 
          cp_info[i].resolve(this);
      }
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  // Getters
  public CPClassEntry getClassEntry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    if (cpe instanceof CPClassEntry) return (CPClassEntry)cpe;
    else throw new ConstantPoolE("No CONSTANT_Class found at index " + index);
  }

  public CPFieldEntry getFieldEntry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    if (cpe instanceof CPFieldEntry) return (CPFieldEntry)cpe;
    else throw new ConstantPoolE("No CONSTANT_Fieldref found at index " + index);
  }

  public CPMethodEntry getMethodEntry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    if (cpe instanceof CPMethodEntry) return (CPMethodEntry)cpe;
    else throw new ConstantPoolE("No CONSTANT_Methodref found at index " + index);
  }

  public CPInterfaceMethodEntry getInterfaceMethodEntry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    if (cpe instanceof CPInterfaceMethodEntry) return (CPInterfaceMethodEntry)cpe;
    else throw new ConstantPoolE("No CONSTANT_InterfaceMethodref found at index " + index);
  }

  public CPStringEntry getStringEntry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    if (cpe instanceof CPStringEntry) return (CPStringEntry)cpe;
    else throw new ConstantPoolE("No CONSTANT_String found at index " + index);
  }

  public CPIntEntry getIntEntry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    if (cpe instanceof CPIntEntry) return (CPIntEntry)cpe;
    else throw new ConstantPoolE("No CONSTANT_Integer found at index " + index);
  }

  public CPFloatEntry getFloatEntry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    if (cpe instanceof CPFloatEntry) return (CPFloatEntry)cpe;
    else throw new ConstantPoolE("No CONSTANT_Float found at index " + index);
  }

  public CPLongEntry getLongEntry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    CPEntry next = get(index+1);
    if (cpe instanceof CPLongEntry && next instanceof CPInvalidEntry) 
      return (CPLongEntry)cpe;
    else throw new ConstantPoolE("No CONSTANT_Long found at indices " + index + " and " + (index+1));
  }

  public CPDoubleEntry getDoubleEntry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    CPEntry next = get(index+1);
    if (cpe instanceof CPDoubleEntry && next instanceof CPInvalidEntry) 
      return (CPDoubleEntry)cpe;
    else throw new ConstantPoolE("No CONSTANT_Double found at indices " + index + " and " + (index+1));
  }

  public CPNameAndTypeEntry getNameAndTypeEntry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    if (cpe instanceof CPNameAndTypeEntry) return (CPNameAndTypeEntry)cpe;
    else throw new ConstantPoolE("No CONSTANT_NameAndType found at index " + index);
  }

  public CPUtf8Entry getUtf8Entry (int index) throws ConstantPoolE {
    CPEntry cpe = get(index);
    if (cpe instanceof CPUtf8Entry) return (CPUtf8Entry)cpe;
      else throw new ConstantPoolE("No CONSTANT_Utf8 found at index " + index);
  }

  public CPEntry get (int index) throws ConstantPoolE {
    checkIndex(index);
    return cp_info[index]; 
  }

  private void checkIndex (int index) throws ConstantPoolE {
    if (index >= FIRST_VALID_INDEX && index < constant_pool_count) return;
    else throw new ConstantPoolE("Index " + index + " not in the range " +
                                 FIRST_VALID_INDEX + ".." + (constant_pool_count-1));
  }

  public String toString () {
      String res = "";
      for (int i=FIRST_VALID_INDEX; i<constant_pool_count; i++) { 
          res += i + ": " + cp_info[i] + "\n";
      }
      return res;
   }
}
