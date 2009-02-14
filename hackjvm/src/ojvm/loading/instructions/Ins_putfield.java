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
 * The encapsulation of an putfield instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_putfield extends Instruction {
  private Descriptor declaringClassDesc;
  private String fieldName;
  private Descriptor fieldDesc;

  public Ins_putfield (InstructionInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
      super(RuntimeConstants.opc_putfield);
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
    iv.visit_putfield (this);
  }

  public String toString () {
      return opcodeName + " " + declaringClassDesc + " :: " + fieldName + " : " + fieldDesc;
  }
}
