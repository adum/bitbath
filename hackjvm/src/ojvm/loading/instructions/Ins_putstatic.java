package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPool;
import ojvm.loading.CPFieldEntry;
import ojvm.loading.ConstantPoolE;
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.Descriptor;
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an putstatic instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_putstatic extends Instruction {
  private Descriptor declaringClassDesc;
  private String fieldName;
  private Descriptor fieldDesc;

  public Ins_putstatic (InstructionInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
      super(RuntimeConstants.opc_putstatic);
      int index = classFile.readU2();
      CPFieldEntry fe = cp.getFieldEntry(index);
      declaringClassDesc = fe.getDeclaringClassDesc();
      fieldName = fe.getFieldName();
      fieldDesc = fe.getFieldType();
  }

  public Descriptor getDeclaringClassDesc () { return declaringClassDesc; }
  public String getFieldName () { return fieldName; }
  public Descriptor getFieldType () { return fieldDesc; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_putstatic (this);
  }

  public String toString () {
      return opcodeName + " " + declaringClassDesc + " :: " + fieldName + " : " + fieldDesc;
  }
}
