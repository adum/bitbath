package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;


/**
 * The encapsulation of an aload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_aload extends Instruction {
  private int index; // index in local vars of current frame

  public Ins_aload (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_aload);
      index = classFile.readU1(); 
  }

  protected Ins_aload (int opcode, int index) {
    super(opcode);
    this.index = index;
  }

  public int getIndex () { return index; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_aload (this);
  }

  public String toString () {
    return opcodeName + " " + index;
  }
}
