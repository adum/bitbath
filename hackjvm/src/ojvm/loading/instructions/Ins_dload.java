package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;


/**
 * The encapsulation of an dload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dload extends Instruction {
  private int index;

  public Ins_dload (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_dload);
      this.index = classFile.readU1();
  }

  protected Ins_dload (int opcode, int index) {
    super(opcode);
    this.index = index;
  }

  public int getIndex () { return index; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_dload (this);
  }

  public String toString () {
    return opcodeName + " " + index;
  }
}
