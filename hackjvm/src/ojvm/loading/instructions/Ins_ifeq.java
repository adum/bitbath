package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;


/**
 * The encapsulation of an ifeq instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ifeq extends Instruction {
  private short offset;

  public Ins_ifeq (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_ifeq);
      this.offset = classFile.readShort();
  }

  public short getOffset () { return offset; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_ifeq (this);
  }

  public String toString () {
    return opcodeName + " " + offset;
  }
}
